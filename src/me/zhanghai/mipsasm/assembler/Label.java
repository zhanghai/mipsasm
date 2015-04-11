/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.assembler;

import me.zhanghai.mipsasm.parser.Tokens;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Label implements Operand {

    private static final Pattern PATTERN = Pattern.compile(Tokens.IDENTIFIER_REGEX);
    private static ThreadLocal<Matcher> MATCHER = new ThreadLocal<Matcher>() {
        @Override
        protected Matcher initialValue() {
            return PATTERN.matcher("");
        }
    };

    private String name;

    protected Label(String name) {
        this.name = name;
    }

    protected static void checkName(String name) {
        Matcher matcher = MATCHER.get().reset(name);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Label name must match " + matcher.pattern());
        }
    }

    public static Label of(String name) {
        checkName(name);
        return new Label(name);
    }

    public String getName() {
        return name;
    }
}
