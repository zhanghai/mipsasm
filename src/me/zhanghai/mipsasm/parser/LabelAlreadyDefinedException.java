/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.parser;

public class LabelAlreadyDefinedException extends ParserException {

    public LabelAlreadyDefinedException() {}

    public LabelAlreadyDefinedException(String message) {
        super(message);
    }

    public LabelAlreadyDefinedException(String message, Throwable cause) {
        super(message, cause);
    }

    public LabelAlreadyDefinedException(Throwable cause) {
        super(cause);
    }
}
