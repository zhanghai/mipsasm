/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.parser;

public class IllegalLabelException extends ParserException {

    public IllegalLabelException() {}

    public IllegalLabelException(String message) {
        super(message);
    }

    public IllegalLabelException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalLabelException(Throwable cause) {
        super(cause);
    }
}
