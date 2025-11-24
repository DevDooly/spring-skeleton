package com.devdooly.skeleton.common.log;

import org.slf4j.Logger;

public interface LogDecorator {
    void trace(Logger logger, String msg);
    void trace(Logger logger, String format, Object arg);
    void trace(Logger logger, String format, Object arg1, Object arg2);
    void trace(Logger logger, String format, Object... args);
    void debug(Logger logger, String msg);
    void debug(Logger logger, String format, Object arg);
    void debug(Logger logger, String format, Object arg1, Object arg2);
    void debug(Logger logger, String format, Object... args);
    void info(Logger logger, String format, Object... args);
    void error(Logger logger, String msg);
    void error(Logger logger, Throwable t, String msg);
    void error(Logger logger, Throwable t, String format, Object... args);
    void warn(Logger logger, String format, Object... args);
    void warn(Logger logger, Throwable t, String msg);
    LogDecorator child(Object childDecoration);
}
