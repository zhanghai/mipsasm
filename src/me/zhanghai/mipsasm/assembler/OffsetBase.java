/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.assembler;

public class OffsetBase implements Operand {

    private Register base;
    private Immediate offset;

    private OffsetBase(Register base, Immediate offset) {
        this.base = base;
        this.offset = offset;
    }

    public static OffsetBase of(Register base, Immediate offset) {
        return new OffsetBase(base, offset);
    }

    public Register getBase() {
        return base;
    }

    public Immediate getOffset() {
        return offset;
    }

    @Override
    public String toString() {
        return (offset.getValue().isZero() ? "" : offset.toString()) + "(" + base.toString() + ")";
    }
}
