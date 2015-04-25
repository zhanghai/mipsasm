/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.parser;

import me.zhanghai.mipsasm.util.StringUtils;

import java.util.regex.Matcher;

public class ParserSplitUtils {

    private static final String SPLIT_DEFAULT_STATEMENT_REGEX = "\\s*" + Tokens.STATEMENT_SEPARATOR_REGEX + "\\s*";
    private static final String SPLIT_DEFAULT_OPERAND_REGEX = "\\s*" + Tokens.OPERAND_SEPARATOR_REGEX + "\\s*";
    private static final String[] SPLIT_DEFAULT_QUOTES_REGEX = new String[] {"\"", "'"};
    private static final String[] SPLIT_DEFAULT_LEFT_BRACKET_REGEX = new String[] {"\\(", "\\[", "\\{"};
    private static final String[] SPLIT_DEFAULT_RIGHT_BRACKET_REGEX = new String[] {"\\)", "\\]", "\\}"};
    private static final ThreadLocal<Matcher> SPLIT_DEFAULT_STATEMENT_DELIMITER_MATCHER = new ThreadLocal<Matcher>() {
        @Override
        protected Matcher initialValue() {
            return StringUtils.prepareMatcherForSplit(SPLIT_DEFAULT_STATEMENT_REGEX, SPLIT_DEFAULT_QUOTES_REGEX,
                    SPLIT_DEFAULT_LEFT_BRACKET_REGEX, SPLIT_DEFAULT_RIGHT_BRACKET_REGEX);
        }
    };
    private static final ThreadLocal<Matcher> SPLIT_DEFAULT_OPERAND_DELIMITER_MATCHER = new ThreadLocal<Matcher>() {
        @Override
        protected Matcher initialValue() {
            return StringUtils.prepareMatcherForSplit(SPLIT_DEFAULT_OPERAND_REGEX, SPLIT_DEFAULT_QUOTES_REGEX,
                    SPLIT_DEFAULT_LEFT_BRACKET_REGEX, SPLIT_DEFAULT_RIGHT_BRACKET_REGEX);
        }
    };

    public static final String[] EMPTY_ARRAY = new String[] {};

    private ParserSplitUtils() {}

    private static String[] trimEmptyArray(String[] array) {
        if (array.length == 1 && array[0].isEmpty()) {
            return EMPTY_ARRAY;
        } else {
            return array;
        }
    }

    public static String[] splitStatements(String string) {
        if (string == null) {
            return EMPTY_ARRAY;
        }
        return trimEmptyArray(StringUtils.split(string, SPLIT_DEFAULT_STATEMENT_REGEX, SPLIT_DEFAULT_QUOTES_REGEX,
                SPLIT_DEFAULT_LEFT_BRACKET_REGEX, SPLIT_DEFAULT_RIGHT_BRACKET_REGEX,
                SPLIT_DEFAULT_STATEMENT_DELIMITER_MATCHER.get()));
    }

    public static String[] splitOperands(String string) {
        if (string == null) {
            return EMPTY_ARRAY;
        }
        return trimEmptyArray(StringUtils.split(string, SPLIT_DEFAULT_OPERAND_REGEX, SPLIT_DEFAULT_QUOTES_REGEX,
                SPLIT_DEFAULT_LEFT_BRACKET_REGEX, SPLIT_DEFAULT_RIGHT_BRACKET_REGEX,
                SPLIT_DEFAULT_OPERAND_DELIMITER_MATCHER.get()));
    }
}
