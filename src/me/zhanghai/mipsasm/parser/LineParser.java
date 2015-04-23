/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.parser;

import me.zhanghai.mipsasm.assembler.AssemblyContext;
import me.zhanghai.mipsasm.util.RegexUtils;

import java.util.regex.Matcher;

public class LineParser {

    private static final ThreadLocal<Matcher> COMMENT_MATCHER = RegexUtils.makeThreadLocalMatcher(Tokens.COMMENT_REGEX);

    private LineParser() {}

    public static void parse(String line, AssemblyContext context) throws ParserException {

        line = COMMENT_MATCHER.get().reset(line).replaceAll("");

        for (String statementString : line.split(Tokens.STATEMENT_SEPARATOR_REGEX)) {
            statementString = statementString.trim();
            if (!statementString.isEmpty()) {
                StatementParser.parse(statementString, context);
            }
        }
    }
}
