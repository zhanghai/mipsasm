/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.disassembler;

public class CoeReaderException extends Exception {

    public CoeReaderException() {}

    public CoeReaderException(String message) {
        super(message);
    }

    public CoeReaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public CoeReaderException(Throwable cause) {
        super(cause);
    }
}
