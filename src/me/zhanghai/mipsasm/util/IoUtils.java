/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.util;

public class IoUtils {

    private IoUtils() {}

    // Parse an integer, result can be either signed or unsigned, the only limitation being the length of int.
    public static int parseSignedInteger(String string) {

        int radix = 10;
        int index = 0;
        boolean negative = false;

        if (string.length() == 0) {
            throw new NumberFormatException("Zero length string");
        }
        char firstChar = string.charAt(0);
        // Handle sign, if present
        if (firstChar == '-') {
            negative = true;
            ++index;
        } else if (firstChar == '+') {
            ++index;
        }

        // Handle radix specifier, if present
        if (string.startsWith("0x", index) || string.startsWith("0X", index)) {
            index += 2;
            radix = 16;
        } else if (string.startsWith("0b", index) || string.startsWith("0B", index)) {
            index += 2;
            radix = 2;
        } else if (string.startsWith("#", index)) {
            ++index;
            radix = 16;
        } else if (string.startsWith("0", index) && string.length() > 1 + index) {
            ++index;
            radix = 8;
        }

        if (string.startsWith("-", index) || string.startsWith("+", index)) {
            throw new NumberFormatException("Sign character in wrong position");
        }

        return Integer.parseInt(negative ? "-" + string.substring(index) : string.substring(index), radix);
    }

    public static int parseUnsignedInteger(String string) {

        if (string.length() == 0) {
            throw new NumberFormatException("Zero length string");
        }

        int index = 0;
        int radix = 10;

        // Handle radix specifier, if present
        if (string.startsWith("0x", index) || string.startsWith("0X", index)) {
            index += 2;
            radix = 16;
        } else if (string.startsWith("0b", index) || string.startsWith("0B", index)) {
            index += 2;
            radix = 2;
        } else if (string.startsWith("#", index)) {
            ++index;
            radix = 16;
        } else if (string.startsWith("0", index) && string.length() > 1 + index) {
            ++index;
            radix = 8;
        }

        if (string.startsWith("-", index) || string.startsWith("+", index)) {
            throw new NumberFormatException("Illegal sign character found");
        }

        return UnsignedCompat.parseUnsignedInt(string.substring(index), radix);
    }

    // Parse an integer, result can be either signed or unsigned, the only limitation being the length of int.
    public static int parseInteger(String string) {

        int radix = 10;
        int index = 0;
        boolean negative = false;

        if (string.length() == 0) {
            throw new NumberFormatException("Zero length string");
        }
        char firstChar = string.charAt(0);
        // Handle sign, if present
        if (firstChar == '-') {
            negative = true;
            ++index;
        } else if (firstChar == '+') {
            ++index;
        }

        // Handle radix specifier, if present
        if (string.startsWith("0x", index) || string.startsWith("0X", index)) {
            index += 2;
            radix = 16;
        } else if (string.startsWith("0b", index) || string.startsWith("0B", index)) {
            index += 2;
            radix = 2;
        } else if (string.startsWith("#", index)) {
            ++index;
            radix = 16;
        } else if (string.startsWith("0", index) && string.length() > 1 + index) {
            ++index;
            radix = 8;
        }

        if (string.startsWith("-", index) || string.startsWith("+", index)) {
            throw new NumberFormatException("Sign character in wrong position");
        }

        if (negative) {
            return Integer.parseInt("-" + string.substring(index), radix);
        } else {
            return UnsignedCompat.parseUnsignedInt(string.substring(index), radix);
        }
    }

    public static String wordToBinaryString(int integer) {
        return String.format("%32s", Integer.toBinaryString(integer)).replace(' ', '0');
    }

    public static String wordToHexString(int integer) {
        return String.format("%08X", integer);
    }
}
