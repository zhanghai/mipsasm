/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.assembler;

import me.zhanghai.mipsasm.util.BitArray;

public class InstructionAssemblers {

    private InstructionAssemblers() {}

    public static final InstructionAssembler DESTINATION_SOURCE_SOURCE2 = new BaseInstructionAssembler() {
        @Override
        public void assemble(Instruction instruction, AssemblyContext context) {
            Operation operation = instruction.getOperation();
            context.appendAssembly(BitArray.of(
                    operation.getCode(),
                    ((Register) instruction.getOperand(OperandPrototypes.SOURCE)).assemble(context),
                    ((Register) instruction.getOperand(OperandPrototypes.SOURCE2)).assemble(context),
                    ((Register) instruction.getOperand(OperandPrototypes.DESTINATION)).assemble(context),
                    ShiftAmount.ZERO.assemble(context),
                    operation.getFunction()));
        }
    };

    public static final InstructionAssembler SOURCE2_SOURCE_IMMEDIATE = new BaseInstructionAssembler() {
        @Override
        public void assemble(Instruction instruction, AssemblyContext context) {
            context.appendAssembly(BitArray.of(
                    instruction.getOperation().getCode(),
                    ((Register) instruction.getOperand(OperandPrototypes.SOURCE)).assemble(context),
                    ((Register) instruction.getOperand(OperandPrototypes.SOURCE2)).assemble(context),
                    ((Immediate) instruction.getOperand(OperandPrototypes.IMMEDIATE)).assemble(context)
            ));
        }
    };

    public static final InstructionAssembler SOURCE_SOURCE2_OFFSET = new BaseInstructionAssembler() {
        @Override
        public void assemble(Instruction instruction, AssemblyContext context) throws AssemblerException {
            context.appendAssembly(BitArray.of(
                    instruction.getOperation().getCode(),
                    ((Register) instruction.getOperand(OperandPrototypes.SOURCE)).assemble(context),
                    ((Register) instruction.getOperand(OperandPrototypes.SOURCE2)).assemble(context),
                    ((Offset) instruction.getOperand(OperandPrototypes.OFFSET)).assemble(context)
            ));
        }
    };

    public static InstructionAssembler SOURCE_OFFSET(final Register destination) {
        return new BaseInstructionAssembler() {
            @Override
            public void assemble(Instruction instruction, AssemblyContext context) throws AssemblerException {
                context.appendAssembly(BitArray.of(
                        instruction.getOperation().getCode(),
                        ((Register) instruction.getOperand(OperandPrototypes.SOURCE)).assemble(context),
                        destination.assemble(context),
                        ((Offset) instruction.getOperand(OperandPrototypes.OFFSET)).assemble(context)
                ));
            }
        };
    }

    public static final InstructionAssembler COPROCESSOR_FUNCTION = new BaseInstructionAssembler() {
        @Override
        public void assemble(Instruction instruction, AssemblyContext context) {
            context.appendAssembly(BitArray.of(
                    instruction.getOperation().getCode(),
                    ((CoprocessorFunction) instruction.getOperand(OperandPrototypes.COPROCESSOR_FUNCTION)).assemble(context)
            ));
        }
    };

    public static final InstructionAssembler SOURCE_SOURCE2 = new BaseInstructionAssembler() {
        @Override
        public void assemble(Instruction instruction, AssemblyContext context) {
            Operation operation = instruction.getOperation();
            context.appendAssembly(BitArray.of(
                    operation.getCode(),
                    ((Register) instruction.getOperand(OperandPrototypes.SOURCE)).assemble(context),
                    ((Register) instruction.getOperand(OperandPrototypes.SOURCE2)).assemble(context),
                    Register.ZERO.assemble(context),
                    ShiftAmount.ZERO.assemble(context),
                    operation.getFunction()
            ));
        }
    };

    public static final InstructionAssembler DESTINATION_SOURCE2_SHIFT_AMOUNT = new BaseInstructionAssembler() {
        @Override
        public void assemble(Instruction instruction, AssemblyContext context) {
            Operation operation = instruction.getOperation();
            context.appendAssembly(BitArray.of(
                    operation.getCode(),
                    Register.ZERO.assemble(context),
                    ((Register) instruction.getOperand(OperandPrototypes.DESTINATION)).assemble(context),
                    ((Register) instruction.getOperand(OperandPrototypes.SOURCE2)).assemble(context),
                    ((ShiftAmount) instruction.getOperand(OperandPrototypes.SHIFT_AMOUNT)).assemble(context),
                    operation.getFunction()
            ));
        }
    };


