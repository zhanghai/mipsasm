/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.parser;

public class MultiplePendingLabelException extends ParserException {

    public MultiplePendingLabelException() {}

    public MultiplePendingLabelException(String message) {
        super(message);
    }

    public MultiplePendingLabelException(String message, Throwable cause) {
        super(message, cause);
    }

    public MultiplePendingLabelException(Throwable cause) {
        super(cause);
    }
}
