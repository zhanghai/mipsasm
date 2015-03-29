/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.parser;

import me.zhanghai.mipsasm.instruction.Operand;

public class OperandsParser {

    private OperandsParser() {}

    public static Operand[] parse(String[] operandStrings, OperandFormat[] operandFormats) {

        if (operandStrings.length != operandFormats.length) {
            throw new OperandCountMismatchException("Expected: " + operandFormats.length + ", found: "
                    + operandStrings.length);
        }

        int operandCount = operandFormats.length;
        Operand[] operands = new Operand[operandCount];
        for (int i = 0; i < operandCount; ++i) {
            OperandFormat operandFormat = operandFormats[i];
            if (operandFormat.isPreset()) {
                operands[i] = operandFormat.getPreset();
            } else {
                String operandString = operandStrings[i];
                Operand operand;
                switch (operandFormat.getType()) {
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
                        throw new IllegalArgumentException("Unknown operand type: " + operandFormat.getType());
                }
                operands[i] = operand;
            }
        }

        return operands;
    }
}
