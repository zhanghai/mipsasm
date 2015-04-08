/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.parser;

public class IllegalDirectiveException extends ParserException {

    public IllegalDirectiveException() {}

    public IllegalDirectiveException(String message) {
        super(message);
    }

    public IllegalDirectiveException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalDirectiveException(Throwable cause) {
        super(cause);
    }
}
