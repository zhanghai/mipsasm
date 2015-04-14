/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.parser;

import me.zhanghai.mipsasm.Constants;
import me.zhanghai.mipsasm.assembler.AssemblyContext;

public class WordDirectiveParser {

    private WordDirectiveParser() {}

    public static void parse(String[] operandStringList, AssemblyContext context) throws ParserException {
        StorageDirectiveParser.parse(operandStringList, context, Constants.WORD_LENGTH);
    }
}
