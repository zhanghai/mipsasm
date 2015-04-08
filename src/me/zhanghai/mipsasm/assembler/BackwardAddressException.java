/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.assembler;

public class BackwardAddressException extends IllegalArgumentException {

    public BackwardAddressException() {}

    public BackwardAddressException(String message) {
        super(message);
    }

    public BackwardAddressException(String message, Throwable cause) {
        super(message, cause);
    }

    public BackwardAddressException(Throwable cause) {
        super(cause);
    }
}
