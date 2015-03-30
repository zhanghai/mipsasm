/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.instruction;

import me.zhanghai.mipsasm.util.BitArray;

public class Label implements Operand, Compilable {

    private String name;

    public Label(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // TODO
    @Override
    public BitArray compile() {
        return null;
    }
}
