/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.assembler;

public class TextDirective extends Directive {

    private WordImmediate address;

    private TextDirective(WordImmediate address) {
        this.address = address;
    }

    public static TextDirective of(WordImmediate address) {
        return new TextDirective(address);
    }

    public WordImmediate getAddress() {
        return address;
    }

    @Override
    public void allocate(AssemblyContext context) {
        context.allocateToAddress(address);
    }

    @Override
    public void write(AssemblyContext context) throws AssemblerException {
        context.writeToAddress(address);
    }

    @Override
    public String toString() {
        return "." + DirectiveInformation.TEXT.name().toLowerCase() + " " + address;
    }
}
