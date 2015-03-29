/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.parser;

import me.zhanghai.mipsasm.instruction.Operand;

public class OperandFormat {

    public enum OperandType {
        REGISTER,
        IMMEDIATE,
        OFFSET_BASE,
        SHIFT_AMOUNT
    }

    private OperandType type;
    private Operand preset;

    public OperandFormat(OperandType type, Operand preset) {
        this.type = type;
        this.preset = preset;
    }

    public OperandFormat(OperandType type) {
        this(type, null);
    }

    public OperandType getType() {
        return type;
    }

    public boolean isPreset() {
        return preset != null;
    }

    public Operand getPreset() {
        return preset;
    }
}
