package com.devdooly.skeleton.common.concurrent;

import java.util.concurrent.Callable;

public interface ConcurrencyLimiter {
  Runnable wrap(Runnable runnable);

  <T> Callable<T> wrap(Callable<T> callable);
}
