/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.instruction;

import me.zhanghai.mipsasm.util.BitArray;

public class ImmediateInstruction extends Instruction {

    private static final int OPERAND_COUNT = 3;

    private Register target;
    private Register source;
    private Immediate immediate;

    protected ImmediateInstruction(Operation operation, Register target, Register source, Immediate immediate) {
        super(operation);

        this.target = target;
        this.source = source;
        this.immediate = immediate;
    }

    public static ImmediateInstruction of(Operation operation, Register target, Register source, Immediate immediate) {
        return new ImmediateInstruction(operation, target, source, immediate);
    }

    public static ImmediateInstruction of(Operation operation, Operand... operands) {
        if (operands.length != OPERAND_COUNT) {
            throw new IllegalArgumentException("Unexpected operand count, expected: " + OPERAND_COUNT + ", found: "
                    + operands.length);
        }
        return of(operation, (Register)operands[0], (Register)operands[1], (Immediate)operands[2]);
    }

    public Register getTarget() {
        return target;
    }

    public Register getSource() {
        return source;
    }

    public Immediate getImmediate() {
        return immediate;
    }

    @Override
    public BitArray getBits() {
        return BitArray.of(getOperation().getCode(), source.getBits(), target.getBits(), immediate.getBits());
    }
}
