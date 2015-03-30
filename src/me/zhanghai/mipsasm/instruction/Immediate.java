/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.instruction;

import me.zhanghai.mipsasm.util.BitArray;

public class Immediate implements Operand, Compilable {

    private static final int IMMEDIATE_LENGTH = 16;

    private BitArray value;

    private Immediate(BitArray value) {
        this.value = value;
    }

    public static Immediate of(int value) {
        int length = BitArray.lengthOf(value);
        if (length > IMMEDIATE_LENGTH) {
            throw new IllegalArgumentException("Immediate length > " + IMMEDIATE_LENGTH + ": " + length);
        }
        return new Immediate(BitArray.of(value, IMMEDIATE_LENGTH));
    }

    public BitArray compile() {
        return value;
    }
}
