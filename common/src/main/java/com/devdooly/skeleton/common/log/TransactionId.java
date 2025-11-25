package com.devdooly.skeleton.common.log;

import com.devdooly.skeleton.common.type.TimeRecord;
import com.devdooly.skeleton.common.utils.ElapsedTimer;

import java.util.Objects;

public class TransactionId implements LogDecoratorAware, TimeRecord {
    private final String tId;
    private final LogDecorator logDecorator;
    private final ElapsedTimer elapsedTimer;

    public TransactionId(String tId, LogDecorator logDecorator) {
        this.tId = tId;
        this.logDecorator = logDecorator;
        this.elapsedTimer = ElapsedTimer.startTimer();
    }

    public String tId() {
        return tId;
    }

    @Override
    public LogDecorator getLogDecorator() {
        return logDecorator;
    }

    @Override
    public long timeTaken() {
        return elapsedTimer.stop();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransactionId that)) return false;
        return Objects.equals(tId, that.tId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(tId);
    }
    
    @Override
    public String toString() {
        return tId;
    }
}
