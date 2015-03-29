/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.instruction;

import me.zhanghai.mipsasm.util.BitArray;

public class InstructionCompilers {

    private InstructionCompilers() {}

    public static final InstructionCompiler DESTINATION_SOURCE_TARGET = new InstructionCompiler() {
        @Override
        public BitArray[] compile(Instruction instruction) {
            return new BitArray[] {BitArray.of(
                    instruction.getOperation().getCode(),
                    ((Register)instruction.getOperand(OperandPrototypes.SOURCE.getName())).getBits(),
                    ((Register)instruction.getOperand(OperandPrototypes.TARGET.getName())).getBits(),
                    ((Register)instruction.getOperand(OperandPrototypes.DESTINATION.getName())).getBits(),
                    ShiftAmount.ZERO.getBits(),
                    instruction.getOperation().getFunction())};
        }
    };

    public static final InstructionCompiler TARGET_SOURCE_IMMEDIATE = new InstructionCompiler() {
        @Override
        public BitArray[] compile(Instruction instruction) {
            return new BitArray[] {BitArray.of(
                    instruction.getOperation().getCode(),
                    ((Register)instruction.getOperand(OperandPrototypes.SOURCE.getName())).getBits(),
                    ((Register)instruction.getOperand(OperandPrototypes.TARGET.getName())).getBits(),
                    ((Immediate)instruction.getOperand(OperandPrototypes.IMMEDIATE.getName())).getBits()
            )};
        }
    };

    public static final InstructionCompiler SOURCE_TARGET_OFFSET = new InstructionCompiler() {
        @Override
        public BitArray[] compile(Instruction instruction) {
            return new BitArray[] {BitArray.of(
                    instruction.getOperation().getCode(),
                    ((Register)instruction.getOperand(OperandPrototypes.SOURCE.getName())).getBits(),
                    ((Register)instruction.getOperand(OperandPrototypes.TARGET.getName())).getBits(),
                    ((Immediate)instruction.getOperand(OperandPrototypes.OFFSET.getName())).getBits()
            )};
        }
    };

    public static InstructionCompiler SOURCE_OFFSET(final Register destination) {
        return new InstructionCompiler() {
            @Override
            public BitArray[] compile(Instruction instruction) {
                return new BitArray[]{BitArray.of(
                        instruction.getOperation().getCode(),
                        ((Register)instruction.getOperand(OperandPrototypes.SOURCE.getName())).getBits(),
                        destination.getBits(),
                        ((Immediate)instruction.getOperand(OperandPrototypes.OFFSET.getName())).getBits()
                )};
            }
        };
    }

    public static final InstructionCompiler COPROCESSOR_FUNCTION = new InstructionCompiler() {
        @Override
        public BitArray[] compile(Instruction instruction) {
            return new BitArray[] {BitArray.of(
                    instruction.getOperation().getCode(),
                    ((CoprocessorFunction)instruction.getOperand(OperandPrototypes.COPROCESSOR_FUNCTION.getName())).getBits()
            )};
        }
    };

    public static final InstructionCompiler SOURCE_TARGET = new InstructionCompiler() {
        @Override
        public BitArray[] compile(Instruction instruction) {
            return new BitArray[] {BitArray.of(
                    instruction.getOperation().getCode(),
                    ((Register)instruction.getOperand(OperandPrototypes.SOURCE.getName())).getBits(),
                    ((Register)instruction.getOperand(OperandPrototypes.TARGET.getName())).getBits(),
                    Register.ZERO.getBits(),
                    ShiftAmount.ZERO.getBits(),
                    instruction.getOperation().getFunction()
            )};
        }
    };
}
