/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.assembler;

import me.zhanghai.mipsasm.util.BitArray;

public enum Register implements Operand, AssemblyProvider {

    ZERO,
    AT,
    V0,
    V1,
    A0,
    A1,
    A2,
    A3,
    T0,
    T1,
    T2,
    T3,
    T4,
    T5,
    T6,
    T7,
    S0,
    S1,
    S2,
    S3,
    S4,
    S5,
    S6,
    S7,
    T8,
    T9,
    K0,
    K1,
    GP,
    SP,
    FP,
    RA;

    private static final int LENGTH = 5;

    public static Register of(int index) {
        return values()[index];
    }

    public BitArray assemble(AssemblyContext context) {
        return BitArray.of(ordinal(), LENGTH);
    }

    @Override
    public String toString() {
        return "$" + name().toLowerCase();
    }
}
