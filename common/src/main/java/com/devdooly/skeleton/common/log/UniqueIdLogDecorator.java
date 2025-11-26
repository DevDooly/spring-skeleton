package com.devdooly.skeleton.common.log;

import org.slf4j.Logger;

import java.text.MessageFormat;

public class UniqueIdLogDecorator implements LogDecorator {
    private final Object decoration;
    private final String prefix;

    public UniqueIdLogDecorator(Object decoration) {
        this.decoration = decoration;
        this.prefix = "[" + decoration + "] ";
    }

    private String formatting(String format) {
        return prefix + format;
    }

    @Override
    public void trace(Logger logger, String msg) {
        if (logger.isTraceEnabled())
            logger.trace(formatting(msg));
    }

    @Override
    public void trace(Logger logger, String format, Object arg) {
        if (logger.isTraceEnabled())
            logger.trace(formatting(format), arg);
    }

    @Override
    public void trace(Logger logger, String format, Object arg1, Object arg2) {
        if (logger.isTraceEnabled())
            logger.trace(formatting(format), arg1, arg2);
    }

    @Override
    public void trace(Logger logger, String format, Object... args) {
        if (logger.isTraceEnabled())
            logger.trace(formatting(format), args);
    }

    @Override
    public void debug(Logger logger, String msg) {
        if (logger.isDebugEnabled())
            logger.debug(formatting(msg));
    }

    @Override
    public void debug(Logger logger, String format, Object arg) {
        if (logger.isDebugEnabled())
            logger.debug(formatting(format), arg);
    }

    @Override
    public void debug(Logger logger, String format, Object arg1, Object arg2) {
        if (logger.isDebugEnabled())
            logger.debug(formatting(format), arg1, arg2);
    }

    @Override
    public void debug(Logger logger, String format, Object... args) {
        if (logger.isDebugEnabled())
            logger.debug(formatting(format), args);
    }

    @Override
    public void info(Logger logger, String format, Object... args) {
        if (logger.isInfoEnabled())
            logger.info(formatting(format), args);

    }

    @Override
    public void error(Logger logger, String msg) {
        if (logger.isErrorEnabled())
            logger.error(formatting(msg));

    }

    @Override
    public void error(Logger logger, Throwable t, String msg) {
        if (logger.isErrorEnabled())
            logger.error(formatting(msg), t);
    }

    @Override
    public void error(Logger logger, Throwable t, String format, Object... args) {
        if (logger.isErrorEnabled())
            logger.error(MessageFormat.format(formatting(format), args), t);
    }

    @Override
    public void warn(Logger logger, String format, Object... args) {
        if (logger.isWarnEnabled())
            logger.warn(formatting(format), args);
    }

    @Override
    public void warn(Logger logger, Throwable t, String msg) {
        if (logger.isWarnEnabled())
            logger.warn(formatting(msg), t);
    }

    @Override
    public LogDecorator child(Object childDecoration) {
        return new UniqueIdLogDecorator(decoration + "_" + childDecoration);
    }
}
