/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.parser;

public class OperandCountMismatchException extends ParserException {

    public OperandCountMismatchException() {}

    public OperandCountMismatchException(String message) {
        super(message);
    }

    public OperandCountMismatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public OperandCountMismatchException(Throwable cause) {
        super(cause);
    }
}
