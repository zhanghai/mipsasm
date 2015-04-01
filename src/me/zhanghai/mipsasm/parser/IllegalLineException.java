/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.parser;

public class IllegalLineException extends ParserException {

    public IllegalLineException() {}

    public IllegalLineException(String message) {
        super(message);
    }

    public IllegalLineException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalLineException(Throwable cause) {
        super(cause);
    }
}
