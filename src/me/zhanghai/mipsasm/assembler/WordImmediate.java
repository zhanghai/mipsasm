/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.assembler;

import me.zhanghai.mipsasm.util.BitArray;

public class WordImmediate implements Operand {

    private BitArray value;

    private WordImmediate(BitArray value) {
        this.value = value;
    }

    public static WordImmediate of(int value) {
        return new WordImmediate(BitArray.ofValue(value));
    }

    public BitArray getValue() {
        return value;
    }

    public BitArray getUpper() {
        return value.subArray(0, AssemblyContext.HALF_WORD_LENGTH);
    }

    public BitArray getLower() {
        return value.subArray(AssemblyContext.HALF_WORD_LENGTH);
    }

    @Override
    public boolean equals(Object object) {

        if (this == object) {
            return true;
        } if (object == null || getClass() != object.getClass()) {
            return false;
        }

        WordImmediate that = (WordImmediate) object;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return "WordImmediate {" + "value=" + value + '}';
    }
}
