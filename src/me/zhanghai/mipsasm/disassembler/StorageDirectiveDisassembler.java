/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.disassembler;

import me.zhanghai.mipsasm.assembler.StorageDirective;
import me.zhanghai.mipsasm.util.BitArray;

public class StorageDirectiveDisassembler {

    public static void disassemble(BitArray bitArray, DisassemblyContext context) throws DisassemblerException {
        try {
            context.appendAssemblable(StorageDirective.of(bitArray));
        } catch (IllegalArgumentException e) {
            throw new DisassemblerException("Unable to parse binary as a storage directive: " + bitArray, e);
        }
    }
}
