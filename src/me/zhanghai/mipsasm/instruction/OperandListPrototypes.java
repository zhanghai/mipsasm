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

    public static final OperandPrototype[] DESTINATION_TARGET_SHIFT_AMOUNT = new OperandPrototype[] {
            OperandPrototypes.DESTINATION,
            OperandPrototypes.TARGET,
            OperandPrototypes.SHIFT_AMOUNT
    };

    public static final OperandPrototype[] DESTINATION_TARGET_SOURCE = new OperandPrototype[] {
            OperandPrototypes.DESTINATION,
            OperandPrototypes.TARGET,
            OperandPrototypes.SOURCE
    };

    public static final OperandPrototype[] LABEL = new OperandPrototype[] {
            OperandPrototypes.LABEL
    };

    public static final OperandPrototype[] DESTINATION_SOURCE = new OperandPrototype[] {
            OperandPrototypes.DESTINATION,
            OperandPrototypes.SOURCE
    };

    public static final OperandPrototype[] SOURCE = new OperandPrototype[] {
            OperandPrototypes.SOURCE
    };

    public static final OperandPrototype[] TARGET_OFFSET_BASE = new OperandPrototype[] {
            OperandPrototypes.TARGET,
            OperandPrototypes.OFFSET_BASE
    };

    public static final OperandPrototype[] TARGET_IMMEDIATE = new OperandPrototype[] {
            OperandPrototypes.TARGET,
            OperandPrototypes.IMMEDIATE
    };

    public static final OperandPrototype[] DESTINATION = new OperandPrototype[] {
            OperandPrototypes.DESTINATION
    };

    public static final OperandPrototype[] HINT_OFFSET_BASE = new OperandPrototype[] {
            OperandPrototypes.HINT,
            OperandPrototypes.OFFSET_BASE
    };
}
