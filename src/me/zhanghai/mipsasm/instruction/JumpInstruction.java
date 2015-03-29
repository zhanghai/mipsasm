/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.instruction;

import me.zhanghai.mipsasm.util.BitArray;

public class JumpInstruction extends Instruction {

    private static final int OPERAND_COUNT = 1;

    private Address address;

    private JumpInstruction(Operation operation, Address address) {
        super(operation);

        this.address = address;
    }

    public static JumpInstruction of(Operation operation, Address address) {
        return new JumpInstruction(operation, address);
    }

    public static JumpInstruction of(Operation operation, Operand... operands) {
        if (operands.length != OPERAND_COUNT) {
            throw new IllegalArgumentException("Unexpected operand count, expected: " + OPERAND_COUNT + ", found: "
                    + operands.length);
        }
        return of(operation, (Address)operands[0]);
    }

    public Address getAddress() {
        return address;
    }

    @Override
    public BitArray getBits() {
        return BitArray.of(getOperation().getCode(), address.getBits());
    }
}
