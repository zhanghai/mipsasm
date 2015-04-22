/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.disassembler;

import me.zhanghai.mipsasm.Constants;
import me.zhanghai.mipsasm.assembler.StorageDirective;
import me.zhanghai.mipsasm.util.BitArray;

public class StorageDirectiveDisassembler {

    // Storage directive must be disassemble to .byte s first so that labels can be at any byte address.
    public static void disassemble(BitArray bitArray, DisassemblyContext context) throws DisassemblerException {
        switch (bitArray.length()) {
            case 4 * Constants.BYTE_LENGTH:
                context.appendAssemblable(StorageDirective.of(bitArray.subArray(3 * Constants.BYTE_LENGTH,
                        4 * Constants.BYTE_LENGTH)), 1);
                // Fall through!
            case 3 * Constants.BYTE_LENGTH:
                context.appendAssemblable(StorageDirective.of(bitArray.subArray(2 * Constants.BYTE_LENGTH,
                        3 * Constants.BYTE_LENGTH)), 1);
                // Fall through!
            case 2 * Constants.BYTE_LENGTH:
                context.appendAssemblable(StorageDirective.of(bitArray.subArray(Constants.BYTE_LENGTH,
                        2 * Constants.BYTE_LENGTH)), 1);
                // Fall through
            case Constants.BYTE_LENGTH:
                context.appendAssemblable(StorageDirective.of(bitArray.subArray(0, Constants.BYTE_LENGTH)), 1);
                break;
            default:
                throw new DisassemblerException("Storage directive length is not 1 to 4 bytes: "
                        + bitArray.length());
        }
    }
}
