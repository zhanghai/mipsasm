/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.parser;

import me.zhanghai.mipsasm.assembler.OffsetLabel;

public class OffsetLabelParser {

    private OffsetLabelParser() {}

    public static OffsetLabel parse(String labelString) {
        try {
            return OffsetLabel.of(labelString);
        } catch (IllegalArgumentException e) {
            throw new InvalidOperandException("Illegal label name: " + labelString, e);
        }
    }
}
