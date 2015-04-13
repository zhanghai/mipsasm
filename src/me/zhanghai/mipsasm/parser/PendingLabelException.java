/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.parser;

public class PendingLabelException extends ParserException {

    public PendingLabelException() {}

    public PendingLabelException(String message) {
        super(message);
    }

    public PendingLabelException(String message, Throwable cause) {
        super(message, cause);
    }

    public PendingLabelException(Throwable cause) {
        super(cause);
    }
}
