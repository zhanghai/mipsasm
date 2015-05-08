/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.parser;

import me.zhanghai.mipsasm.assembler.Immediate;
import me.zhanghai.mipsasm.assembler.Offset;
import me.zhanghai.mipsasm.assembler.OffsetLabel;

import javax.script.ScriptException;

public class OffsetParser {

    private OffsetParser() {}

    public static Offset parse(String offsetString) throws ParserException {
        try {
            return Offset.of(Immediate.of(JavaScriptParser.parseSignedInteger(offsetString)));
        } catch (ScriptException | NumberFormatException e) {
            try {
                return Offset.of(OffsetLabel.of(offsetString));
            } catch (IllegalArgumentException ex) {
                throw new IllegalLabelException("Offset label name: " + offsetString, ex);
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalOperandException("Offset value length too long", e);
        }
    }
}
