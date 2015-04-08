/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.util;

public class StringUtils {

    public static final String[] EMPTY_ARRAY = new String[0];

    private StringUtils() {}

    public static boolean isEmpty(String string) {
        return string == null || string.isEmpty();
    }

    public static String[] splitAndTrim(String string, String regex) {
        String[] splitStrings = string.split(regex);
        for (int i = 0; i < splitStrings.length; ++i) {
            splitStrings[i] = splitStrings[i].trim();
        }
        if (splitStrings.length == 1 && splitStrings[0].isEmpty()) {
            return EMPTY_ARRAY;
        }
        return splitStrings;
    }
}
