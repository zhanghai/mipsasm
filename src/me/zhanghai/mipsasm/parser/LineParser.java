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

        for (String statementString : ParserSplitUtils.splitStatements(line)) {
            statementString = statementString.trim();
            if (statementString.isEmpty()) {
                continue;
            }
            StatementParser.parse(statementString, context);
        }
    }
}
