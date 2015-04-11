/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.assembler;

public enum OperandType {
    REGISTER,
    IMMEDIATE,
    OFFSET,
    COPROCESSOR_FUNCTION,
    SHIFT_AMOUNT,
    TARGET,
    OFFSET_BASE,
    WORD_IMMEDIATE,
    LABEL
}
