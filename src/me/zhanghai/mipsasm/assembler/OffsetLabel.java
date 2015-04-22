/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.assembler;

import me.zhanghai.mipsasm.Constants;
import me.zhanghai.mipsasm.InternalException;
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

    // From MIPS 32 manual:
    // An 18-bit signed offset (the 16-bit offset field shifted left 2 bits) is added to the address of the instruction
    // following the branch (not the branch itself), in the branch delay slot, to form a PC-relative effective target
    // address.
    @Override
    public BitArray assemble(AssemblyContext context) throws AssemblerException {
        int offset = context.getLabelAddress(this) - (context.getAddress() + Constants.BYTES_PER_INSTRUCTION);
        if (offset % Constants.BYTES_PER_INSTRUCTION != 0) {
            throw new InternalException("Instruction alignment failed, label: " + getName() + ", offset: " + offset);
        }
        offset /= Constants.BYTES_PER_INSTRUCTION;
        try {
            return BitArray.ofInteger(offset, LENGTH);
        } catch (IllegalArgumentException e) {
            throw new OffsetTooLargeException("Label name: " + getName() + ", offset: " + offset, e);
        }
    }
}
