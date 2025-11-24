package com.devdooly.skeleton.common.concurrent;

import org.junit.Test;

public class VirtualFutureTest {

    private String process() {
        return "ok";
    }

    @Test
    public void test() {
        VirtualFuture<String> result = new VirtualFuture<>(this::process);
        System.out.println(result.join());
    }
}
