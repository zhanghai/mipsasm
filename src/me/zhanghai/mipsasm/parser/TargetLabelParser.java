/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.parser;

import me.zhanghai.mipsasm.assembler.TargetLabel;

public class TargetLabelParser {

    private TargetLabelParser() {}

    public static TargetLabel parse(String labelString) throws ParserException {
        try {
            return TargetLabel.of(labelString);
        } catch (IllegalArgumentException e) {
            throw new IllegalOperandException("Illegal label name: " + labelString, e);
        }
    }
}
