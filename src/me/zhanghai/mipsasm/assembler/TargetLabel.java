/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.assembler;

import me.zhanghai.mipsasm.Constants;
import me.zhanghai.mipsasm.util.BitArray;

public class TargetLabel extends Label implements AssemblyProvider {

    private static final int LENGTH = 26;

    private TargetLabel(String name) {
        super(name);
    }

    public static TargetLabel of(String name) {
        checkName(name);
        return new TargetLabel(name);
    }

    @Override
    public BitArray assemble(AssemblyContext context) throws AssemblerException {
        BitArray address = BitArray.of(context.getLabelAddress(this), Constants.ADDRESS_LENGTH);
        if (address.get(0) || address.get(1)) {
            throw new AssemblerException("Instruction alignment failed internally, label: " + getName() + ", address: "
                    + address);
        }
        // TODO: Delay slot is not taken into consideration here.
        if (address.subArray(2, Constants.ADDRESS_LENGTH).equals(
                BitArray.of(context.getAddress(), 2, Constants.ADDRESS_LENGTH))) {
            throw new OffsetTooLargeException("Label name: " + getName() + ", address: " + address
                    + ", current address: " + BitArray.of(context.getAddress(), Constants.ADDRESS_LENGTH));
        }
        return address
                .rightShift(2)
                .setLength(LENGTH);
    }
}
