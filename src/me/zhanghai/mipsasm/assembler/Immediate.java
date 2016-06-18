/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.assembler;

import me.zhanghai.mipsasm.Constants;
import me.zhanghai.mipsasm.util.BitArray;

public class Immediate implements Operand, AssemblyProvider {

    static final int LENGTH = Constants.HALF_WORD_LENGTH;

    private BitArray value;

    private Immediate(BitArray value) {
        this.value = value;
    }

    public static Immediate of(int value) {
        return new Immediate(BitArray.ofInteger(value, LENGTH));
    }

    public BitArray getValue() {
        return value;
    }

    @Override
    public BitArray assemble(AssemblyContext context) {
        return value;
    }

    @Override
    public String toString() {
        return value.toDecimalString();
    }
}
