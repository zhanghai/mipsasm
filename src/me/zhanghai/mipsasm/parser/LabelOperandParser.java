/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.parser;

import me.zhanghai.mipsasm.assembler.Label;

public class LabelOperandParser {

    private LabelOperandParser() {}

    public static Label parse(String labelString) throws ParserException {
        try {
            return Label.of(labelString);
        } catch (IllegalArgumentException e) {
            throw new IllegalLabelException("Label name: " + labelString, e);
        }
    }
}
