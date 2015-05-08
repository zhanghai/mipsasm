/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.parser;

import me.zhanghai.mipsasm.assembler.AssemblyContext;
import me.zhanghai.mipsasm.assembler.BackwardAddressException;
import me.zhanghai.mipsasm.assembler.DataDirective;
import me.zhanghai.mipsasm.assembler.WordImmediate;

import javax.script.ScriptException;
import java.util.Arrays;

public class DataDirectiveParser {

    private DataDirectiveParser() {}

    public static void parse(String[] operandStringList, AssemblyContext context) throws ParserException {

        if (operandStringList.length != 1) {
            throw new OperandCountMismatchException("Expected: [Address], found: "
                    + Arrays.toString(operandStringList));
        }

        String addressString = operandStringList[0];
        WordImmediate address;
        try {
            address = WordImmediate.of(JavaScriptParser.parseUnsignedInteger(addressString));
        } catch (ScriptException | IllegalArgumentException e) {
            throw new IllegalOperandException("Address: " + addressString, e);
        }
        if (address.getValue().get(0) || address.getValue().get(1)) {
            throw new IllegalOperandException("Address is not word aligned: " + addressString);
        }
        try {
            context.appendAssemblable(DataDirective.of(address));
        } catch (BackwardAddressException e) {
            throw new IllegalOperandException("Address moves backward", e);
        }
    }
}
