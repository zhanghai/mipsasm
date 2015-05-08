/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.parser;

import me.zhanghai.mipsasm.assembler.CoprocessorFunction;

import javax.script.ScriptException;

public class CoprocessorFunctionParser {

    private CoprocessorFunctionParser() {}

    public static CoprocessorFunction parse(String coprocessorFunctionString) throws ParserException {
        try {
            return CoprocessorFunction.of(JavaScriptParser.parseUnsignedInteger(coprocessorFunctionString));
        } catch (ScriptException | NumberFormatException e) {
            throw new IllegalOperandException("Unable to parse coprocessor function: " + coprocessorFunctionString, e);
        } catch (IllegalArgumentException e) {
            throw new IllegalOperandException("coprocessor function length too long: " + coprocessorFunctionString, e);
        }
    }
}
