/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.assembler;

import me.zhanghai.mipsasm.util.BitArray;

public class Address {

    private static final int LENGTH = 32;

    private BitArray value;

    private Address(BitArray value) {
        this.value = value;
    }

    public static Address of(int value) {
        return new Address(BitArray.of(value, LENGTH));
    }

    public BitArray getValue() {
        return value;
    }

    @Override
    public boolean equals(Object object) {

        if (this == object) {
            return true;
        } if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Address address = (Address) object;
        return value.equals(address.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return "Address {" + "value=" + value + '}';
    }
}
