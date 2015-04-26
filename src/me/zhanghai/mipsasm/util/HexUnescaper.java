/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.util;

import org.apache.commons.lang3.text.translate.CharSequenceTranslator;

import java.io.IOException;
import java.io.Writer;

public class HexUnescaper extends CharSequenceTranslator {

    @Override
    public int translate(final CharSequence input, final int index, final Writer out) throws IOException {

        int remaining = input.length() - index - 1; // how many characters left, ignoring the leading \
        StringBuilder builder = new StringBuilder();

        if(input.charAt(index) == '\\' && remaining > 0 && isX(input.charAt(index + 1))) {

            int next2 = index + 2;
            int next3 = index + 3;

            if (remaining > 1 && isHexDigit(input.charAt(next2))) {
                builder.append(input.charAt(next2));
                if(remaining > 2 && isHexDigit(input.charAt(next3))) {
                    builder.append(input.charAt(next3));
                }
            }

            out.write(Integer.parseInt(builder.toString(), 16));
            return 2 + builder.length();
        }
        return 0;
    }

    private boolean isX(char ch) {
        return ch == 'x' || ch == 'X';
    }

    private boolean isHexDigit(char ch) {
        return ch >= '0' && ch <= '7';
    }
}
