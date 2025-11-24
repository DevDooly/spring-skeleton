package com.devdooly.skeleton.common.log;

public interface TransactionIdFactory {
    TransactionId get();

    TransactionId getExisting(String tId);
}
