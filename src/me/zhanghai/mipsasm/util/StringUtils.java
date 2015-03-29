/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.util;

public class StringUtils {

    private StringUtils() {}

    public static boolean isEmpty(String string) {
        return string == null || string.isEmpty();
    }
}
