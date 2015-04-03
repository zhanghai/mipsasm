/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.parser;

import me.zhanghai.mipsasm.assembler.AssemblyContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StatementParser {

    private static final String GROUP_LABEL = "label";
    private static final String GROUP_INSTRUCTION = "instructions";
    private static final Pattern PATTERN = Pattern.compile(
            "(?:(?<" + GROUP_LABEL + ">\\S+):)?\\s*(?<" + GROUP_INSTRUCTION + ">.+)?");
    private static final ThreadLocal<Matcher> MATCHER = new ThreadLocal<Matcher>() {
        @Override
        protected Matcher initialValue() {
            return PATTERN.matcher("");
        }
    };


    private StatementParser() {}

    public static void parse(String statement, AssemblyContext context) throws ParserException {

        Matcher matcher = MATCHER.get().reset(statement);

        if (!matcher.matches()) {
            throw new IllegalStatementException("Statement: " + statement);
        }

        String labelString = matcher.group(GROUP_LABEL);
        if (labelString != null) {
            LabelParser.parse(labelString, context);
        }

        String instructionString = matcher.group(GROUP_INSTRUCTION);
        if (instructionString != null) {
            InstructionParser.parse(instructionString, context);
        }
    }
}
