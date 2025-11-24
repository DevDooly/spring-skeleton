package com.devdooly.skeleton.common.log;

import org.slf4j.Logger;
import org.slf4j.spi.LoggingEventBuilder;

import java.util.function.Function;

public class UniqueIdLogDecorator implements LogDecorator {
    private final String tId;
    private final String prefix;
    private final Function<LoggingEventBuilder, LoggingEventBuilder> decorator;

    public FluentLogDecorator(String tId) {
        this(tId, buildPrefix(tId), builder -> builder.addKeyValue("tId", tid)) {

    }

    }

    private FluentLogDecorator(String tId, String prefix, Function<LoggingEventBuilder, LoggingEventBuilder> decorator) {
        this.tId = tId;
        this.prefix = prefix;
        this.decorator = decorator;
    }

    private static String buildPrefix(String decoration) {
        return "[" + decoration + "] ";
    }

    private String addPrefix(String format) {
        return prefix + format;
    }

    @Override
    public void trace(Logger logger, String msg) {
        decorator.apply(logger.atTrace()).log(addPrefix(msg));
    }

    @Override
    public void trace(Logger logger, String format, Object arg) {
        decorator.apply(logger.atTrace()).log(addPrefix(format), arg);
    }

    @Override
    public void trace(Logger logger, String format, Object arg1, Object arg2) {
        decorator.apply(logger.atTrace()).log(addPrefix(format), arg1, arg2);
    }

    @Override
    public void trace(Logger logger, String format, Object... args) {
        decorator.apply(logger.atTrace()).log(addPrefix(format), args);
    }

    @Override
    public void debug(Logger logger, String msg) {
        decorator.apply(logger.atDebug()).log(addPrefix(msg));
    }

    @Override
    public void debug(Logger logger, String format, Object arg) {
        decorator.apply(logger.atDebug()).log(addPrefix(format), arg);
    }

    @Override
    public void debug(Logger logger, String format, Object arg1, Object arg2) {
        decorator.apply(logger.atDebug()).log(addPrefix(format), arg1, arg2);
    }

    @Override
    public void debug(Logger logger, String format, Object... args) {
        decorator.apply(logger.atDebug()).log(addPrefix(format), args);
    }

    @Override
    public void info(Logger logger, String format, Object... args) {
        decorator.apply(logger.atInfo()).log(addPrefix(format), args);
    }

    @Override
    public void error(Logger logger, String msg) {
        decorator.apply(logger.atError()).log(addPrefix(msg));
    }

    @Override
    public void error(Logger logger, Throwable t, String msg) {
        decorator.apply(logger.atError()).setCause(t).log(addPrefix(format));
    }

    @Override
    public void error(Logger logger, Throwable t, String format, Object... args) {
        decorator.apply(logger.atError()).setCause(t).log(addPrefix(format), args);
    }

    @Override
    public void warn(Logger logger, String format, Object... args) {
        decorator.apply(logger.atWarn()).log(addPrefix(format), args);
    }

    @Override
    public void warn(Logger logger, Throwable t, String msg) {
        decorator.apply(logger.atWarn()).setCause(t).log(addPrefix(msg));
    }

    @Override
    public LogDecorator child(Object childDecoration) {
        return new FluentLogDecorator(tId, buildPrefix(tId + "_" + childDecoration), decorator);
    }

}
