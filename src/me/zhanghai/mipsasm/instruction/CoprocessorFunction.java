/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.instruction;

import me.zhanghai.mipsasm.util.BitArray;

public class CoprocessorFunction implements BitProvider {

    private static final int COPROCESSOR_PROCESSOR_LENGTH = 26;

    private BitArray value;

    private CoprocessorFunction(BitArray value) {
        this.value = value;
    }

    public static CoprocessorFunction of(int value) {
        int length = BitArray.lengthOf(value);
        if (length > COPROCESSOR_PROCESSOR_LENGTH) {
            throw new IllegalArgumentException("Coprocessor function length > " + COPROCESSOR_PROCESSOR_LENGTH + ": " + length);
        }
        return new CoprocessorFunction(BitArray.of(value, COPROCESSOR_PROCESSOR_LENGTH));
    }

    public BitArray getBits() {
        return value;
    }
}
