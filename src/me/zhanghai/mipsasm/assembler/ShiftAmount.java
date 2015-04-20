/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.assembler;

import me.zhanghai.mipsasm.util.BitArray;

public class ShiftAmount implements Operand, AssemblyProvider {

    private static final int LENGTH = 5;

    public static final ShiftAmount ZERO = ShiftAmount.of(0);

    private BitArray value;

    private ShiftAmount(BitArray value) {
        this.value = value;
    }

    public static ShiftAmount of(BitArray value) {
        if (value.length() != LENGTH) {
            throw new IllegalArgumentException("Shift amount length != " + LENGTH + ": " + value.length());
        }
        return new ShiftAmount(BitArray.copyOf(value));
    }

    public static ShiftAmount of(int value) {
        return new ShiftAmount(BitArray.of(value, LENGTH));
    }

    public BitArray assemble(AssemblyContext context) {
        return value;
    }

    @Override
    public String toString() {
        return value.toDecimalString();
    }
}
