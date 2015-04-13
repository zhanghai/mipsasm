/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.assembler;

public interface InstructionAssembler {

    void allocate(Instruction instruction, AssemblyContext context);

    void write(Instruction instruction, AssemblyContext context) throws AssemblerException;
}
