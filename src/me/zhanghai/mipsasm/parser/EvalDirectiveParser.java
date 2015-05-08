/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.parser;

import me.zhanghai.mipsasm.assembler.AssemblyContext;

import javax.script.ScriptException;

public class EvalDirectiveParser {

    private EvalDirectiveParser() {}

    public static void parse(String[] operandStringList, AssemblyContext context) throws ParserException {

        if (operandStringList.length == 0) {
            throw new OperandCountMismatchException("Expected: >0; found: 0");
        }

        for (String operandString : operandStringList) {
            try {
                JavaScriptParser.eval(operandString);
            } catch (ScriptException e) {
                throw new ParserException("Error evaluating JavaScript", e);
            }
        }
    }
}