    public static final InstructionAssembler DESTINATION_SOURCE2_SOURCE = new BaseInstructionAssembler() {
        @Override
        public void assemble(Instruction instruction, AssemblyContext context) {
            Operation operation = instruction.getOperation();
            context.appendAssembly(BitArray.of(
                    operation.getCode(),
                    ((Register) instruction.getOperand(OperandPrototypes.SOURCE)).assemble(context),
                    ((Register) instruction.getOperand(OperandPrototypes.SOURCE2)).assemble(context),
                    ((Register) instruction.getOperand(OperandPrototypes.DESTINATION)).assemble(context),
                    ShiftAmount.ZERO.assemble(context),
                    operation.getFunction()));
        }
    };

    public static final InstructionAssembler TARGET = new BaseInstructionAssembler() {
        @Override
        public void assemble(Instruction instruction, AssemblyContext context) throws AssemblerException {
            context.appendAssembly(BitArray.of(
                    instruction.getOperation().getCode(),
                    ((Target) instruction.getOperand(OperandPrototypes.TARGET)).assemble(context)
            ));
        }
    };

    public static final InstructionAssembler DESTINATION_SOURCE = new BaseInstructionAssembler() {
        @Override
        public void assemble(Instruction instruction, AssemblyContext context) {
            Operation operation = instruction.getOperation();
            context.appendAssembly(BitArray.of(
                    operation.getCode(),
                    ((Register) instruction.getOperand(OperandPrototypes.SOURCE)).assemble(context),
                    Register.ZERO.assemble(context),
                    ((Register) instruction.getOperand(OperandPrototypes.DESTINATION)).assemble(context),
                    ShiftAmount.ZERO.assemble(context),
                    operation.getFunction()
            ));
        }
    };

    public static final InstructionAssembler SOURCE = new BaseInstructionAssembler() {
        @Override
        public void assemble(Instruction instruction, AssemblyContext context) {
            Operation operation = instruction.getOperation();
            context.appendAssembly(BitArray.of(
                    operation.getCode(),
                    ((Register) instruction.getOperand(OperandPrototypes.SOURCE)).assemble(context),
                    Register.ZERO.assemble(context),
                    Register.ZERO.assemble(context),
                    ShiftAmount.ZERO.assemble(context),
                    operation.getFunction()
            ));
        }
    };

    public static final InstructionAssembler SOURCE2_OFFSET_BASE = new BaseInstructionAssembler() {
        @Override
        public void assemble(Instruction instruction, AssemblyContext context) {
            OffsetBase offsetBase = (OffsetBase) instruction.getOperand(OperandPrototypes.OFFSET_BASE);
            context.appendAssembly(BitArray.of(
                    instruction.getOperation().getCode(),
                    offsetBase.getBase().assemble(context),
                    ((Register) instruction.getOperand(OperandPrototypes.SOURCE2)).assemble(context),
                    offsetBase.getOffset().assemble(context)
            ));
        }
    };

    public static final InstructionAssembler SOURCE2_IMMEDIATE = new BaseInstructionAssembler() {
        @Override
        public void assemble(Instruction instruction, AssemblyContext context) {
            context.appendAssembly(BitArray.of(
                    instruction.getOperation().getCode(),
                    Register.ZERO.assemble(context),
                    ((Register) instruction.getOperand(OperandPrototypes.SOURCE2)).assemble(context),
                    ((Immediate) instruction.getOperand(OperandPrototypes.IMMEDIATE)).assemble(context)
            ));
        }
    };

    public static final InstructionAssembler DESTINATION = new BaseInstructionAssembler() {
        @Override
        public void assemble(Instruction instruction, AssemblyContext context) {
            Operation operation = instruction.getOperation();
            context.appendAssembly(BitArray.of(
                    operation.getCode(),
                    Register.ZERO.assemble(context),
                    Register.ZERO.assemble(context),
                    ((Register) instruction.getOperand(OperandPrototypes.DESTINATION)).assemble(context),
                    ShiftAmount.ZERO.assemble(context),
                    operation.getFunction()
            ));
        }
    };

    public static final InstructionAssembler HINT_OFFSET_BASE = new BaseInstructionAssembler() {
        @Override
        public void assemble(Instruction instruction, AssemblyContext context) {
            OffsetBase offsetBase = (OffsetBase) instruction.getOperand(OperandPrototypes.OFFSET_BASE);
            context.appendAssembly(BitArray.of(
                    instruction.getOperation().getCode(),
                    offsetBase.getBase().assemble(context),
                    ((Register) instruction.getOperand(OperandPrototypes.HINT)).assemble(context),
                    offsetBase.getOffset().assemble(context)
            ));
        }
    };

    public static abstract class BaseInstructionAssembler implements InstructionAssembler {
        @Override
        public void offset(Instruction instruction, AssemblyContext context) {
            // Most instructions only offsets a word.
            context.offsetByWord();
        }
    }
}
