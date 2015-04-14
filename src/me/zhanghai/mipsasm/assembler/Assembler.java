/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.assembler;

public class Assembler {

    private Assembler() {}

    public static void assemble(AssemblyContext context) throws AssemblerException {
        for (Assemblable assemblable : context.getAssemblableList()) {
            try {
                assemblable.write(context);
            } catch (AssemblerException e) {
                throw new AssemblerException("Assemblable: " + assemblable, e);
            }
        }
        context.packAssemblyToWords();
    }
}
