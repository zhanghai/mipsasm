/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.parser;

public class IllegalOperandException extends ParserException {

    public IllegalOperandException() {}

    public IllegalOperandException(String message) {
        super(message);
    }

    public IllegalOperandException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalOperandException(Throwable cause) {
        super(cause);
    }
}
