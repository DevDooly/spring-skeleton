package com.devdooly.skeleton.common.concurrent;

import com.devdooly.skeleton.common.log.TransactionId;
import com.devdooly.skeleton.common.log.TransactionIdFactory;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class TransactionLogExecutor extends ThreadPoolExecutor {
    private final ThreadLocal<TransactionId> threadLocal = new ThreadLocal<>();
    private final TransactionIdFactory transactionIdFactory;

    TransactionLogExecutor(int size, String prefix, TransactionIdFactory transactionIdFactory) {
        super(size, size, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), NamedThreadFactory.create(prefix), new AbortPolicy());
    }

    public final void execute(Consumer<TransactionId> runConsumer) {
        execute(() -> runConsumer.accept(threadLocal.get()));
    }

    @Override
    protected final void beforeExecute(Thread t, Runnable r) {
        threadLocal.set(transactionIdFactory.get());
    }

    @Override
    protected final void afterExecute(Runnable r, Throwable t) {
        threadLocal.remove();
    }
}
