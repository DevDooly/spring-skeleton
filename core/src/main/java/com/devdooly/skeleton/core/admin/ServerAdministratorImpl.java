package com.devdooly.skeleton.core.admin;

import com.devdooly.skeleton.common.concurrent.Delay;
import com.devdooly.skeleton.common.concurrent.ThreadPoolUtils;
import com.devdooly.skeleton.common.utils.ElapsedTimer;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

public class ServerAdministratorImpl implements  ServerAdministrator {

    private final long since = System.currentTimeMillis();
    private final Lock lock = new ReentrantLock();
    private final InternalStatus internalStatus = new InternalStatus();
    private final Runnable drainTrigger;
    private final Supplier<Boolean> readyFlag;
    private final Supplier<Boolean> drainedFlag;

    public ServerAdministratorImpl(Runnable drainTrigger,
                                   Supplier<Boolean> readyFlag,
                                   Supplier<Boolean> drainedFlag) {
        this.drainTrigger = drainTrigger;
        this.readyFlag = readyFlag;
        this.drainedFlag = drainedFlag;
    }

    @Override
    public long getSince() {
        return since;
    }

    @Override
    public ServerStatus getStatus() {
        return internalStatus.getStatus();
    }

    @Override
    public void startUp() {
        if (internalStatus.isNotReady()) {
            final long timeout = 3000L;
            final ElapsedTimer timer = ElapsedTimer.startTimer();
            while (!readyFlag.get()) {
                Delay.sleepMillis(50);
                if (timer.elapsed() > timeout) {
                    throw new IllegalStateException("start up failure due to timeout.");
                }
            }
            internalStatus.toUp();
        } else {
            throw new IllegalStateException("the status must be 'NOT_READY'.");
        }
    }

    @Override
    public boolean isAlive() {
        return !internalStatus.isDown();
    }

    @Override
    public boolean isReady() {
        return internalStatus.isUp(); 
    }

    @Override
    public boolean isShutdownable() {
        return internalStatus.isOutOfService() || internalStatus.isDown();
    }

    @Override
    public void drain() {
        drain(internalStatus::toOutOfService); 
    }

    @Override
    public void down() {
        drain(internalStatus::toDown);    
    }

    private void drain(Runnable updateStatusAfterDrain) {
        if (internalStatus.isUp()) {
            lock.lock();
            try {
                if (internalStatus.isUp()) {
                    internalStatus.toDrain();
                    drainTrigger.run();
                    ThreadPoolUtils.runOnVirtual(() -> {
                        while (internalStatus.isDrain()) {
                            if (drainedFlag.get()) {
                                updateStatusAfterDrain.run();
                            }
                            Delay.sleepMillis(1400L);
                        }
                    });
                }
            } finally {
                lock.unlock();
            }
        }
    }

    private static class InternalStatus {
        private volatile ServerStatus status = ServerStatus.NOT_READY;

        public boolean isNotReady() {
            return status == ServerStatus.NOT_READY;
        }

        public boolean isUp() {
            return status == ServerStatus.UP;
        }

        public boolean isDrain() {
            return status == ServerStatus.DRAIN;
        }

        public boolean isOutOfService() {
            return status == ServerStatus.OUT_OF_SERVICE;
        }

        public boolean isDown() {
            return status == ServerStatus.DOWN;
        }

        public void toUp() {
            status = ServerStatus.UP;
        }

        public void toDrain() {
            status = ServerStatus.DRAIN;
        }

        public void toOutOfService() {
            status = ServerStatus.OUT_OF_SERVICE;
        }

        public void toDown() {
            status = ServerStatus.DOWN;
        }

        public ServerStatus getStatus() {
            return status;
        }
    }

}
