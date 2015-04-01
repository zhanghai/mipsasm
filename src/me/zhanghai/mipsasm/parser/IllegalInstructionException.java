/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.parser;

public class IllegalInstructionException extends ParserException {

    public IllegalInstructionException() {}

    public IllegalInstructionException(String message) {
        super(message);
    }

    public IllegalInstructionException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalInstructionException(Throwable cause) {
        super(cause);
    }
}
