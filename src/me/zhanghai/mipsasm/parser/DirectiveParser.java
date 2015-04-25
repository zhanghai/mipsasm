/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.parser;

import me.zhanghai.mipsasm.assembler.AssemblyContext;
import me.zhanghai.mipsasm.assembler.DirectiveInformation;
import me.zhanghai.mipsasm.util.RegexUtils;

import java.util.regex.Matcher;

public class DirectiveParser {

    private static final ThreadLocal<Matcher> MATCHER = RegexUtils.makeThreadLocalMatcher("(\\S+)(?:\\s+(.*))?");

    private DirectiveParser() {}

    public static void parse(String directiveString, AssemblyContext context) throws ParserException {

        Matcher matcher = MATCHER.get().reset(directiveString);
        if (!matcher.matches()) {
            throw new IllegalInstructionException("Instruction: " + directiveString);
        }

        String directiveName = matcher.group(1);
        DirectiveInformation directiveInformation;
        try {
            directiveInformation = DirectiveInformation.valueOf(directiveName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new NoSuchDirectiveException("Directive: " + directiveName);
        }

        String operandListString = matcher.group(2);
        String[] operandStringList = ParserSplitUtils.splitOperands(operandListString);
        directiveInformation.parse(operandStringList, context);
    }
}
