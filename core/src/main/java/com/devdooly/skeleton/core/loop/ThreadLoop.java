package com.devdooly.skeleton.core.loop;

public interface ThreadLoop extends Runnable {

    default String name() {
        String name = getClass().getSimpleName();
        return Character.toLowerCase(name.charAt(0)) + name.substring(1);
    }

    default boolean onVirtual() {
        return true;
    }

    default void go() {
        if (onVirtual()) {
            Thread.ofVirtual()
                    .name(name())
                    .start(this);
        } else {
            Thread.ofPlatform()
                    .name(name())
                    .daemon(false)
                    .start(this);
        }
    }

}
