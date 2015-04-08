/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.assembler;

public class TextDirective extends Directive {

    private Address address;

    private TextDirective(Address address) {
        this.address = address;
    }

    public static TextDirective of(Address address) {
        return new TextDirective(address);
    }

    public Address getAddress() {
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
