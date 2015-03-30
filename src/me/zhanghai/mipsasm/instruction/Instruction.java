/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.instruction;

import java.util.Arrays;

public class Instruction {

    private Operation operation;
    private OperandInstance[] operandListInstance;
    private InstructionCompiler compiler;

    private Instruction(Operation operation, OperandInstance[] operandListInstance, InstructionCompiler compiler) {
        this.operation = operation;
        this.operandListInstance = operandListInstance;
        this.compiler = compiler;
    }

    public static Instruction of(Operation operation, OperandInstance[] operandListInstance, InstructionCompiler compiler) {
        return new Instruction(operation, operandListInstance, compiler);
    }

    public Operation getOperation() {
        return operation;
    }

    public OperandInstance[] getOperandListInstance() {
        return operandListInstance;
    }

    public Operand getOperand(String name) {
        for (OperandInstance operandInstance : operandListInstance) {
            if (operandInstance.getName().equals(name)) {
                return operandInstance.getOperand();
            }
        }
        throw new OperandNotFoundException("Operand name: " + name + ", operandListInstance: "
                + Arrays.toString(operandListInstance));
    }

    public InstructionCompiler getCompiler() {
        return compiler;
    }
}
