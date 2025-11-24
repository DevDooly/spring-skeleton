package com.devdooly.skeleton.common.log;

import com.devdooly.skeleton.common.utils.CommonUtils.;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

public class TimestampIdGenerator implements IdGenerator {
    private final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    private final String prefix;
    private final AtomicLong sequence;

    public TimestampIdGenerator(String id) {
        prefix = id + "_";
        sequence = new AtomicInteger(0);
    }

    @Override
    public String get() {
        return prefix + LocalDateTime.now().format(format) + "_S" + getSeq();
    }

    private String getSeq() {
        return CommonUtils.padding(4, '0', Long.toHexString(0xffff & sequence.incrementAndGet()));
    }

}
