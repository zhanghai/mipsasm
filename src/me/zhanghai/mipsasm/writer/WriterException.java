/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.writer;

public class WriterException extends Exception {

    public WriterException() {}

    public WriterException(String message) {
        super(message);
    }

    public WriterException(String message, Throwable cause) {
        super(message, cause);
    }

    public WriterException(Throwable cause) {
        super(cause);
    }
}
