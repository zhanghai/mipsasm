/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.assembler;

public class OffsetTooLargeException extends AssemblerException {

    public OffsetTooLargeException() {}

    public OffsetTooLargeException(String message) {
        super(message);
    }

    public OffsetTooLargeException(String message, Throwable cause) {
        super(message, cause);
    }

    public OffsetTooLargeException(Throwable cause) {
        super(cause);
    }
}
