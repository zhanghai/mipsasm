/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.assembler;

import me.zhanghai.mipsasm.util.BitArray;

public class ShiftAmount implements Operand, Assemblable {

    private static final int LENGTH = 5;

    public static final ShiftAmount ZERO = ShiftAmount.of(0b00000);

    private BitArray value;

    private ShiftAmount(BitArray value) {
        this.value = value;
    }

    public static ShiftAmount of(int value) {
        int length = BitArray.lengthOf(value);
        if (length > LENGTH) {
            throw new IllegalArgumentException("Shift amount length > " + LENGTH + ": " + length);
        }
        return new ShiftAmount(BitArray.of(value, LENGTH));
    }

    public BitArray assemble(AssemblyContext context) {
        return value;
    }
}
