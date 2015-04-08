/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.assembler;

import me.zhanghai.mipsasm.util.BitArray;

public class CoprocessorFunction implements Operand, AssemblyProvider {

    private static final int LENGTH = 26;

    private BitArray value;

    private CoprocessorFunction(BitArray value) {
        this.value = value;
    }

    public static CoprocessorFunction of(int value) {
        int length = BitArray.lengthOf(value);
        if (length > LENGTH) {
            throw new IllegalArgumentException("Coprocessor function length > " + LENGTH + ": " + length);
        }
        return new CoprocessorFunction(BitArray.of(value, LENGTH));
    }

    public BitArray getValue() {
        return value;
    }

    public BitArray assemble(AssemblyContext context) {
        return value;
    }
}
