package com.devdooly.skeleton.common.log;

import java.util.function.Function;

public class TransactionIdFactoryImpl implements TransactionIdFactory {
    private final IdGenerator idGenerator;
    private final Function<String, LogDecorator> logDecoratorSupplier;

    public TransactionIdFactoryImpl(IdGenerator idGenerator) {
        this(idGenerator, UniqueIdLogDecorator::new);
    }

    public TransactionIdFactoryImpl(IdGenerator idGenerator, Function<String, LogDecorator> logDecoratorSupplier) {
        this.idGenerator = idGenerator;
        this.logDecoratorSupplier = logDecoratorSupplier;
    }

    @Override
    public TransactionId get() {
        return getExisting(idGenerator.get());
    }

    @Override
    public TransactionId getExisting(String tId) {
        return new TransactionId(tId, logDecoratorSupplier.apply(tId));
    }
}
