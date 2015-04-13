/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.assembler;

import me.zhanghai.mipsasm.InternalException;

public class OperandNotFoundException extends InternalException {

    public OperandNotFoundException() {}

    public OperandNotFoundException(String message) {
        super(message);
    }

    public OperandNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public OperandNotFoundException(Throwable cause) {
        super(cause);
    }
}
