/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.disassembler;

import me.zhanghai.mipsasm.Constants;
import me.zhanghai.mipsasm.assembler.*;
import me.zhanghai.mipsasm.util.BitArray;

// These disassemblers is lenient: bits that should be zero filled are ignored.
public class InstructionDisassemblers {

    private static OperandInstance getDestination(BitArray bitArray) {
        return OperandInstance.fromPrototype(OperandPrototypes.DESTINATION,
                Register.of(bitArray.subArray(11, 16).value()));
    }

    private static OperandInstance getSource(BitArray bitArray) {
        return OperandInstance.fromPrototype(OperandPrototypes.SOURCE, Register.of(bitArray.subArray(21, 26).value()));
    }

    private static OperandInstance getSource2(BitArray bitArray) {
        return OperandInstance.fromPrototype(OperandPrototypes.SOURCE2, Register.of(bitArray.subArray(16, 21).value()));
    }

    private static OperandInstance getImmediate(BitArray bitArray) {
        return OperandInstance.fromPrototype(OperandPrototypes.IMMEDIATE,
                Immediate.of(bitArray.subArray(0, 16).value()));
    }

    private static OperandInstance getOffset(BitArray bitArray, DisassemblyContext context) {
        int address = context.getAddress() + Constants.BYTES_PER_INSTRUCTION
                + bitArray.subArray(0, 16).integerValue() * Constants.BYTES_PER_INSTRUCTION;
        return OperandInstance.fromPrototype(OperandPrototypes.OFFSET,
                Offset.of(OffsetLabel.of(context.addLabel(address))));
    }

    private static OperandInstance getCoprocessorFunction(BitArray bitArray) {
        return OperandInstance.fromPrototype(OperandPrototypes.COPROCESSOR_FUNCTION,
                CoprocessorFunction.of(bitArray.subArray(0, 25).value()));
    }

    private static OperandInstance getTarget(BitArray bitArray, DisassemblyContext context) {
        int address = BitArray.of(
                BitArray.of(context.getAddress() + Constants.BYTES_PER_INSTRUCTION, Constants.ADDRESS_LENGTH)
                        .subArray(28, Constants.ADDRESS_LENGTH),
                bitArray.subArray(0, 26).setLength(28).leftShift(2)
        ).value();
        return OperandInstance.fromPrototype(OperandPrototypes.TARGET,
                Target.of(TargetLabel.of(context.addLabel(address))));
    }

    private static OperandInstance getOffsetBase(BitArray bitArray) {
        return OperandInstance.fromPrototype(OperandPrototypes.OFFSET_BASE,
                OffsetBase.of(Register.of(bitArray.subArray(21, 26).value()),
                        Immediate.of(bitArray.subArray(0, 16).value())));
    }

    private static OperandInstance getHint(BitArray bitArray) {
        return OperandInstance.fromPrototype(OperandPrototypes.HINT, Register.of(bitArray.subArray(16, 21).value()));
    }

    private static OperandInstance getShiftAmount(BitArray bitArray) {
        return OperandInstance.fromPrototype(OperandPrototypes.SHIFT_AMOUNT,
                ShiftAmount.of(bitArray.subArray(6, 11).value()));
    }

    public static final InstructionDisassembler EMPTY = new BaseInstructionDisassembler() {
        @Override
        public Instruction getInstruction(InstructionInformation information, BitArray bitArray,
                                          DisassemblyContext context) {
            return Instruction.of(information.getOperation(), new OperandInstance[] {});
        }
    };

    public static final InstructionDisassembler DESTINATION_SOURCE_SOURCE2 = new BaseInstructionDisassembler() {
        @Override
        public Instruction getInstruction(InstructionInformation information, BitArray bitArray,
                                DisassemblyContext context) {
            return Instruction.of(information.getOperation(), new OperandInstance[] {
                    getDestination(bitArray),
                    getSource(bitArray),
                    getSource2(bitArray)
            });
        }
    };

    public static final InstructionDisassembler SOURCE2_SOURCE_IMMEDIATE = new BaseInstructionDisassembler() {
        @Override
        public Instruction getInstruction(InstructionInformation information, BitArray bitArray,
                                DisassemblyContext context) {
            return Instruction.of(information.getOperation(), new OperandInstance[] {
                    getSource2(bitArray),
                    getSource(bitArray),
                    getImmediate(bitArray)
            });
        }
    };

    public static final InstructionDisassembler SOURCE_SOURCE2_OFFSET = new BaseInstructionDisassembler() {
        @Override
        public Instruction getInstruction(InstructionInformation information, BitArray bitArray,
                                DisassemblyContext context) {
            return Instruction.of(information.getOperation(), new OperandInstance[] {
                    getSource(bitArray),
                    getSource2(bitArray),
                    getOffset(bitArray, context)
            });
        }
    };

    public static final InstructionDisassembler SOURCE_OFFSET = new BaseInstructionDisassembler() {
        @Override
        public Instruction getInstruction(InstructionInformation information, BitArray bitArray,
                                DisassemblyContext context) {
            return Instruction.of(information.getOperation(), new OperandInstance[] {
                    getSource(bitArray),
                    getOffset(bitArray, context)
            });
        }
    };

