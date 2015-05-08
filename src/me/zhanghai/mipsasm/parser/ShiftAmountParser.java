/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.parser;

import me.zhanghai.mipsasm.assembler.ShiftAmount;

import javax.script.ScriptException;

public class ShiftAmountParser {

    private ShiftAmountParser() {}

    public static ShiftAmount parse(String shiftAmountString) throws ParserException {
        try {
            return ShiftAmount.of(JavaScriptParser.parseUnsignedInteger(shiftAmountString));
        } catch (ScriptException | NumberFormatException e) {
            throw new IllegalOperandException("Unable to parse shift amount: " + shiftAmountString, e);
        } catch (IllegalArgumentException e) {
            throw new IllegalOperandException("Shift amount length too long: " + shiftAmountString, e);
        }
    }
}
