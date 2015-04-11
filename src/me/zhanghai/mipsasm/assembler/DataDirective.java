/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.assembler;

public class DataDirective extends Directive {

    private WordImmediate address;

    private DataDirective(WordImmediate address) {
        this.address = address;
    }

    public static DataDirective of(WordImmediate address) {
        return new DataDirective(address);
    }

    public WordImmediate getAddress() {
        return address;
    }

    @Override
    public void locate(AssemblyContext context) {
        context.advanceToAddress(address);
    }

    @Override
    public void assemble(AssemblyContext context) throws AssemblerException {
        context.appendAssemblyByZeroToAddress(address);
    }
}
