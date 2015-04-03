/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.parser;

public class IllegalStatementException extends ParserException {

    public IllegalStatementException() {}

    public IllegalStatementException(String message) {
        super(message);
    }

    public IllegalStatementException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalStatementException(Throwable cause) {
        super(cause);
    }
}
