/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.parser;

import me.zhanghai.mipsasm.instruction.Operand;
import me.zhanghai.mipsasm.instruction.OperandPrototype;

public class OperandsParser {

    private OperandsParser() {}

    public static Operand[] parse(String[] operandStrings, OperandPrototype[] operandPrototypes) {

        if (operandStrings.length != operandPrototypes.length) {
            throw new OperandCountMismatchException("Expected: " + operandPrototypes.length + ", found: "
                    + operandStrings.length);
        }

        int operandCount = operandPrototypes.length;
        Operand[] operands = new Operand[operandCount];
        for (int i = 0; i < operandCount; ++i) {
            OperandPrototype operandPrototype = operandPrototypes[i];
            if (operandPrototype.isPreset()) {
                operands[i] = operandPrototype.getPreset();
            } else {
                String operandString = operandStrings[i];
                Operand operand;
                switch (operandPrototype.getType()) {
                    case REGISTER:
                        operand = RegisterParser.parse(operandString);
                        break;
                    case IMMEDIATE:
                        operand = ImmediateParser.parse(operandString);
                        break;
                    case OFFSET_BASE:
                        operand = OffsetBaseParser.parse(operandString);
                        break;
                    case SHIFT_AMOUNT:
                        operand = ShiftAmountParser.parse(operandString);
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown operand type: " + operandPrototype.getType());
                }
                operands[i] = operand;
            }
        }

        return operands;
    }
}
