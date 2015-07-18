/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.assembler;

import me.zhanghai.mipsasm.InternalException;
import me.zhanghai.mipsasm.util.BitArray;

public class InstructionAssemblers {

    private InstructionAssemblers() {}

    private static Register getSource(Instruction instruction) {
        return (Register) instruction.getOperand(OperandPrototypes.SOURCE);
    }

    private static Register getSource2(Instruction instruction) {
        return (Register) instruction.getOperand(OperandPrototypes.SOURCE2);
    }

    private static Register getDestination(Instruction instruction) {
        return (Register) instruction.getOperand(OperandPrototypes.DESTINATION);
    }

    private static Immediate getImmediate(Instruction instruction) {
        return (Immediate) instruction.getOperand(OperandPrototypes.IMMEDIATE);
    }

    private static Offset getOffset(Instruction instruction) {
        return (Offset) instruction.getOperand(OperandPrototypes.OFFSET);
    }

    private static ShiftAmount getShiftAmount(Instruction instruction) {
        return (ShiftAmount) instruction.getOperand(OperandPrototypes.SHIFT_AMOUNT);
    }

    private static CoprocessorFunction getCoprocessorFunction(Instruction instruction) {
        return (CoprocessorFunction) instruction.getOperand(OperandPrototypes.COPROCESSOR_FUNCTION);
    }

    private static Target getTarget(Instruction instruction) {
        return (Target) instruction.getOperand(OperandPrototypes.TARGET);
    }

    private static OffsetBase getOffsetBase(Instruction instruction) {
        return (OffsetBase) instruction.getOperand(OperandPrototypes.OFFSET_BASE);
    }

    private static Register getHint(Instruction instruction) {
        return ((Register) instruction.getOperand(OperandPrototypes.HINT));
    }

    private static WordImmediate getWordImmediate(Instruction instruction) {
        return (WordImmediate) instruction.getOperand(OperandPrototypes.WORD_IMMEDIATE);
    }

    private static Label getLabel(Instruction instruction) {
        return (Label) instruction.getOperand(OperandPrototypes.LABEL);
    }

    public static final InstructionAssembler DESTINATION_SOURCE_SOURCE2 = new BaseInstructionAssembler() {
        @Override
        public void write(Instruction instruction, AssemblyContext context) {
            Operation operation = instruction.getOperation();
            context.writeBytes(BitArray.of(
                    operation.getCode(),
                    getSource(instruction).assemble(context),
                    getSource2(instruction).assemble(context),
                    getDestination(instruction).assemble(context),
                    ShiftAmount.ZERO.assemble(context),
                    operation.getFunction()));
        }
    };

    public static final InstructionAssembler SOURCE2_SOURCE_IMMEDIATE = new BaseInstructionAssembler() {
        @Override
        public void write(Instruction instruction, AssemblyContext context) {
            context.writeBytes(BitArray.of(
                    instruction.getOperation().getCode(),
                    getSource(instruction).assemble(context),
                    getSource2(instruction).assemble(context),
                    getImmediate(instruction).assemble(context)
            ));
        }
    };

    public static final InstructionAssembler SOURCE_SOURCE2_OFFSET = new BaseInstructionAssembler() {
        @Override
        public void write(Instruction instruction, AssemblyContext context) throws AssemblerException {
            context.writeBytes(BitArray.of(
                    instruction.getOperation().getCode(),
                    getSource(instruction).assemble(context),
                    getSource2(instruction).assemble(context),
                    getOffset(instruction).assemble(context)
            ));
        }
    };

    public static final InstructionAssembler SOURCE_SOURCE2_OFFSET_DELAY_SLOT =
            new DelaySlotInstructionAssembler(SOURCE_SOURCE2_OFFSET);

    public static InstructionAssembler SOURCE_OFFSET = new BaseInstructionAssembler() {
        @Override
        public void write(Instruction instruction, AssemblyContext context) throws AssemblerException {
            Operation operation = instruction.getOperation();
            context.writeBytes(BitArray.of(
                    operation.getCode(),
                    getSource(instruction).assemble(context),
                    operation.getSource2(),
                    getOffset(instruction).assemble(context)
            ));
        }
    };

    public static final InstructionAssembler SOURCE_OFFSET_DELAY_SLOT =
            new DelaySlotInstructionAssembler(SOURCE_OFFSET);

    public static final InstructionAssembler COPROCESSOR_FUNCTION = new BaseInstructionAssembler() {
        @Override
        public void write(Instruction instruction, AssemblyContext context) {
            context.writeBytes(BitArray.of(
                    instruction.getOperation().getCode(),
                    BitArray.of(0b1, 1),
                    getCoprocessorFunction(instruction).assemble(context)
            ));
        }
    };

