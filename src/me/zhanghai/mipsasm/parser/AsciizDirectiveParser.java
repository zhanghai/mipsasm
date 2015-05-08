/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.parser;

import me.zhanghai.mipsasm.Constants;
import me.zhanghai.mipsasm.assembler.AssemblyContext;
import me.zhanghai.mipsasm.assembler.StorageDirective;
import me.zhanghai.mipsasm.util.BitArray;

public class AsciizDirectiveParser {

    private AsciizDirectiveParser() {}

    public static void parse(String[] operandStringList, AssemblyContext context) throws ParserException {

        for (String operandString : operandStringList) {

            AsciiDirectiveParser.parse(new String[]{operandString}, context);

            StorageDirective storage;
            storage = StorageDirective.of(BitArray.of(0, Constants.BYTE_LENGTH));
            context.appendAssemblable(storage);
        }
    }
}
