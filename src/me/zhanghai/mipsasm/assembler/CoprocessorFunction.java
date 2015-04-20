/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.assembler;

import me.zhanghai.mipsasm.util.BitArray;

public class CoprocessorFunction implements Operand, AssemblyProvider {

    private static final int LENGTH = 25;

    private BitArray value;

    private CoprocessorFunction(BitArray value) {
        this.value = value;
    }

    public static CoprocessorFunction of(BitArray value) {
        if (value.length() != LENGTH) {
            throw new IllegalArgumentException("Coprocessor function length != " + LENGTH + ": " + value.length());
        }
        return new CoprocessorFunction(BitArray.copyOf(value));
    }

    public static CoprocessorFunction of(int value) {
        return new CoprocessorFunction(BitArray.of(value, LENGTH));
    }

    public BitArray getValue() {
        return value;
    }

    public BitArray assemble(AssemblyContext context) {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
