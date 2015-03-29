/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.parser;

public class InvalidInstructionException extends IllegalFormatException {

    public InvalidInstructionException() {}

    public InvalidInstructionException(String message) {
        super(message);
    }

    public InvalidInstructionException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidInstructionException(Throwable cause) {
        super(cause);
    }
}