    public static final InstructionDisassembler COPROCESSOR_FUNCTION = new BaseInstructionDisassembler() {
        @Override
        public Instruction getInstruction(InstructionInformation information, BitArray bitArray,
                                DisassemblyContext context) {
            return Instruction.of(information.getOperation(), new OperandInstance[] {
                    getCoprocessorFunction(bitArray)
            });
        }
    };

    public static final InstructionDisassembler SOURCE_SOURCE2 = new BaseInstructionDisassembler() {
        @Override
        public Instruction getInstruction(InstructionInformation information, BitArray bitArray,
                                DisassemblyContext context) {
            return Instruction.of(information.getOperation(), new OperandInstance[] {
                    getSource(bitArray),
                    getSource2(bitArray)
            });
        }
    };

    public static final InstructionDisassembler TARGET = new BaseInstructionDisassembler() {
        @Override
        public Instruction getInstruction(InstructionInformation information, BitArray bitArray,
                                DisassemblyContext context) {
            return Instruction.of(information.getOperation(), new OperandInstance[] {
                    getTarget(bitArray, context),
            });
        }
    };

    public static final InstructionDisassembler DESTINATION_SOURCE = new BaseInstructionDisassembler() {
        @Override
        public Instruction getInstruction(InstructionInformation information, BitArray bitArray,
                                DisassemblyContext context) {
            return Instruction.of(information.getOperation(), new OperandInstance[] {
                    getDestination(bitArray),
                    getSource(bitArray)
            });
        }
    };

    public static final InstructionDisassembler SOURCE = new BaseInstructionDisassembler() {
        @Override
        public Instruction getInstruction(InstructionInformation information, BitArray bitArray,
                                DisassemblyContext context) {
            return Instruction.of(information.getOperation(), new OperandInstance[] {
                    getSource(bitArray)
            });
        }
    };

    public static final InstructionDisassembler SOURCE2_OFFSET_BASE = new BaseInstructionDisassembler() {
        @Override
        public Instruction getInstruction(InstructionInformation information, BitArray bitArray,
                                DisassemblyContext context) {
            return Instruction.of(information.getOperation(), new OperandInstance[] {
                    getSource2(bitArray),
                    getOffsetBase(bitArray)
            });
        }
    };

    public static final InstructionDisassembler SOURCE2_IMMEDIATE = new BaseInstructionDisassembler() {
        @Override
        public Instruction getInstruction(InstructionInformation information, BitArray bitArray,
                                DisassemblyContext context) {
            return Instruction.of(information.getOperation(), new OperandInstance[] {
                    getSource2(bitArray),
                    getImmediate(bitArray)
            });
        }
    };

    public static final InstructionDisassembler SOURCE2_DESTINATION = new BaseInstructionDisassembler() {
        @Override
        protected Instruction getInstruction(InstructionInformation information, BitArray bitArray,
                                             DisassemblyContext context) {
            return Instruction.of(information.getOperation(), new OperandInstance[] {
                    getSource2(bitArray),
                    getDestination(bitArray)
            });
        }
    };

    public static final InstructionDisassembler DESTINATION = new BaseInstructionDisassembler() {
        @Override
        public Instruction getInstruction(InstructionInformation information, BitArray bitArray,
                                DisassemblyContext context) {
            return Instruction.of(information.getOperation(), new OperandInstance[] {
                    getDestination(bitArray)
            });
        }
    };

    public static final InstructionDisassembler HINT_OFFSET_BASE = new BaseInstructionDisassembler() {
        @Override
        public Instruction getInstruction(InstructionInformation information, BitArray bitArray,
                                DisassemblyContext context) {
            return Instruction.of(information.getOperation(), new OperandInstance[] {
                    getHint(bitArray),
                    getOffsetBase(bitArray)
            });
        }
    };

    public static final InstructionDisassembler DESTINATION_SOURCE2_SHIFT_AMOUNT = new BaseInstructionDisassembler() {
        @Override
        public Instruction getInstruction(InstructionInformation information, BitArray bitArray,
                                DisassemblyContext context) {
            return Instruction.of(information.getOperation(), new OperandInstance[] {
                    getDestination(bitArray),
                    getSource2(bitArray),
                    getShiftAmount(bitArray)
            });
        }
    };

    public static final InstructionDisassembler DESTINATION_SOURCE2_SOURCE = new BaseInstructionDisassembler() {
        @Override
        public Instruction getInstruction(InstructionInformation information, BitArray bitArray,
                                          DisassemblyContext context) {
            return Instruction.of(information.getOperation(), new OperandInstance[] {
                    getDestination(bitArray),
                    getSource2(bitArray),
                    getSource(bitArray)
            });
        }
    };

    // WAIT instruction is implementation-dependent.
    public static final InstructionDisassembler WAIT = EMPTY;

    private static abstract class BaseInstructionDisassembler implements InstructionDisassembler {
        @Override
        public void disassemble(InstructionInformation information, BitArray bitArray, DisassemblyContext context) {
            context.appendAssemblable(getInstruction(information, bitArray, context),
                    Constants.BYTES_PER_INSTRUCTION);
        }

        protected abstract Instruction getInstruction(InstructionInformation information, BitArray bitArray,
                                                      DisassemblyContext context);
    }
}
