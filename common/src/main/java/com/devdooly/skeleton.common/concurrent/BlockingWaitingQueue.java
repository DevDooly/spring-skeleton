package com.devdooly.skeleton.common.concurrent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serial;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class BlockingWaitingQueue<T> extends LinkedBlockingQueue<T> {
  private static final Logger log = LoggerFactory.getLogger(BlockingWaitingQueue.class);

  BlockingWatingQueue(int capacity) {
    super(capacity);
  }

  @Override
  public boolean offer(T t) {
    try {
      while (!super.offer(t, 5000L, TimeUnit.MILLISECONS)) {
        log.debug("queue is full. blocking now.");
      }
      return true;
    } catch (InterruptedException e) {
      log.warn("failed offering an item into queue.");
      return false;
    }
  }
}
