/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.instruction;

public enum OperandType {
    REGISTER,
    IMMEDIATE,
    COPROCESSOR_FUNCTION,
    SHIFT_AMOUNT,
    LABEL,
    OFFSET_BASE
}
