/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.parser;

import me.zhanghai.mipsasm.assembler.AssemblyContext;
import me.zhanghai.mipsasm.assembler.DirectiveInformation;
import me.zhanghai.mipsasm.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DirectiveParser {

    private static final Pattern PATTERN = Pattern.compile("(\\S+)(?:\\s+(.*))?");

    private static final ThreadLocal<Matcher> MATCHER = new ThreadLocal<Matcher>() {
        @Override
        protected Matcher initialValue() {
            return PATTERN.matcher("");
        }
    };

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
        String[] operandStringList = operandListString != null ?
                StringUtils.splitAndTrim(operandListString, Tokens.OPERAND_SEPARATOR_REGEX) : new String[0];
        directiveInformation.parse(operandStringList, context);
    }
}
