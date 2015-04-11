/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.assembler;

import me.zhanghai.mipsasm.util.BitArray;

public class DwordImmediate implements Operand {

    private BitArray lower, upper;

    private DwordImmediate(BitArray lower, BitArray upper) {
        this.lower = lower;
        this.upper = upper;
    }

    public static DwordImmediate of(long value) {
        return new DwordImmediate(BitArray.ofValue((int) (value >>> 32)), BitArray.ofValue((int) value));
    }

    public BitArray getLower() {
        return lower;
    }

    public BitArray getUpper() {
        return upper;
    }

    @Override
    public boolean equals(Object object) {

        if (this == object) {
            return true;
        } if (object == null || getClass() != object.getClass()) {
            return false;
        }

        DwordImmediate that = (DwordImmediate) object;

        if (!lower.equals(that.lower)) {
            return false;
        } else {
            return upper.equals(that.upper);
        }
    }

    @Override
    public int hashCode() {
        int result = lower.hashCode();
        result = 31 * result + upper.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "DwordImmediate {" +
                "lower=" + lower +
                ", upper=" + upper +
                '}';
    }
}
