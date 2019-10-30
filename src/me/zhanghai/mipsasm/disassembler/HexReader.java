/*
 * Copyright (c) 2019 Chen Mouxiang <cmx_1007@foxmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.disassembler;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;

import me.zhanghai.mipsasm.util.IoUtils;

import static me.zhanghai.mipsasm.util.UnsignedCompat.parseUnsignedByte;

public class HexReader {

    public static ByteArrayInputStream hexToBytes(InputStream inputStream) throws IOException {
        String vector = IoUtils.inputStreamToString(inputStream).toLowerCase();
        Matcher vectorIgnoreMatcher = CoeReader.VECTOR_IGNORE_MATCHER.get().reset(vector);
        vector = vectorIgnoreMatcher.replaceAll("");
        byte[] bytes = new byte[(int) Math.ceil(vector.length() / 2d)];
        for (int i = 0, j = 0, k = 2; j < vector.length(); ++i, j = k, k += 2) {
            if (k > vector.length()) {
                k = vector.length();
            }
            bytes[i] = parseUnsignedByte(vector.substring(j, k), 16);
        }
        return new ByteArrayInputStream(bytes);
    }

    private HexReader() {}
}
