/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.parser;

import me.zhanghai.mipsasm.assembler.Immediate;
import me.zhanghai.mipsasm.assembler.OffsetBase;
import me.zhanghai.mipsasm.assembler.Register;
import me.zhanghai.mipsasm.util.RegexUtils;

import javax.script.ScriptException;
import java.util.regex.Matcher;

public class OffsetBaseParser {

    private static final ThreadLocal<Matcher> MATCHER =
            RegexUtils.makeThreadLocalMatcher("(\\S*)\\s*\\(\\s*(\\S+)\\s*\\)");

    private OffsetBaseParser() {}

    public static OffsetBase parse(String offsetBaseString) throws ParserException {

        Matcher offsetBaseMatcher = MATCHER.get().reset(offsetBaseString);

        if (!offsetBaseMatcher.matches()) {
            throw new IllegalOperandException("Cannot parse offset and base: " + offsetBaseString);
        }

        Immediate offset;
        String offsetString = offsetBaseMatcher.group(1);
        if (offsetString.isEmpty()) {
            offset = Immediate.of(0);
        } else {
            try {
                offset = Immediate.of(JavaScriptParser.parseSignedInteger(offsetString));
            } catch (ScriptException | NumberFormatException e) {
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