    public static final InstructionAssembler SOURCE_SOURCE2 = new BaseInstructionAssembler() {
        @Override
        public void write(Instruction instruction, AssemblyContext context) {
            Operation operation = instruction.getOperation();
            context.writeBytes(BitArray.of(
                    operation.getCode(),
                    getSource(instruction).assemble(context),
                    getSource2(instruction).assemble(context),
                    Register.ZERO.assemble(context),
                    ShiftAmount.ZERO.assemble(context),
                    operation.getFunction()
            ));
        }
    };

    public static final InstructionAssembler CO0 = new BaseInstructionAssembler() {
        @Override
        public void write(Instruction instruction, AssemblyContext context) {
            Operation operation = instruction.getOperation();
            context.writeBytes(BitArray.of(
                    operation.getCode(),
                    BitArray.of(0b1, 1),
                    BitArray.of(0b0000000000000000000, 19),
                    operation.getFunction()
            ));
        }
    };

    public static final InstructionAssembler TARGET = new BaseInstructionAssembler() {
        @Override
        public void write(Instruction instruction, AssemblyContext context) throws AssemblerException {
            context.writeBytes(BitArray.of(
                    instruction.getOperation().getCode(),
                    getTarget(instruction).assemble(context)
            ));
        }
    };

    public static final InstructionAssembler TARGET_DELAY_SLOT = new DelaySlotInstructionAssembler(TARGET);

    public static final InstructionAssembler DESTINATION_SOURCE = new BaseInstructionAssembler() {
        @Override
        public void write(Instruction instruction, AssemblyContext context) {
            Operation operation = instruction.getOperation();
            context.writeBytes(BitArray.of(
                    operation.getCode(),
                    getSource(instruction).assemble(context),
                    Register.ZERO.assemble(context),
                    getDestination(instruction).assemble(context),
                    ShiftAmount.ZERO.assemble(context),
                    operation.getFunction()
            ));
        }
    };

    public static final InstructionAssembler DESTINATION_SOURCE_DELAY_SLOT =
            new DelaySlotInstructionAssembler(DESTINATION_SOURCE);

    public static final InstructionAssembler SOURCE = new BaseInstructionAssembler() {
        @Override
        public void write(Instruction instruction, AssemblyContext context) {
            Operation operation = instruction.getOperation();
            context.writeBytes(BitArray.of(
                    operation.getCode(),
                    getSource(instruction).assemble(context),
                    Register.ZERO.assemble(context),
                    Register.ZERO.assemble(context),
                    ShiftAmount.ZERO.assemble(context),
                    operation.getFunction()
            ));
        }
    };

    public static final InstructionAssembler SOURCE_DELAY_SLOT = new DelaySlotInstructionAssembler(SOURCE);

    public static final InstructionAssembler SOURCE2_OFFSET_BASE = new BaseInstructionAssembler() {
        @Override
        public void write(Instruction instruction, AssemblyContext context) {
            OffsetBase offsetBase = getOffsetBase(instruction);
            context.writeBytes(BitArray.of(
                    instruction.getOperation().getCode(),
                    offsetBase.getBase().assemble(context),
                    getSource2(instruction).assemble(context),
                    offsetBase.getOffset().assemble(context)
            ));
        }
    };

    public static final InstructionAssembler SOURCE2_IMMEDIATE = new BaseInstructionAssembler() {
        @Override
        public void write(Instruction instruction, AssemblyContext context) {
            context.writeBytes(BitArray.of(
                    instruction.getOperation().getCode(),
                    Register.ZERO.assemble(context),
                    getSource2(instruction).assemble(context),
                    getImmediate(instruction).assemble(context)
            ));
        }
    };

    public static final InstructionAssembler SOURCE2_DESTINATION = new BaseInstructionAssembler() {
        @Override
        public void write(Instruction instruction, AssemblyContext context) throws AssemblerException {
            Operation operation = instruction.getOperation();
            context.writeBytes(BitArray.of(
                    operation.getCode(),
                    operation.getSource(),
                    getSource2(instruction).assemble(context),
                    getDestination(instruction).assemble(context),
                    BitArray.of(0b0000000, 8),
                    // sel, 0 according to SQS.
                    BitArray.of(0b000, 3)
            ));
        }
    };

