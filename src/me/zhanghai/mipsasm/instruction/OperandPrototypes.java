/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.instruction;

public class OperandPrototypes {

    private OperandPrototypes() {}

    public static final OperandPrototype DESTINATION = new OperandPrototype("destination", OperandType.REGISTER);

    public static final OperandPrototype TARGET = new OperandPrototype("target", OperandType.REGISTER);

    public static final OperandPrototype SOURCE = new OperandPrototype("source", OperandType.REGISTER);

    public static final OperandPrototype IMMEDIATE = new OperandPrototype("immediate", OperandType.IMMEDIATE);

    public static final OperandPrototype OFFSET = new OperandPrototype("offset", OperandType.IMMEDIATE);

    public static final OperandPrototype COPROCESSOR_FUNCTION = new OperandPrototype("coprocessorFunction", OperandType.COPROCESSOR_FUNCTION);
}
