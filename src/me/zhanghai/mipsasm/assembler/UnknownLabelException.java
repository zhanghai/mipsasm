/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.assembler;

public class UnknownLabelException extends AssemblerException {

    public UnknownLabelException() {}

    public UnknownLabelException(String message) {
        super(message);
    }

    public UnknownLabelException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownLabelException(Throwable cause) {
        super(cause);
    }
}
