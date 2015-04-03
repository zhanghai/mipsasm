/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.parser;

import me.zhanghai.mipsasm.assembler.AssemblyContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LineParser {

    private static final Pattern COMMENT_PATTERN = Pattern.compile(Tokens.COMMENT_REGEX);
    private static final ThreadLocal<Matcher> COMMENT_MATCHER = new ThreadLocal<Matcher>() {
        @Override
        protected Matcher initialValue() {
            return COMMENT_PATTERN.matcher("");
        }
    };


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
