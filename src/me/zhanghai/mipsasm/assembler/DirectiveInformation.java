/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.assembler;

import me.zhanghai.mipsasm.parser.*;

public enum  DirectiveInformation {

    TEXT,
    DATA,
    ASCII,
    ASCIIZ,
    BYTE,
    HALF,
    WORD,
    SPACE;

    public void parse(String[] operandStringList, AssemblyContext context) throws ParserException {
        switch (this) {
            case TEXT:
                TextDirectiveParser.parse(operandStringList, context);
                break;
            case DATA:
                DataDirectiveParser.parse(operandStringList, context);
                break;
            case ASCII:
                AsciiParser.parse(operandStringList, context);
                break;
            case ASCIIZ:
                AsciizParser.parse(operandStringList, context);
                break;
            case BYTE:
                ByteDirectiveParser.parse(operandStringList, context);
                break;
            case HALF:
                HalfWordDirectiveParser.parse(operandStringList, context);
                break;
            case WORD:
                WordDirectiveParser.parse(operandStringList, context);
                break;
            case SPACE:
                SpaceDirectiveParser.parse(operandStringList, context);
                break;
            default:
                throw new IllegalStateException("Parser not found: " + this);
        }
    }
}
