/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.assembler;

import me.zhanghai.mipsasm.Constants;
import me.zhanghai.mipsasm.InternalException;
import me.zhanghai.mipsasm.util.BitArray;

public class StorageDirective extends Directive {

    private BitArray value;

    private StorageDirective(BitArray value) {
        this.value = value;
    }

    public static StorageDirective of(BitArray value) {
        if (value.length() % Constants.BYTE_LENGTH != 0) {
            throw new IllegalArgumentException("Storage directive length not in bytes: " + value.length());
        }
        return new StorageDirective(BitArray.copyOf(value));
    }

    public static StorageDirective of(int value, int length) {
        return of(BitArray.of(value, length));
    }

    public BitArray getValue() {
        return value;
    }

    @Override
    public void allocate(AssemblyContext context) {
        context.allocateBits(value.length());
    }

    @Override
    public void write(AssemblyContext context) throws AssemblerException {
        context.writeBytes(value);
    }

    @Override
    public boolean equals(Object object) {

        if (this == object) {
            return true;
        } if (object == null || getClass() != object.getClass()) {
            return false;
        }

        StorageDirective storage = (StorageDirective) object;
        return value.equals(storage.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(".");
        switch (value.length()) {
            case Constants.BYTE_LENGTH:
                builder.append(DirectiveInformation.BYTE.name().toLowerCase());
                break;
            case Constants.HALF_WORD_LENGTH:
                builder.append(DirectiveInformation.HALF.name().toLowerCase());
                break;
            case Constants.WORD_LENGTH:
                builder.append(DirectiveInformation.WORD.name().toLowerCase());
                break;
            default:
                throw new InternalException("Illegal storage directive length: " + value.length());
        }
        builder.append(" ")
                .append(value.toHexString());
        return builder.toString();
    }
}
