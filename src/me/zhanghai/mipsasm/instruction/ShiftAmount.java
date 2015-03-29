/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.instruction;

import me.zhanghai.mipsasm.util.BitArray;

public class ShiftAmount implements Operand, BitProvider {

    private static final int SHIFT_AMOUNT_LENGTH = 5;

    public static final ShiftAmount ZERO = ShiftAmount.of(0b00000);

    private BitArray value;

    private ShiftAmount(BitArray value) {
        this.value = value;
    }

    public static ShiftAmount of(int value) {
        int length = BitArray.lengthOf(value);
        if (length > SHIFT_AMOUNT_LENGTH) {
            throw new IllegalArgumentException("Shift amount length > " + SHIFT_AMOUNT_LENGTH + ": " + length);
        }
        return new ShiftAmount(BitArray.of(value, SHIFT_AMOUNT_LENGTH));
    }

    public BitArray getBits() {
        return value;
    }
}