    public static final InstructionAssembler DESTINATION = new BaseInstructionAssembler() {
        @Override
        public void write(Instruction instruction, AssemblyContext context) {
            Operation operation = instruction.getOperation();
            context.writeBytes(BitArray.of(
                    operation.getCode(),
                    Register.ZERO.assemble(context),
                    Register.ZERO.assemble(context),
                    getDestination(instruction).assemble(context),
                    ShiftAmount.ZERO.assemble(context),
                    operation.getFunction()
            ));
        }
    };

    public static final InstructionAssembler HINT_OFFSET_BASE = new BaseInstructionAssembler() {
        @Override
        public void write(Instruction instruction, AssemblyContext context) {
            OffsetBase offsetBase = (OffsetBase) instruction.getOperand(OperandPrototypes.OFFSET_BASE);
            context.writeBytes(BitArray.of(
                    instruction.getOperation().getCode(),
                    offsetBase.getBase().assemble(context),
                    getHint(instruction).assemble(context),
                    offsetBase.getOffset().assemble(context)
            ));
        }
    };

    public static final InstructionAssembler DESTINATION_SOURCE2_SHIFT_AMOUNT = new BaseInstructionAssembler() {
        @Override
        public void write(Instruction instruction, AssemblyContext context) {
            Operation operation = instruction.getOperation();
            context.writeBytes(BitArray.of(
                    operation.getCode(),
                    Register.ZERO.assemble(context),
                    getSource2(instruction).assemble(context),
                    getDestination(instruction).assemble(context),
                    getShiftAmount(instruction).assemble(context),
                    operation.getFunction()
            ));
        }
    };

    public static final InstructionAssembler DESTINATION_SOURCE2_SOURCE = new BaseInstructionAssembler() {
        @Override
        public void write(Instruction instruction, AssemblyContext context) {
            Operation operation = instruction.getOperation();
            context.writeBytes(BitArray.of(
                    operation.getCode(),
                    getSource(instruction).assemble(context),
                    getSource2(instruction).assemble(context),
                    getDestination(instruction).assemble(context),
                    ShiftAmount.ZERO.assemble(context),
                    operation.getFunction()));
        }
    };

    public static final InstructionAssembler B = new BaseTransformInstructionAssembler() {
        @Override
        protected Instruction transformInstruction(Instruction instruction, AssemblyContext context)
                throws AssemblerException {
            return Instruction.of(Operation.BEQ, new OperandInstance[]{
                    OperandInstance.fromPrototype(OperandPrototypes.SOURCE, Register.ZERO),
                    OperandInstance.fromPrototype(OperandPrototypes.SOURCE2, Register.ZERO),
                    OperandInstance.fromPrototype(OperandPrototypes.OFFSET, getOffset(instruction))
            });
        }
    };

    public static final InstructionAssembler LI = new BaseMultipleTransformInstructionAssembler() {
        @Override
        protected Instruction[] transformInstruction(Instruction instruction, AssemblyContext context)
                throws AssemblerException {
            Register source2 = getSource2(instruction);
            WordImmediate wordImmediate = getWordImmediate(instruction);
            return new Instruction[] {
                    Instruction.of(Operation.LUI, new OperandInstance[] {
                            OperandInstance.fromPrototype(OperandPrototypes.SOURCE2, source2),
                            OperandInstance.fromPrototype(OperandPrototypes.IMMEDIATE,
                                    Immediate.of(wordImmediate.getUpper().value()))
                    }),
                    Instruction.of(Operation.ORI, new OperandInstance[] {
                            OperandInstance.fromPrototype(OperandPrototypes.SOURCE2, source2),
                            OperandInstance.fromPrototype(OperandPrototypes.SOURCE, source2),
                            OperandInstance.fromPrototype(OperandPrototypes.IMMEDIATE,
                                    Immediate.of(wordImmediate.getLower().value()))
                    })
            };
        }
    };

    public static final InstructionAssembler LA = new BaseTransformInstructionAssembler() {
        @Override
        public void allocate(Instruction instruction, AssemblyContext context) {
            LI.allocate(Instruction.of(Operation.LI, new OperandInstance[] {
                    OperandInstance.fromPrototype(OperandPrototypes.SOURCE2, getSource2(instruction)),
                    // HACK: In allocation phase, label may not yet be available, however a valid structure of
                    // instruction is required in the implementation of LI.
                    OperandInstance.fromPrototype(OperandPrototypes.WORD_IMMEDIATE, WordImmediate.of(0))
            }), context);
        }
        @Override
        protected Instruction transformInstruction(Instruction instruction, AssemblyContext context)
                throws AssemblerException {
            return Instruction.of(Operation.LI, new OperandInstance[] {
                    OperandInstance.fromPrototype(OperandPrototypes.SOURCE2, getSource2(instruction)),
                    OperandInstance.fromPrototype(OperandPrototypes.WORD_IMMEDIATE,
                            WordImmediate.of(context.getLabelAddress(getLabel(instruction))))
            });
        }
    };

