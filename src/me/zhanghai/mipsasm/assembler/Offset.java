/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.assembler;

import me.zhanghai.mipsasm.util.BitArray;

public class Offset implements Operand, AssemblyProvider {

    private boolean isLabel;

    private OffsetLabel label;

    private Immediate immediate;

    public Offset(boolean isLabel, OffsetLabel label, Immediate immediate) {
        this.isLabel = isLabel;
        this.label = label;
        this.immediate = immediate;
    }

    public static Offset of(OffsetLabel label) {
        return new Offset(true, label, null);
    }

    public static Offset of(Immediate immediate) {
        return new Offset(false, null, immediate);
    }

    public boolean isLabel() {
        return isLabel;
    }

    public OffsetLabel getLabel() {
        if (!isLabel) {
            throw new IllegalStateException("Not a label: " + immediate);
        }
        return label;
    }

    public Immediate getImmediate() {
        if (isLabel) {
            throw new IllegalStateException("Not an immediate: " + label);
        }
        return immediate;
    }

    @Override
    public BitArray assemble(AssemblyContext context) throws AssemblerException {
        if (isLabel) {
            return label.assemble(context);
        } else {
            return immediate.assemble(context);
        }
    }

    @Override
    public String toString() {
        return isLabel ? label.toString() : immediate.toString();
    }
}
