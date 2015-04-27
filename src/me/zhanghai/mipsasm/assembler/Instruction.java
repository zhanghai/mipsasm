/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.assembler;

import me.zhanghai.mipsasm.util.IoUtils;

import java.util.Arrays;

public class Instruction implements Assemblable {

    private Operation operation;
    private OperandInstance[] operandListInstance;

    private Instruction(Operation operation, OperandInstance[] operandListInstance) {
        this.operation = operation;
        this.operandListInstance = operandListInstance;
    }

    public static Instruction of(Operation operation, OperandInstance[] operandListInstance) {
        return new Instruction(operation, operandListInstance);
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
                + Arrays.toString(operandListInstance) + ", instruction: "+ this);
    }

    public Operand getOperand(String operandName) {
        return getOperandInstance(operandName).getOperand();
    }

    public Operand getOperand(OperandPrototype operandPrototype) {
        return getOperand(operandPrototype.getName());
    }

    private InstructionAssembler getAssembler() {
        return InstructionInformation.ofOperation(operation).getAssembler();
    }

    public void allocate(AssemblyContext context) {
        getAssembler().allocate(this, context);
    }

    public void write(AssemblyContext context) throws AssemblerException {
        getAssembler().write(this, context);
    }

    @Override
    public String toString() {
        if (operandListInstance.length == 0) {
            return operation.name().toLowerCase();
        } else {
            return operation.name().toLowerCase() + " " + IoUtils.arrayToString(operandListInstance,
                    new IoUtils.Stringifier<OperandInstance>() {
                        @Override
                        public String stringify(OperandInstance operandInstance) {
                            return operandInstance.getOperand().toString();
                        }
                    }, ", ");
        }
    }
}