    public static final InstructionAssembler MOVE = new BaseTransformInstructionAssembler() {
        @Override
        protected Instruction transformInstruction(Instruction instruction, AssemblyContext context) {
            return Instruction.of(Operation.ADD, new OperandInstance[] {
                    OperandInstance.fromPrototype(OperandPrototypes.DESTINATION, getDestination(instruction)),
                    OperandInstance.fromPrototype(OperandPrototypes.SOURCE, getSource(instruction)),
                    OperandInstance.fromPrototype(OperandPrototypes.SOURCE2, Register.ZERO)
            });
        }
    };

    public static final InstructionAssembler NOP = new BaseTransformInstructionAssembler() {
        @Override
        protected Instruction transformInstruction(Instruction instruction, AssemblyContext context) {
            return Instruction.of(Operation.SLL, new OperandInstance[] {
                    OperandInstance.fromPrototype(OperandPrototypes.DESTINATION, Register.ZERO),
                    OperandInstance.fromPrototype(OperandPrototypes.SOURCE2, Register.ZERO),
                    OperandInstance.fromPrototype(OperandPrototypes.SHIFT_AMOUNT, ShiftAmount.ZERO)
            });
        }
    };

    public static final InstructionAssembler CODE = new BaseInstructionAssembler() {
        @Override
        public void write(Instruction instruction, AssemblyContext context) throws AssemblerException {
            Operation operation = instruction.getOperation();
            context.writeBytes(BitArray.of(
                    operation.getCode(),
                    BitArray.of(0b00000000000000000000, 20),
                    operation.getFunction()
            ));
        }
    };

    // WAIT instruction is implementation-dependent.
    public static final InstructionAssembler WAIT = CO0;

    public static abstract class BaseInstructionAssembler implements InstructionAssembler {
        @Override
        public void allocate(Instruction instruction, AssemblyContext context) {
            // Most instructions only offsets a word.
            context.allocateWord();
        }
    }

    public static abstract class BaseTransformInstructionAssembler implements InstructionAssembler {
        protected abstract Instruction transformInstruction(Instruction instruction, AssemblyContext context)
                throws AssemblerException;
        @Override
        public void allocate(Instruction instruction, AssemblyContext context) {
            try {
                transformInstruction(instruction, context).allocate(context);
            } catch (AssemblerException e) {
                throw new InternalException(
                        "Should override allocate() if transformInstruction() can throw an AssemblerException", e);
            }
        }
        @Override
        public void write(Instruction instruction, AssemblyContext context) throws AssemblerException {
            transformInstruction(instruction, context).write(context);
        }
    }

    public static abstract class BaseMultipleTransformInstructionAssembler implements InstructionAssembler {
        protected abstract Instruction[] transformInstruction(Instruction instruction, AssemblyContext context)
                throws AssemblerException;
        @Override
        public void allocate(Instruction instruction, AssemblyContext context) {
            try {
                for (Instruction transformedInstruction : transformInstruction(instruction, context)) {
                    transformedInstruction.allocate(context);
                }
            } catch (AssemblerException e) {
                throw new InternalException(
                        "Should override allocate() if transformInstruction() can throw an AssemblerException", e);
            }
        }
        @Override
        public void write(Instruction instruction, AssemblyContext context) throws AssemblerException {
            for (Instruction transformedInstruction : transformInstruction(instruction, context)) {
                transformedInstruction.write(context);
            }
        }
    }

    public static class DelaySlotInstructionAssembler implements InstructionAssembler {
        private static final Instruction NOP = Instruction.of(Operation.NOP, new OperandInstance[] {});
        private InstructionAssembler assembler;
        public DelaySlotInstructionAssembler(InstructionAssembler assembler) {
            this.assembler = assembler;
        }
        @Override
        public void allocate(Instruction instruction, AssemblyContext context) {
            assembler.allocate(instruction, context);
            if (AssemblerPreferences.getDelaySlotEnabled()) {
                NOP.allocate(context);
            }
        }
        @Override
        public void write(Instruction instruction, AssemblyContext context) throws AssemblerException {
            assembler.write(instruction, context);
            if (AssemblerPreferences.getDelaySlotEnabled()) {
                NOP.write(context);
            }
        }
    }
}
