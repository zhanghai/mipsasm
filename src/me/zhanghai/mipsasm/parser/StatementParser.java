/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.parser;

import me.zhanghai.mipsasm.assembler.AssemblyContext;
import me.zhanghai.mipsasm.util.RegexUtils;

import java.util.regex.Matcher;

public class StatementParser {

    private static final String GROUP_LABEL = "label";
    private static final String GROUP_DIRECTIVE_OR_INSTRUCTION = "directiveOrInstruction";
    private static final ThreadLocal<Matcher> MATCHER = RegexUtils.makeThreadLocalMatcher(
            "(?:(?<" + GROUP_LABEL + ">\\S+):)?\\s*(?<" + GROUP_DIRECTIVE_OR_INSTRUCTION + ">.+)?");

    private static final String DIRECTIVE_PREFIX = ".";

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

        String directiveOrInstructionString = matcher.group(GROUP_DIRECTIVE_OR_INSTRUCTION);
        if (directiveOrInstructionString != null) {
            if (directiveOrInstructionString.startsWith(DIRECTIVE_PREFIX)) {
                String directiveString = directiveOrInstructionString.substring(DIRECTIVE_PREFIX.length());
                DirectiveParser.parse(directiveString, context);
            } else {
                String instructionString = directiveOrInstructionString;
                InstructionParser.parse(instructionString, context);
            }
        }
    }
}
