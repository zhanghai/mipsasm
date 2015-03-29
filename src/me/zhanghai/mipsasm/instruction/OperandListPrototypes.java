/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.instruction;

public class OperandListPrototypes {

    public static final OperandPrototype[] DESTINATION_SOURCE_TARGET = new OperandPrototype[] {
            OperandPrototypes.DESTINATION,
            OperandPrototypes.SOURCE,
            OperandPrototypes.TARGET
    };

    public static final OperandPrototype[] TARGET_SOURCE_IMMEDIATE = new OperandPrototype[] {
            OperandPrototypes.TARGET,
            OperandPrototypes.SOURCE,
            OperandPrototypes.IMMEDIATE
    };

    public static final OperandPrototype[] SOURCE_TARGET_OFFSET = new OperandPrototype[] {
            OperandPrototypes.SOURCE,
            OperandPrototypes.TARGET,
            OperandPrototypes.OFFSET
    };

    public static final OperandPrototype[] SOURCE_OFFSET = new OperandPrototype[] {
            OperandPrototypes.SOURCE,
            OperandPrototypes.OFFSET
    };

    public static final OperandPrototype[] COPROCESSOR_FUNCTION = new OperandPrototype[] {
            OperandPrototypes.COPROCESSOR_FUNCTION
    };

    public static final OperandPrototype[] SOURCE_TARGET = new OperandPrototype[] {
            OperandPrototypes.SOURCE,
            OperandPrototypes.TARGET
    };
}
