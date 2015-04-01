/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.assembler;

public class UndefinedLabelException extends AssemblerException {

    public UndefinedLabelException() {}

    public UndefinedLabelException(String message) {
        super(message);
    }

    public UndefinedLabelException(String message, Throwable cause) {
        super(message, cause);
    }

    public UndefinedLabelException(Throwable cause) {
        super(cause);
    }
}
