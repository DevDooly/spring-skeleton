package com.devdooly.skeleton.common.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.Semaphore;

public class SemaConcurrencyLimiter implements ConcurrencyLimiter {
  private final Semaphore sem;

  public SemaConcurrencyLimiter(int maxConcurrency) {
    this.sem = new Semaphore(maxConcurrency);
  }

  @Override
  public Runnable wrap(Runnable runnable) {
    return () -> {
      sem.acquireUninterruptibly();
      try {
        runnable.run();
      } finally {
        sem.release();
      }
    };
  }

  @Override
  public <T> Callable<T> wrap(Callable<T> callable) {
    return () -> {
      sem.acquireUninterruptibly();
      try {
        return callable.call();
      } finally {
        sem.release();
      }
    };
  }

}
