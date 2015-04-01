/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.parser;

public class MissingOperationException extends ParserException {

    public MissingOperationException() {}

    public MissingOperationException(String message) {
        super(message);
    }

    public MissingOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public MissingOperationException(Throwable cause) {
        super(cause);
    }
}
