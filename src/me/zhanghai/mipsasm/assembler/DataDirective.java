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
    public void allocate(AssemblyContext context) {
        context.allocateToAddress(address);
    }

    @Override
    public void write(AssemblyContext context) throws AssemblerException {
        context.writeToAddress(address);
    }

    @Override
    public String toString() {
        return "." + DirectiveInformation.DATA.name().toLowerCase() + " " + address;
    }
}
