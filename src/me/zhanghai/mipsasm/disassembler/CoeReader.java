/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.disassembler;

import me.zhanghai.mipsasm.util.IoUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CoeReader {

    private static String GROUP_RADIX = "radix";
    private static Pattern RADIX_PATTERN = Pattern.compile(
            "memory_initialization_radix\\s*=\\s*(?<" + GROUP_RADIX + ">.*?)\\s*;");
    private static ThreadLocal<Matcher> RADIX_MATCHER = new ThreadLocal<Matcher>() {
        @Override
        protected Matcher initialValue() {
            return RADIX_PATTERN.matcher("");
        }
    };

    private static String GROUP_VECTOR = "vector";
    private static Pattern VECTOR_PATTERN = Pattern.compile(
            "memory_initialization_vector\\s*=\\s*(?<" + GROUP_VECTOR + ">.*?)\\s*;");
    private static ThreadLocal<Matcher> VECTOR_MATCHER = new ThreadLocal<Matcher>() {
        @Override
        protected Matcher initialValue() {
            return VECTOR_PATTERN.matcher("");
        }
    };

    private static Pattern VECTOR_IGNORE_PATTERN = Pattern.compile("[,\\s]+");
    private static ThreadLocal<Matcher> VECTOR_IGNORE_MATCHER = new ThreadLocal<Matcher>() {
        @Override
        protected Matcher initialValue() {
            return VECTOR_IGNORE_PATTERN.matcher("");
        }
    };

    public static ByteArrayInputStream coeToBytes(InputStream inputStream) throws IOException, CoeReaderException {

        String input = IoUtils.inputStreamToString(inputStream).toLowerCase();
        Matcher radixMatcher = RADIX_MATCHER.get().reset(input);
        if (!radixMatcher.find()) {
            throw new CoeReaderException("Radix not found");
        }
        int radix;
        try {
            radix = Integer.parseInt(radixMatcher.group(GROUP_RADIX));
        } catch (NumberFormatException e) {
            throw new CoeReaderException("Cannot parse radix", e);
        }
        Matcher vectorMatcher = VECTOR_MATCHER.get().reset(input);
        if (!vectorMatcher.find()) {
            throw new CoeReaderException("Vector not found");
        }
        String vector = vectorMatcher.group(GROUP_VECTOR);
        Matcher vectorIgnoreMatcher = VECTOR_IGNORE_MATCHER.get().reset(vector);
        vector = vectorIgnoreMatcher.replaceAll("");

        byte[] bytes;
        switch (radix) {
            case 2:
                bytes = new byte[(int) Math.ceil(vector.length() / 8d)];
                for (int i = 0, j = 0, k = 8; j < vector.length(); ++i, j = k, k += 8) {
                    if (k > vector.length()) {
                        k = vector.length();
                    }
                    bytes[i] = Byte.parseByte(vector.substring(j, k));
                }
                break;
            case 16:
                bytes = new byte[(int) Math.ceil(vector.length() / 2d)];
                for (int i = 0, j = 0, k = 2; j < vector.length(); ++i, j = k, k += 2) {
                    if (k > vector.length()) {
                        k = vector.length();
                    }
                    bytes[i] = Byte.parseByte(vector.substring(j, k));
                }
                break;
            default:
                throw new CoeReaderException("Unsupported radix: " + radix);
        }
        return new ByteArrayInputStream(bytes);
    }
}
