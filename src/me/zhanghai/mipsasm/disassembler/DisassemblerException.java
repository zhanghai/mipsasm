/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.disassembler;

public class DisassemblerException extends Exception {

    public DisassemblerException() {}

    public DisassemblerException(String message) {
        super(message);
    }

    public DisassemblerException(String message, Throwable cause) {
        super(message, cause);
    }

    public DisassemblerException(Throwable cause) {
        super(cause);
    }
}
