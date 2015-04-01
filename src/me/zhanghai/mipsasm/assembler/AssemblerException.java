/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.assembler;

public class AssemblerException extends Exception {

    public AssemblerException() {}

    public AssemblerException(String message) {
        super(message);
    }

    public AssemblerException(String message, Throwable cause) {
        super(message, cause);
    }

    public AssemblerException(Throwable cause) {
        super(cause);
    }
}
