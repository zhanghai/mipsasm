/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.parser;

import me.zhanghai.mipsasm.assembler.Immediate;
import me.zhanghai.mipsasm.assembler.OffsetBase;
import me.zhanghai.mipsasm.assembler.Register;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OffsetBaseParser {

    private static final Pattern PATTERN = Pattern.compile("(\\S*)\\s*\\(\\s*(\\S+)\\s*\\)");

    private static final ThreadLocal<Matcher> MATCHER = new ThreadLocal<Matcher>() {
        @Override
        protected Matcher initialValue() {
            return PATTERN.matcher("");
        }
    };


    private OffsetBaseParser() {}

    public static OffsetBase parse(String offsetBaseString) throws ParserException {

        Matcher offsetBaseMatcher = MATCHER.get();
        offsetBaseMatcher.reset(offsetBaseString);

        if (!offsetBaseMatcher.matches()) {
            throw new IllegalOperandException("Cannot parse offset and base: " + offsetBaseString);
        }

        Immediate offset;
        String offsetString = offsetBaseMatcher.group(1);
        if (offsetString.isEmpty()) {
            offset = Immediate.of(0);
        } else {
            try {
                offset = Immediate.of(Integer.parseInt(offsetString));
            } catch (NumberFormatException e) {
                throw new IllegalOperandException("Offset cannot be parsed: " + offsetString, e);
            } catch (IllegalArgumentException e) {
                throw new IllegalOperandException("Offset length to long: " + offsetString, e);
            }
        }

        Register base;
        String baseString = offsetBaseMatcher.group(2);
        base = RegisterParser.parse(baseString);

        return OffsetBase.of(base, offset);
    }
}
