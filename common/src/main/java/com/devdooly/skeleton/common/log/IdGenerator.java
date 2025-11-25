package com.devdooly.skeleton.common.log;

import java.util.function.Supplier;

@FunctionalInterface
public interface IdGenerator extends Supplier<String> {
}    
