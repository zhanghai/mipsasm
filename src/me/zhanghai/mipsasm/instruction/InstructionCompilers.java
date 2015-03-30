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
            Operation operation = instruction.getOperation();
            return new BitArray[] {BitArray.of(
                    operation.getCode(),
                    ((Register) instruction.getOperand(OperandPrototypes.SOURCE.getName())).compile(),
                    ((Register) instruction.getOperand(OperandPrototypes.TARGET.getName())).compile(),
                    ((Register) instruction.getOperand(OperandPrototypes.DESTINATION.getName())).compile(),
                    ShiftAmount.ZERO.compile(),
                    operation.getFunction())};
        }
    };

    public static final InstructionCompiler TARGET_SOURCE_IMMEDIATE = new InstructionCompiler() {
        @Override
        public BitArray[] compile(Instruction instruction) {
            return new BitArray[] {BitArray.of(
                    instruction.getOperation().getCode(),
                    ((Register)instruction.getOperand(OperandPrototypes.SOURCE.getName())).compile(),
                    ((Register)instruction.getOperand(OperandPrototypes.TARGET.getName())).compile(),
                    ((Immediate)instruction.getOperand(OperandPrototypes.IMMEDIATE.getName())).compile()
            )};
        }
    };

    public static final InstructionCompiler SOURCE_TARGET_OFFSET = new InstructionCompiler() {
        @Override
        public BitArray[] compile(Instruction instruction) {
            return new BitArray[] {BitArray.of(
                    instruction.getOperation().getCode(),
                    ((Register)instruction.getOperand(OperandPrototypes.SOURCE.getName())).compile(),
                    ((Register)instruction.getOperand(OperandPrototypes.TARGET.getName())).compile(),
                    ((Immediate)instruction.getOperand(OperandPrototypes.OFFSET.getName())).compile()
            )};
        }
    };

    public static InstructionCompiler SOURCE_OFFSET(final Register destination) {
        return new InstructionCompiler() {
            @Override
            public BitArray[] compile(Instruction instruction) {
                return new BitArray[]{BitArray.of(
                        instruction.getOperation().getCode(),
                        ((Register)instruction.getOperand(OperandPrototypes.SOURCE.getName())).compile(),
                        destination.compile(),
                        ((Immediate)instruction.getOperand(OperandPrototypes.OFFSET.getName())).compile()
                )};
            }
        };
    }

    public static final InstructionCompiler COPROCESSOR_FUNCTION = new InstructionCompiler() {
        @Override
        public BitArray[] compile(Instruction instruction) {
            return new BitArray[] {BitArray.of(
                    instruction.getOperation().getCode(),
                    ((CoprocessorFunction)instruction.getOperand(OperandPrototypes.COPROCESSOR_FUNCTION.getName())).compile()
            )};
        }
    };

    public static final InstructionCompiler SOURCE_TARGET = new InstructionCompiler() {
        @Override
        public BitArray[] compile(Instruction instruction) {
            Operation operation = instruction.getOperation();
            return new BitArray[] {BitArray.of(
                    operation.getCode(),
                    ((Register)instruction.getOperand(OperandPrototypes.SOURCE.getName())).compile(),
                    ((Register)instruction.getOperand(OperandPrototypes.TARGET.getName())).compile(),
                    Register.ZERO.compile(),
                    ShiftAmount.ZERO.compile(),
                    operation.getFunction()
            )};
        }
    };

    public static final InstructionCompiler DESTINATION_TARGET_SHIFT_AMOUNT = new InstructionCompiler() {
        @Override
        public BitArray[] compile(Instruction instruction) {
            Operation operation = instruction.getOperation();
            return new BitArray[] {BitArray.of(
                    operation.getCode(),
                    Register.ZERO.compile(),
                    ((Register) instruction.getOperand(OperandPrototypes.DESTINATION.getName())).compile(),
                    ((Register) instruction.getOperand(OperandPrototypes.TARGET.getName())).compile(),
                    ((ShiftAmount) instruction.getOperand(OperandPrototypes.SHIFT_AMOUNT.getName())).compile(),
                    operation.getFunction()
            )};
        }
    };


    public static final InstructionCompiler DESTINATION_TARGET_SOURCE = new InstructionCompiler() {
        @Override
        public BitArray[] compile(Instruction instruction) {
            Operation operation = instruction.getOperation();
            return new BitArray[] {BitArray.of(
                    operation.getCode(),
                    ((Register) instruction.getOperand(OperandPrototypes.SOURCE.getName())).compile(),
                    ((Register) instruction.getOperand(OperandPrototypes.TARGET.getName())).compile(),
                    ((Register) instruction.getOperand(OperandPrototypes.DESTINATION.getName())).compile(),
                    ShiftAmount.ZERO.compile(),
                    operation.getFunction())};
        }
    };

    public static final InstructionCompiler LABEL = new InstructionCompiler() {
        @Override
        public BitArray[] compile(Instruction instruction) {
            return new BitArray[] {BitArray.of(
                    instruction.getOperation().getCode(),
                    ((Label)instruction.getOperand(OperandPrototypes.LABEL.getName())).compile()
            )};
        }
    };

    public static final InstructionCompiler DESTINATION_SOURCE = new InstructionCompiler() {
        @Override
        public BitArray[] compile(Instruction instruction) {
            Operation operation = instruction.getOperation();
            return new BitArray[] {BitArray.of(
                    operation.getCode(),
                    ((Register) instruction.getOperand(OperandPrototypes.SOURCE.getName())).compile(),
                    Register.ZERO.compile(),
                    ((Register) instruction.getOperand(OperandPrototypes.DESTINATION.getName())).compile(),
                    ShiftAmount.ZERO.compile(),
                    operation.getFunction()
            )};
        }
    };

    public static final InstructionCompiler SOURCE = new InstructionCompiler() {
        @Override
        public BitArray[] compile(Instruction instruction) {
            Operation operation = instruction.getOperation();
            return new BitArray[] {BitArray.of(
                    operation.getCode(),
                    ((Register) instruction.getOperand(OperandPrototypes.SOURCE.getName())).compile(),
                    Register.ZERO.compile(),
                    Register.ZERO.compile(),
                    ShiftAmount.ZERO.compile(),
                    operation.getFunction()
            )};
        }
    };

    public static final InstructionCompiler TARGET_OFFSET_BASE = new InstructionCompiler() {
        @Override
        public BitArray[] compile(Instruction instruction) {
            OffsetBase offsetBase = (OffsetBase)instruction.getOperand(OperandPrototypes.OFFSET_BASE.getName());
            return new BitArray[] {BitArray.of(
                    instruction.getOperation().getCode(),
                    offsetBase.getBase().compile(),
                    ((Register)instruction.getOperand(OperandPrototypes.TARGET.getName())).compile(),
                    offsetBase.getOffset().compile()
            )};
        }
    };

    public static final InstructionCompiler TARGET_IMMEDIATE = new InstructionCompiler() {
        @Override
        public BitArray[] compile(Instruction instruction) {
            return new BitArray[] {BitArray.of(
                    instruction.getOperation().getCode(),
                    Register.ZERO.compile(),
                    ((Register)instruction.getOperand(OperandPrototypes.TARGET.getName())).compile(),
                    ((Immediate)instruction.getOperand(OperandPrototypes.IMMEDIATE.getName())).compile()
            )};
        }
    };

    public static final InstructionCompiler DESTINATION = new InstructionCompiler() {
        @Override
        public BitArray[] compile(Instruction instruction) {
            Operation operation = instruction.getOperation();
            return new BitArray[] {BitArray.of(
                    operation.getCode(),
                    Register.ZERO.compile(),
                    Register.ZERO.compile(),
                    ((Register) instruction.getOperand(OperandPrototypes.DESTINATION.getName())).compile(),
                    ShiftAmount.ZERO.compile(),
                    operation.getFunction()
            )};
        }
    };

    public static final InstructionCompiler HINT_OFFSET_BASE = new InstructionCompiler() {
        @Override
        public BitArray[] compile(Instruction instruction) {
            OffsetBase offsetBase = (OffsetBase)instruction.getOperand(OperandPrototypes.OFFSET_BASE.getName());
            return new BitArray[] {BitArray.of(
                    instruction.getOperation().getCode(),
                    offsetBase.getBase().compile(),
                    ((Register)instruction.getOperand(OperandPrototypes.HINT.getName())).compile(),
                    offsetBase.getOffset().compile()
            )};
        }
    };
}
