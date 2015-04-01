/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.assembler;

public class OperandPrototypes {

    private OperandPrototypes() {}

    public static final OperandPrototype DESTINATION = new OperandPrototype("destination", OperandType.REGISTER);

    public static final OperandPrototype SOURCE2 = new OperandPrototype("source2", OperandType.REGISTER);

    public static final OperandPrototype SOURCE = new OperandPrototype("source", OperandType.REGISTER);

    public static final OperandPrototype IMMEDIATE = new OperandPrototype("immediate", OperandType.IMMEDIATE);

    public static final OperandPrototype OFFSET = new OperandPrototype("offset", OperandType.OFFSET);

    public static final OperandPrototype COPROCESSOR_FUNCTION = new OperandPrototype("coprocessorFunction", OperandType.COPROCESSOR_FUNCTION);

    public static final OperandPrototype SHIFT_AMOUNT = new OperandPrototype("shiftAmount", OperandType.SHIFT_AMOUNT);

    public static final OperandPrototype TARGET = new OperandPrototype("target", OperandType.TARGET);

    public static final OperandPrototype OFFSET_BASE = new OperandPrototype("offset(base)", OperandType.OFFSET_BASE);

    public static final OperandPrototype HINT = new OperandPrototype("hint", OperandType.REGISTER);
}
