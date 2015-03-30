/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.instruction;

import me.zhanghai.mipsasm.util.BitArray;

public class Address implements Compilable {

    private static final int ADDRESS_LENGTH = 26;

    private BitArray value;

    private Address(BitArray value) {
        this.value = value;
    }

    public static Address of(int value) {
        int length = BitArray.lengthOf(value);
        if (length > ADDRESS_LENGTH) {
            throw new IllegalArgumentException("Address length > " + ADDRESS_LENGTH + ": " + length);
        }
        return new Address(BitArray.of(value, ADDRESS_LENGTH));
    }

    public BitArray compile() {
        return value;
    }
}
