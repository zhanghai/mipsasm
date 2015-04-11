/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.parser;

import me.zhanghai.mipsasm.assembler.Operand;
import me.zhanghai.mipsasm.assembler.OperandInstance;
import me.zhanghai.mipsasm.assembler.OperandPrototype;

import java.util.Arrays;

public class OperandListParser {

    private OperandListParser() {}

    public static OperandInstance[] parse(String[] operandStringList, OperandPrototype[] operandListPrototype)
            throws ParserException {

        if (operandStringList.length != operandListPrototype.length) {
            throw new OperandCountMismatchException("Expected: " + Arrays.toString(operandListPrototype) + ", found: "
                    + Arrays.toString(operandStringList));
        }

        int operandCount = operandListPrototype.length;
        OperandInstance[] operandListInstance = new OperandInstance[operandCount];
        for (int i = 0; i < operandCount; ++i) {
            OperandPrototype operandPrototype = operandListPrototype[i];
            String operandString = operandStringList[i];
            Operand operand;
            switch (operandPrototype.getType()) {
                case REGISTER:
                    operand = RegisterParser.parse(operandString);
                    break;
                case IMMEDIATE:
                    operand = ImmediateParser.parse(operandString);
                    break;
                case OFFSET:
                    operand = OffsetParser.parse(operandString);
                    break;
                case COPROCESSOR_FUNCTION:
                    operand = CoprocessorFunctionParser.parse(operandString);
                    break;
                case SHIFT_AMOUNT:
                    operand = ShiftAmountParser.parse(operandString);
                    break;
                case TARGET:
                    operand = TargetParser.parse(operandString);
                    break;
                case OFFSET_BASE:
                    operand = OffsetBaseParser.parse(operandString);
                    break;
                case WORD_IMMEDIATE:
                    operand = WordImmediateParser.parse(operandString);
                    break;
                case LABEL:
                    operand = LabelOperandParser.parse(operandString);
                    break;
                default:
                    // Never happens.
                    throw new RuntimeException("Unknown operand type: " + operandPrototype.getType());
            }
            operandListInstance[i] = OperandInstance.fromPrototype(operandPrototype, operand);
        }

        return operandListInstance;
    }
}
