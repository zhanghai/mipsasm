/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.instruction;

// NOTE: If OffsetBase is to be updated, source and immediate in ImmediateInstruction should also be updated.
public class OffsetBaseInstruction extends ImmediateInstruction {

    private static final int OPERAND_COUNT = 2;

    private OffsetBase offsetBase;

    public OffsetBaseInstruction(Operation operation, Register target, OffsetBase offsetBase) {
        super(operation, target, offsetBase.getBase(), offsetBase.getOffset());

        this.offsetBase = offsetBase;
    }

    public static OffsetBaseInstruction of(Operation operation, Register target, OffsetBase offsetBase) {
        return new OffsetBaseInstruction(operation, target, offsetBase);
    }

    public static OffsetBaseInstruction of(Operation operation, Operand... operands) {
        if (operands.length != OPERAND_COUNT) {
            throw new IllegalArgumentException("Unexpected operand count, expected: " + OPERAND_COUNT + ", found: "
                    + operands.length);
        }
        return of(operation, (Register)operands[0], (OffsetBase)operands[1]);
    }

    public OffsetBase getOffsetBase() {
        return offsetBase;
    }
}
