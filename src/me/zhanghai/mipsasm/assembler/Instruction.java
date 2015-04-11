/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.assembler;

import java.util.Arrays;

public class Instruction implements Assemblable {

    private Operation operation;
    private OperandInstance[] operandListInstance;
    private InstructionAssembler assembler;

    private Instruction(Operation operation, OperandInstance[] operandListInstance, InstructionAssembler assembler) {
        this.operation = operation;
        this.operandListInstance = operandListInstance;
        this.assembler = assembler;
    }

    public static Instruction of(Operation operation, OperandInstance[] operandListInstance, InstructionAssembler compiler) {
        return new Instruction(operation, operandListInstance, compiler);
    }

    public Operation getOperation() {
        return operation;
    }

    public OperandInstance[] getOperandListInstance() {
        return operandListInstance;
    }

    public OperandInstance getOperandInstance(String operandName) {
        for (OperandInstance operandInstance : operandListInstance) {
            if (operandInstance.getName().equals(operandName)) {
                return operandInstance;
            }
        }
        throw new OperandNotFoundException("Operand name: " + operandName + ", operandListInstance: "
                + Arrays.toString(operandListInstance));
    }

    public Operand getOperand(String operandName) {
        return getOperandInstance(operandName).getOperand();
    }

    public Operand getOperand(OperandPrototype operandPrototype) {
        return getOperand(operandPrototype.getName());
    }

    public InstructionAssembler getAssembler() {
        return assembler;
    }

    public void locate(AssemblyContext context) {
        assembler.locate(this, context);
    }

    public void assemble(AssemblyContext context) throws AssemblerException {
        assembler.assemble(this, context);
    }
}
