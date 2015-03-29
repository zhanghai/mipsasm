/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.instruction;

import me.zhanghai.mipsasm.util.BitArray;

public abstract class Instruction {

    public enum Type {
        Register,
        Immediate,
        OffsetBase,
        Jump,
    }

    private Operation operation;

    public Instruction(Operation operation) {
        this.operation = operation;
    }

    public Instruction of(Type type, Operation operation, Operand[] operands) {
        switch (type) {
            case Register:
                return RegisterInstruction.of(operation, operands);
            case Immediate:
                return ImmediateInstruction.of(operation, operands);
            case OffsetBase:
                return OffsetBaseInstruction.of(operation, operands);
            case Jump:
                return JumpInstruction.of(operation, operands);
            default:
                throw new IllegalArgumentException("Unknown type: " + type);
        }
    }

    public Operation getOperation() {
        return operation;
    }

    public abstract BitArray getBits();
}
