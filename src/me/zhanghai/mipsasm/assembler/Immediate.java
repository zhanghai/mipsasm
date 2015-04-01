/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.assembler;

import me.zhanghai.mipsasm.util.BitArray;

public class Immediate implements Operand, Assemblable {

    static final int LENGTH = 16;

    private BitArray value;

    private Immediate(BitArray value) {
        this.value = value;
    }

    public static Immediate of(int value) {
        int length = BitArray.lengthOf(value);
        if (length > LENGTH) {
            throw new IllegalArgumentException("Immediate length > " + LENGTH + ": " + length);
        }
        return new Immediate(BitArray.of(value, LENGTH));
    }

    public BitArray assemble(AssemblyContext context) {
        return value;
    }
}
