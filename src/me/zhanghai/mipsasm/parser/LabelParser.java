/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.parser;

import me.zhanghai.mipsasm.assembler.AssemblyContext;
import me.zhanghai.mipsasm.util.RegexUtils;

import java.util.regex.Matcher;

public class LabelParser {

    private static final ThreadLocal<Matcher> MATCHER = RegexUtils.makeThreadLocalMatcher(Tokens.IDENTIFIER_REGEX);

    private LabelParser() {}

    public static void parse(String labelString, AssemblyContext context) throws ParserException {

        Matcher matcher = MATCHER.get().reset(labelString);
        if (!matcher.matches()) {
            throw new IllegalLabelException("Label: " + labelString);
        }

        context.setPendingLabel(labelString);
    }
}
