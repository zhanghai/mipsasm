/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.parser;

import me.zhanghai.mipsasm.Constants;
import me.zhanghai.mipsasm.assembler.AssemblyContext;
import me.zhanghai.mipsasm.assembler.StorageDirective;
import me.zhanghai.mipsasm.util.BitArray;
import me.zhanghai.mipsasm.util.RegexUtils;
import me.zhanghai.mipsasm.util.StringUtils;
import me.zhanghai.mipsasm.util.UnsignedCompat;

import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;

public class AsciiDirectiveParser {

    private static final ThreadLocal<Matcher> STRING_MATCHER = RegexUtils.makeThreadLocalMatcher(
            "(?<!\\\\)" + Tokens.STRING_QUOTATION + "(.*)" + "(?<!\\\\)" + Tokens.STRING_QUOTATION);

    private AsciiDirectiveParser() {}

    public static void parse(String[] operandStringList, AssemblyContext context) throws ParserException {

        if (operandStringList.length == 0) {
            throw new OperandCountMismatchException("Expected: >0; found: 0");
        }

        Matcher stringMatcher = STRING_MATCHER.get();
        for (String operandString : operandStringList) {
            stringMatcher.reset(operandString);
            if (!stringMatcher.matches()) {
                throw new IllegalOperandException("Operand is not a string in double quotation marks: "
                        + operandString);
            }
            String string = stringMatcher.group(1);
            string = StringUtils.unescapeMips(string);
            for (byte b : string.getBytes(StandardCharsets.US_ASCII)) {
                StorageDirective storage;
                storage = StorageDirective.of(BitArray.of(UnsignedCompat.unsignedByteToInt(b), Constants.BYTE_LENGTH));
                context.appendAssemblable(storage);
            }
        }
    }
}
