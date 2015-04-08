/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.parser;

public class NoSuchDirectiveException extends ParserException {

    public NoSuchDirectiveException() {}

    public NoSuchDirectiveException(String message) {
        super(message);
    }

    public NoSuchDirectiveException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchDirectiveException(Throwable cause) {
        super(cause);
    }
}
