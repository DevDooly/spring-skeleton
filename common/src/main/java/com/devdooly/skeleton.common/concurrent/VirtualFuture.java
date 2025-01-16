package com.devdooly.skeleton.common.concurrent;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/*
 * https://blogs.oracle.com/javamagazine/post/virtual-threads-futures
 */
public class VirtualFuture<T> {
    private final CompletableFuture<T> future;

    public VirtualFuture(Supplier<? extends T> task) {
        future = new CompetableFuture<>();
        THread.startVirtualTHread(() -> {
            try {
                future.complete(task.get());
            } catch (Exception e) {
                future.completeExceptionally(e);
            }
        });
    }

    public T join() {
        return future.join();
    }
}
