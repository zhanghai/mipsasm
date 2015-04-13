/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.writer;

import me.zhanghai.mipsasm.assembler.AssemblyContext;

import java.io.OutputStream;

public enum Writer {

    BINARY,
    COE,
    DEBUG,
    HEXDEBUG;

    public void write(OutputStream outputStream, AssemblyContext context) throws WriterException {
        switch (this) {
            case BINARY:
                BinaryWriter.write(outputStream, context);
                break;
            case COE:
                CoeWriter.write(outputStream, context);
                break;
            case DEBUG:
                DebugWriter.write(outputStream, context);
                break;
            case HEXDEBUG:
                HexDebugWriter.write(outputStream, context);
                break;
        }
    }
}
