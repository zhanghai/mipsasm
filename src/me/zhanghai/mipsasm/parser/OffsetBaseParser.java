/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.parser;

import me.zhanghai.mipsasm.instruction.Immediate;
import me.zhanghai.mipsasm.instruction.OffsetBase;
import me.zhanghai.mipsasm.instruction.Register;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OffsetBaseParser {

    private static final Pattern OFFSET_BASE_PATTERN = Pattern.compile(
            "(.*)\\((.+)\\)");

    private static final ThreadLocal<Matcher> OFFSET_BASE_MATCHER = new ThreadLocal<Matcher>() {
        @Override
        protected Matcher initialValue() {
            return OFFSET_BASE_PATTERN.matcher("");
        }
    };

    private OffsetBaseParser() {}

    public static OffsetBase parse(String offsetBaseString) {

        Matcher offsetBaseMatcher = OFFSET_BASE_MATCHER.get();
        offsetBaseMatcher.reset(offsetBaseString);

        if (!offsetBaseMatcher.matches()) {
            throw new InvalidOperandException("Cannot parse offset and base: " + offsetBaseString);
        }

        Immediate offset;
        String offsetString = offsetBaseMatcher.group(1);
        if (offsetString.isEmpty()) {
            offset = Immediate.of(0);
        } else {
            try {
                offset = Immediate.of(Integer.parseInt(offsetString));
            } catch (NumberFormatException e) {
                throw new InvalidOperandException("Offset cannot be parsed: " + offsetString, e);
            } catch (IllegalArgumentException e) {
                throw new InvalidOperandException("Offset length to long: " + offsetString, e);
            }
        }

        Register base;
        String baseString = offsetBaseMatcher.group(2);
        base = RegisterParser.parse(baseString);

        return OffsetBase.of(base, offset);
    }
}
