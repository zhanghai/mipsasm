/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {

    private RegexUtils() {}

    public static ThreadLocal<Matcher> makeThreadLocalMatcher(final String regex) {
        return new ThreadLocal<Matcher>() {
            @Override
            protected Matcher initialValue() {
                return Pattern.compile(regex).matcher("");
            }
        };
    }

    public static ThreadLocal<Matcher> makeThreadLocalMatcher(final String regex, final int flags) {
        return new ThreadLocal<Matcher>() {
            @Override
            protected Matcher initialValue() {
                return Pattern.compile(regex, flags).matcher("");
            }
        };
    }
}
