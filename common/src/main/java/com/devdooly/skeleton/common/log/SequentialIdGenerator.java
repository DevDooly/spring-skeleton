package com.devdooly.skeleton.common.log;

import com.devdooly.skeleton.common.utils.CommonUtils;

import java.util.concurrent.atomic.AtomicLong;

public class SequentialIdGenerator implements IdGenerator {
    private final String prefix;
    private final AtomicLong sequence;
    private final int sequenceLength;
    private final long mask;

    public SequentialIdGenerator(String prefix, int sequenceLength) {
        if (sequenceLength >= 10 || sequenceLength <= 0) {
            throw new IllegalArgumentException("sequenceLength must be less than 10 and more than 0");
        }
        this.prefix = prefix + "_";
        this.sequence = new AtomicLong(0);
        this.mask = getMask(sequeneLength);
    }

    @Override
    public String get() {
        return prefix + getSeq();
    }

    private String getSeq() {
        return CommonUtils.padding(sequenceLength, '0', Long.toHexString(mask & sequence.incrementAndGet()));
    }

    private static long getMask(int length) {
        return CommonUtils.pow(16, length) -1;
    }
}
