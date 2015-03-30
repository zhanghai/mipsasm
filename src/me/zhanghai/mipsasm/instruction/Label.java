/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.instruction;

import me.zhanghai.mipsasm.util.BitArray;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Label implements Operand, Compilable {

    private static final Pattern NAME_PATTERN = Pattern.compile("\\w+");

    private static ThreadLocal<Matcher> NAME_MATCHER = new ThreadLocal<Matcher>() {
        @Override
        protected Matcher initialValue() {
            return NAME_PATTERN.matcher("");
        }
    };

    private String name;

    private Label(String name) {
        this.name = name;
    }

    public static Label of(String name) {
        Matcher nameMatcher = NAME_MATCHER.get();
        nameMatcher.reset(name);
        if (!nameMatcher.matches()) {
            throw new IllegalArgumentException("Label name must match \\w+");
        }
        return new Label(name);
    }

    public String getName() {
        return name;
    }

    // TODO
    @Override
    public BitArray compile() {
        return null;
    }
}
