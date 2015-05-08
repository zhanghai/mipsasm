/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.parser;

import me.zhanghai.mipsasm.assembler.AssemblyContext;

import javax.script.ScriptException;

public class EchoDirectiveParser {

    private EchoDirectiveParser() {}

    public static void parse(String[] operandStringList, AssemblyContext context) throws ParserException {

        if (operandStringList.length == 0) {
            throw new OperandCountMismatchException("Expected: >0; found: 0");
        }

        for (String operandString : operandStringList) {
            String line;
            try {
                line = JavaScriptParser.eval(operandString).toString();
            } catch (ScriptException e) {
                throw new ParserException("Error evaluating JavaScript", e);
            }
            try {
                LineParser.parse(line, context);
            } catch (ParserException e) {
                throw new ParserException("Error assembling echoed line: " + line, e);
            }
        }
    }
}
