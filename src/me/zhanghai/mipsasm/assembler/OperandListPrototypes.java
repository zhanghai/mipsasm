/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.assembler;

public class OperandListPrototypes {

    public static final OperandPrototype[] EMPTY = new OperandPrototype[] {};

    public static final OperandPrototype[] DESTINATION_SOURCE_SOURCE2 = new OperandPrototype[] {
            OperandPrototypes.DESTINATION,
            OperandPrototypes.SOURCE,
            OperandPrototypes.SOURCE2
    };

    public static final OperandPrototype[] SOURCE2_SOURCE_IMMEDIATE = new OperandPrototype[] {
            OperandPrototypes.SOURCE2,
            OperandPrototypes.SOURCE,
            OperandPrototypes.IMMEDIATE
    };

    public static final OperandPrototype[] OFFSET = new OperandPrototype[] {
            OperandPrototypes.OFFSET
    };

    public static final OperandPrototype[] SOURCE_SOURCE2_OFFSET = new OperandPrototype[] {
            OperandPrototypes.SOURCE,
            OperandPrototypes.SOURCE2,
            OperandPrototypes.OFFSET
    };

    public static final OperandPrototype[] SOURCE_OFFSET = new OperandPrototype[] {
            OperandPrototypes.SOURCE,
            OperandPrototypes.OFFSET
    };

    public static final OperandPrototype[] COPROCESSOR_FUNCTION = new OperandPrototype[] {
            OperandPrototypes.COPROCESSOR_FUNCTION
    };

    public static final OperandPrototype[] SOURCE_SOURCE2 = new OperandPrototype[] {
            OperandPrototypes.SOURCE,
            OperandPrototypes.SOURCE2
    };

    public static final OperandPrototype[] DESTINATION_SOURCE2_SHIFT_AMOUNT = new OperandPrototype[] {
            OperandPrototypes.DESTINATION,
            OperandPrototypes.SOURCE2,
            OperandPrototypes.SHIFT_AMOUNT
    };

    public static final OperandPrototype[] DESTINATION_SOURCE2_SOURCE = new OperandPrototype[] {
            OperandPrototypes.DESTINATION,
            OperandPrototypes.SOURCE2,
            OperandPrototypes.SOURCE
    };

    public static final OperandPrototype[] TARGET = new OperandPrototype[] {
            OperandPrototypes.TARGET
    };

    public static final OperandPrototype[] DESTINATION_SOURCE = new OperandPrototype[] {
            OperandPrototypes.DESTINATION,
            OperandPrototypes.SOURCE
    };

    public static final OperandPrototype[] SOURCE = new OperandPrototype[] {
            OperandPrototypes.SOURCE
    };

    public static final OperandPrototype[] SOURCE2_OFFSET_BASE = new OperandPrototype[] {
            OperandPrototypes.SOURCE2,
            OperandPrototypes.OFFSET_BASE
    };

    public static final OperandPrototype[] SOURCE2_IMMEDIATE = new OperandPrototype[] {
            OperandPrototypes.SOURCE2,
            OperandPrototypes.IMMEDIATE
    };

    public static final OperandPrototype[] SOURCE2_DESTINATION = new OperandPrototype[] {
            OperandPrototypes.SOURCE2,
            OperandPrototypes.DESTINATION
    };

    public static final OperandPrototype[] DESTINATION = new OperandPrototype[] {
            OperandPrototypes.DESTINATION
    };

    public static final OperandPrototype[] HINT_OFFSET_BASE = new OperandPrototype[] {
            OperandPrototypes.HINT,
            OperandPrototypes.OFFSET_BASE
    };

    public static final OperandPrototype[] SOURCE2_WORD_IMMEDIATE = new OperandPrototype[] {
            OperandPrototypes.SOURCE2,
            OperandPrototypes.WORD_IMMEDIATE
    };

    public static final OperandPrototype[] SOURCE2_LABEL = new OperandPrototype[] {
            OperandPrototypes.SOURCE2,
            OperandPrototypes.LABEL
    };
}
