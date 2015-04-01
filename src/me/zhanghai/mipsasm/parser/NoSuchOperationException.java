/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.parser;

public class NoSuchOperationException extends ParserException {

    public NoSuchOperationException() {}

    public NoSuchOperationException(String message) {
        super(message);
    }

    public NoSuchOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchOperationException(Throwable cause) {
        super(cause);
    }
}
