/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.parser;

import me.zhanghai.mipsasm.instruction.Label;

public class LabelParser {

    private LabelParser() {}

    public static Label parse(String labelString) {
        try {
            return Label.of(labelString);
        } catch (IllegalArgumentException e) {
            throw new InvalidOperandException("Illegal label name: " + labelString, e);
        }
    }
}
