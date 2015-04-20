/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.assembler;

public class SpaceDirective extends Directive {

    private int length;

    private SpaceDirective(int length) {
        this.length = length;
    }

    public static SpaceDirective of(int length) {
        return new SpaceDirective(length);
    }

    public int getLength() {
        return length;
    }

    @Override
    public void allocate(AssemblyContext context) {
        context.allocateSpace(length);
    }

    @Override
    public void write(AssemblyContext context) throws AssemblerException {
        context.writeSpace(length);
    }

    @Override
    public boolean equals(Object object) {

        if (this == object) {
            return true;
        } else if (object == null || getClass() != object.getClass()) {
            return false;
        }

        SpaceDirective that = (SpaceDirective) object;
        return length == that.length;
    }

    @Override
    public int hashCode() {
        return length;
    }

    @Override
    public String toString() {
        return "." + DirectiveInformation.SPACE.name().toLowerCase() + " " + length;
    }
}
