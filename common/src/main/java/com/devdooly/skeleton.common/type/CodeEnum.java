package com.devdooly.skeleton.common.type;

public interface CodeEnum {
    String name();

    default String code() {
        return name();
    }
}
