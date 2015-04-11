/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.assembler;

public class OperandPrototypes {

    private OperandPrototypes() {}

    public static final OperandPrototype DESTINATION = OperandPrototype.of("destination", OperandType.REGISTER);

    public static final OperandPrototype SOURCE2 = OperandPrototype.of("source2", OperandType.REGISTER);

    public static final OperandPrototype SOURCE = OperandPrototype.of("source", OperandType.REGISTER);

    public static final OperandPrototype IMMEDIATE = OperandPrototype.of("immediate", OperandType.IMMEDIATE);

    public static final OperandPrototype OFFSET = OperandPrototype.of("offset", OperandType.OFFSET);

    public static final OperandPrototype COPROCESSOR_FUNCTION = OperandPrototype.of("coprocessorFunction", OperandType.COPROCESSOR_FUNCTION);

    public static final OperandPrototype SHIFT_AMOUNT = OperandPrototype.of("shiftAmount", OperandType.SHIFT_AMOUNT);

    public static final OperandPrototype TARGET = OperandPrototype.of("target", OperandType.TARGET);

    public static final OperandPrototype OFFSET_BASE = OperandPrototype.of("offset(base)", OperandType.OFFSET_BASE);

    public static final OperandPrototype HINT = OperandPrototype.of("hint", OperandType.REGISTER);

    public static final OperandPrototype WORD_IMMEDIATE = OperandPrototype.of("wordImmediate", OperandType.WORD_IMMEDIATE);

    public static final OperandPrototype LABEL = OperandPrototype.of("label", OperandType.LABEL);
}
