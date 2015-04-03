/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.parser;

import me.zhanghai.mipsasm.assembler.AssemblyContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LabelParser {

    private static final Pattern PATTERN = Pattern.compile(Tokens.IDENTIFIER_REGEX);

    private static final ThreadLocal<Matcher> MATCHER = new ThreadLocal<Matcher>() {
        @Override
        protected Matcher initialValue() {
            return PATTERN.matcher("");
        }
    };


    private LabelParser() {}

    public static void parse(String labelString, AssemblyContext context) throws ParserException {

        Matcher matcher = MATCHER.get().reset(labelString);
        if (!matcher.matches()) {
            throw new IllegalLabelException("Label: " + labelString);
        }

        context.addLabelAtOffset(labelString);
    }
}
