/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.instruction;

import me.zhanghai.mipsasm.util.BitArray;

public class RegisterInstruction extends Instruction {

    private static final int OPERAND_COUNT = 4;

    private Register destination;
    private Register source;
    private Register target;
    private ShiftAmount shiftAmount;

    public RegisterInstruction(Operation operation, Register destination, Register source, Register target,
                               ShiftAmount shiftAmount) {
        super(operation);

        this.destination = destination;
        this.source = source;
        this.target = target;
        this.shiftAmount = shiftAmount;
    }

    public static RegisterInstruction of(Operation operation, Register destination, Register source, Register target,
                                         ShiftAmount shiftAmount) {
        return new RegisterInstruction(operation, destination, source, target, shiftAmount);
    }

    public static RegisterInstruction of(Operation operation, Operand... operands) {
        if (operands.length != OPERAND_COUNT) {
            throw new IllegalArgumentException("Unexpected operand count, expected: " + OPERAND_COUNT + ", found: "
                    + operands.length);
        }
        return of(operation, (Register)operands[0], (Register)operands[1], (Register)operands[2],
                (ShiftAmount)operands[3]);
    }

    public Register getDestination() {
        return destination;
    }

    public Register getSource() {
        return source;
    }

    public Register getTarget() {
        return target;
    }

    public ShiftAmount getShiftAmount() {
        return shiftAmount;
    }

    @Override
    public BitArray getBits() {
        Operation operation = getOperation();
        return BitArray.of(operation.getCode(), source.getBits(), target.getBits(), destination.getBits(),
                shiftAmount.getBits(), operation.getFunction());
    }
}
