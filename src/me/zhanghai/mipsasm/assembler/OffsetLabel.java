/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.assembler;

import me.zhanghai.mipsasm.util.BitArray;

public class OffsetLabel extends Label implements AssemblyProvider {

    private static final int LENGTH = Immediate.LENGTH;

    private OffsetLabel(String name) {
        super(name);
    }

    public static OffsetLabel of(String name) {
        checkName(name);
        return new OffsetLabel(name);
    }

    @Override
    public BitArray assemble(AssemblyContext context) throws AssemblerException {
        int offset = context.getLabelAddress(this) - (context.getAddress() + AssemblyContext.BYTES_IN_WORD);
        try {
            return BitArray.ofInteger(offset, LENGTH);
        } catch (IllegalArgumentException e) {
            throw new OffsetTooLargeException("Label name: " + getName() + ", offset: " + offset, e);
        }
    }
}
