/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.disassembler;

import me.zhanghai.mipsasm.assembler.InstructionInformation;
import me.zhanghai.mipsasm.util.BitArray;

public interface InstructionDisassembler {
    void disassemble(InstructionInformation information, BitArray bitArray, DisassemblyContext context);
}
