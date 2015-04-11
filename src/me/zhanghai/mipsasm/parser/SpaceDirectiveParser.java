/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.parser;

import me.zhanghai.mipsasm.assembler.AssemblyContext;
import me.zhanghai.mipsasm.assembler.SpaceDirective;
import me.zhanghai.mipsasm.util.IoUtils;

import java.util.Arrays;

public class SpaceDirectiveParser {

    private SpaceDirectiveParser() {}

    public static void parse(String[] operandStringList, AssemblyContext context) throws ParserException {

        if (operandStringList.length != 1) {
            throw new OperandCountMismatchException("Expected: [ByteCount], found: "
                    + Arrays.toString(operandStringList));
        }

        String byteCountString = operandStringList[0];
        SpaceDirective space;
        try {
            space = SpaceDirective.of(IoUtils.decodeUnsignedInt(byteCountString));
        } catch (IllegalArgumentException e) {
            throw new IllegalOperandException("ByteCount: " + byteCountString, e);
        }
        context.appendAssemblable(space);
    }
}
