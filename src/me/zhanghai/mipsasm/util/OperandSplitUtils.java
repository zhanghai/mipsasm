/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.util;

import me.zhanghai.mipsasm.parser.Tokens;

import java.util.regex.Matcher;

public class OperandSplitUtils {

    private static final String SPLIT_DEFAULT_REGEX = "\\s*" + Tokens.OPERAND_SEPARATOR_REGEX + "\\s*";
    private static final String[] SPLIT_DEFAULT_QUOTES_REGEX = new String[] {"\"", "'"};
    private static final String[] SPLIT_DEFAULT_LEFT_BRACKET_REGEX = new String[] {"\\(", "\\[", "\\{"};
    private static final String[] SPLIT_DEFAULT_RIGHT_BRACKET_REGEX = new String[] {"\\)", "\\]", "\\}"};
    private static final ThreadLocal<Matcher> SPLIT_DEFAULT_DELIMITER_MATCHER = new ThreadLocal<Matcher>() {
        @Override
        protected Matcher initialValue() {
            return StringUtils.prepareMatcherForSplit(SPLIT_DEFAULT_REGEX, SPLIT_DEFAULT_QUOTES_REGEX,
                    SPLIT_DEFAULT_LEFT_BRACKET_REGEX, SPLIT_DEFAULT_RIGHT_BRACKET_REGEX);
        }
    };

    public static final String[] EMPTY_ARRAY = new String[] {};

    private OperandSplitUtils() {}

    public static String[] split(String string) {
        if (string == null) {
            return EMPTY_ARRAY;
        }
        String[] result = StringUtils.split(string, SPLIT_DEFAULT_REGEX, SPLIT_DEFAULT_QUOTES_REGEX,
                SPLIT_DEFAULT_LEFT_BRACKET_REGEX, SPLIT_DEFAULT_RIGHT_BRACKET_REGEX,
                SPLIT_DEFAULT_DELIMITER_MATCHER.get());
        if (result.length == 1 && result[0].isEmpty()) {
            return EMPTY_ARRAY;
        } else {
            return result;
        }
    }
}
