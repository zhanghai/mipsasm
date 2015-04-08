/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.assembler;

import me.zhanghai.mipsasm.util.BitArray;

public class StorageDirective extends Directive {

    private BitArray value;

    private StorageDirective(BitArray value) {
        this.value = value;
    }

    public static StorageDirective of(int value, int length) {
        return new StorageDirective(BitArray.of(value, length));
    }

    public BitArray getValue() {
        return value;
    }

    @Override
    public void locate(AssemblyContext context) {
        context.advanceByBits(value.length());
    }

    @Override
    public void assemble(AssemblyContext context) throws AssemblerException {
        context.appendAssembly(value);
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
        return "Storage {" + "value=" + value + '}';
    }
}
