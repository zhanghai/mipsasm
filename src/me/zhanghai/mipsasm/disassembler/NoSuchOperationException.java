/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.disassembler;

public class NoSuchOperationException extends DisassemblerException {

    public NoSuchOperationException() {}

    public NoSuchOperationException(String message) {
        super(message);
    }

    public NoSuchOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchOperationException(Throwable cause) {
        super(cause);
    }
}
