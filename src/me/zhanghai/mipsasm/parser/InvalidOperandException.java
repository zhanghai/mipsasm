/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.parser;

public class InvalidOperandException extends IllegalFormatException {

    public InvalidOperandException() {}

    public InvalidOperandException(String message) {
        super(message);
    }

    public InvalidOperandException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidOperandException(Throwable cause) {
        super(cause);
    }
}
