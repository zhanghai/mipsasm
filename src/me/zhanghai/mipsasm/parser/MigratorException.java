/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.parser;

public class MigratorException extends Exception {

    public MigratorException() {}

    public MigratorException(String message) {
        super(message);
    }

    public MigratorException(String message, Throwable cause) {
        super(message, cause);
    }

    public MigratorException(Throwable cause) {
        super(cause);
    }
}
