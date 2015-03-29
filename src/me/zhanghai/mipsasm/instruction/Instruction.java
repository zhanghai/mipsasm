/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.instruction;

import java.util.Arrays;

public class Instruction {

    private Operation operation;
    private OperandInstance[] operands;
    private InstructionCompiler compiler;

    public Instruction(Operation operation, OperandInstance[] operands, InstructionCompiler compiler) {
        this.operation = operation;
        this.operands = operands;
        this.compiler = compiler;
    }

    public Operation getOperation() {
        return operation;
    }

    public OperandInstance[] getOperands() {
        return operands;
    }

    public Operand getOperand(String name) {
        for (OperandInstance operandInstance : operands) {
            if (operandInstance.getName().equals(name)) {
                return operandInstance.getOperand();
            }
        }
        throw new OperandNotFoundException("Operand name: " + name + ", operands: " + Arrays.toString(operands));
    }

    public InstructionCompiler getCompiler() {
        return compiler;
    }
}
