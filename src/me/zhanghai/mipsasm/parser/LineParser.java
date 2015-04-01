/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.parser;

import me.zhanghai.mipsasm.assembler.AssemblyContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LineParser {

    private static final String GROUP_LABEL = "label";
    private static final String GROUP_INSTRUCTION = "instruction";
    private static final Pattern PATTERN = Pattern.compile(
            "(?:(?<" + GROUP_LABEL + ">\\S+):)?\\s*(?<" + GROUP_INSTRUCTION + ">[^#]+)?\\s*(?:#.*)?");

    private static final ThreadLocal<Matcher> MATCHER = new ThreadLocal<Matcher>() {
        @Override
        protected Matcher initialValue() {
            return PATTERN.matcher("");
        }
    };


    private LineParser() {}

    public static void parse(String line, AssemblyContext context) throws ParserException {

        line = line.trim();

        Matcher matcher = MATCHER.get();
        matcher.reset(line);

        if (!matcher.matches()) {
            throw new IllegalLineException("Line: " + line);
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
