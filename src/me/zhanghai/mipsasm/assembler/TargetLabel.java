/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.assembler;

import me.zhanghai.mipsasm.util.BitArray;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TargetLabel implements Operand, Assemblable {

    private static final Pattern NAME_PATTERN = Pattern.compile("\\w+");

    private static ThreadLocal<Matcher> NAME_MATCHER = new ThreadLocal<Matcher>() {
        @Override
        protected Matcher initialValue() {
            return NAME_PATTERN.matcher("");
        }
    };

    private static final int LENGTH = 26;

    private String name;

    private TargetLabel(String name) {
        this.name = name;
    }

    public static TargetLabel of(String name) {
        Matcher nameMatcher = NAME_MATCHER.get().reset(name);
        if (!nameMatcher.matches()) {
            throw new IllegalArgumentException("Label name must match \\w+");
        }
        return new TargetLabel(name);
    }

    public String getName() {
        return name;
    }

    @Override
    public BitArray assemble(AssemblyContext context) throws AssemblerException {
        // FIXME: offset should be direct addressed.
        int offset = context.getLabelOffset(name);
        try {
            return BitArray.of(offset, LENGTH);
        } catch (IllegalArgumentException e) {
            throw new OffsetTooLargeException("Label name: " + name + ", offset: " + offset, e);
        }
    }
}
