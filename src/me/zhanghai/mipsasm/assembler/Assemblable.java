/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.assembler;

public interface Assemblable {

    void allocate(AssemblyContext context);

    void write(AssemblyContext context) throws AssemblerException;
}
