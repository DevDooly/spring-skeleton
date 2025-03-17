package com.devdooly.skeleton.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class ByteUtilsTest {

    @Test
    public void compressTest() {
        String origin = sampleString();
        byte[] bytes = origin.getBytes();
        byte[] compressed = ByteUtils.compress(bytes);

        log.info("origin length : {}", bytes.length);
        log.info("compressed length : {}", compressed.length);
    }

    private String sampleString() {
        return """
                hello java,
                hello skeleton
                """;
    }

}
