/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.assembler;

import me.zhanghai.mipsasm.InternalException;
import me.zhanghai.mipsasm.disassembler.InstructionDisassembler;
import me.zhanghai.mipsasm.disassembler.InstructionDisassemblers;

import java.util.EnumMap;
import java.util.Map;

public enum InstructionInformation {

    ADD(Operation.ADD, OperandListPrototypes.DESTINATION_SOURCE_SOURCE2,
            InstructionAssemblers.DESTINATION_SOURCE_SOURCE2, InstructionDisassemblers.DESTINATION_SOURCE_SOURCE2),
    ADDI(Operation.ADDI, OperandListPrototypes.SOURCE2_SOURCE_IMMEDIATE, InstructionAssemblers.SOURCE2_SOURCE_IMMEDIATE,
            InstructionDisassemblers.SOURCE2_SOURCE_IMMEDIATE),
    ADDIU(Operation.ADDIU, OperandListPrototypes.SOURCE2_SOURCE_IMMEDIATE,
            InstructionAssemblers.SOURCE2_SOURCE_IMMEDIATE, InstructionDisassemblers.SOURCE2_SOURCE_IMMEDIATE),
    ADDU(Operation.ADDU, OperandListPrototypes.DESTINATION_SOURCE_SOURCE2,
            InstructionAssemblers.DESTINATION_SOURCE_SOURCE2, InstructionDisassemblers.DESTINATION_SOURCE_SOURCE2),
    AND(Operation.AND, OperandListPrototypes.DESTINATION_SOURCE_SOURCE2,
            InstructionAssemblers.DESTINATION_SOURCE_SOURCE2, InstructionDisassemblers.DESTINATION_SOURCE_SOURCE2),
    ANDI(Operation.ANDI, OperandListPrototypes.SOURCE2_SOURCE_IMMEDIATE, InstructionAssemblers.SOURCE2_SOURCE_IMMEDIATE,
            InstructionDisassemblers.SOURCE2_SOURCE_IMMEDIATE),
    B(Operation.B, OperandListPrototypes.OFFSET, InstructionAssemblers.B, null),
    BEQ(Operation.BEQ, OperandListPrototypes.SOURCE_SOURCE2_OFFSET,
            InstructionAssemblers.SOURCE_SOURCE2_OFFSET_DELAY_SLOT, InstructionDisassemblers.SOURCE_SOURCE2_OFFSET),
    BEQL(Operation.BEQL, OperandListPrototypes.SOURCE_SOURCE2_OFFSET,
            InstructionAssemblers.SOURCE_SOURCE2_OFFSET_DELAY_SLOT, InstructionDisassemblers.SOURCE_SOURCE2_OFFSET),
    BGEZ(Operation.BGEZ, OperandListPrototypes.SOURCE_OFFSET, InstructionAssemblers.SOURCE_OFFSET_DELAY_SLOT,
            InstructionDisassemblers.SOURCE_OFFSET),
    BGEZAL(Operation.BGEZAL, OperandListPrototypes.SOURCE_OFFSET, InstructionAssemblers.SOURCE_OFFSET_DELAY_SLOT,
            InstructionDisassemblers.SOURCE_OFFSET),
    BGEZALL(Operation.BGEZALL, OperandListPrototypes.SOURCE_OFFSET, InstructionAssemblers.SOURCE_OFFSET_DELAY_SLOT,
            InstructionDisassemblers.SOURCE_OFFSET),
    BGEZL(Operation.BGEZL, OperandListPrototypes.SOURCE_OFFSET, InstructionAssemblers.SOURCE_OFFSET_DELAY_SLOT,
            InstructionDisassemblers.SOURCE_OFFSET),
    BGTZ(Operation.BGTZ, OperandListPrototypes.SOURCE_OFFSET, InstructionAssemblers.SOURCE_OFFSET_DELAY_SLOT,
            InstructionDisassemblers.SOURCE_OFFSET),
    BGTZL(Operation.BGTZL, OperandListPrototypes.SOURCE_OFFSET, InstructionAssemblers.SOURCE_OFFSET_DELAY_SLOT,
            InstructionDisassemblers.SOURCE_OFFSET),
    BLEZ(Operation.BLEZ, OperandListPrototypes.SOURCE_OFFSET, InstructionAssemblers.SOURCE_OFFSET_DELAY_SLOT,
            InstructionDisassemblers.SOURCE_OFFSET),
    BLEZL(Operation.BLEZL, OperandListPrototypes.SOURCE_OFFSET, InstructionAssemblers.SOURCE_OFFSET_DELAY_SLOT,
            InstructionDisassemblers.SOURCE_OFFSET),
    BLTZ(Operation.BLTZ, OperandListPrototypes.SOURCE_OFFSET, InstructionAssemblers.SOURCE_OFFSET_DELAY_SLOT,
            InstructionDisassemblers.SOURCE_OFFSET),
    BLTZAL(Operation.BLTZAL, OperandListPrototypes.SOURCE_OFFSET, InstructionAssemblers.SOURCE_OFFSET_DELAY_SLOT,
            InstructionDisassemblers.SOURCE_OFFSET),
    BLTZALL(Operation.BLTZALL, OperandListPrototypes.SOURCE_OFFSET, InstructionAssemblers.SOURCE_OFFSET_DELAY_SLOT,
            InstructionDisassemblers.SOURCE_OFFSET),
    BLTZL(Operation.BLTZL, OperandListPrototypes.SOURCE_OFFSET, InstructionAssemblers.SOURCE_OFFSET_DELAY_SLOT,
            InstructionDisassemblers.SOURCE_OFFSET),
    BNE(Operation.BNE, OperandListPrototypes.SOURCE_SOURCE2_OFFSET,
            InstructionAssemblers.SOURCE_SOURCE2_OFFSET_DELAY_SLOT, InstructionDisassemblers.SOURCE_SOURCE2_OFFSET),
    BNEL(Operation.BNEL, OperandListPrototypes.SOURCE_SOURCE2_OFFSET,
            InstructionAssemblers.SOURCE_SOURCE2_OFFSET_DELAY_SLOT, InstructionDisassemblers.SOURCE_SOURCE2_OFFSET),
    BREAK(Operation.BREAK, OperandListPrototypes.EMPTY, InstructionAssemblers.CODE, InstructionDisassemblers.EMPTY),
    COP2(Operation.COP2, OperandListPrototypes.COPROCESSOR_FUNCTION, InstructionAssemblers.COPROCESSOR_FUNCTION,
            InstructionDisassemblers.COPROCESSOR_FUNCTION),
    DIV(Operation.DIV, OperandListPrototypes.SOURCE_SOURCE2, InstructionAssemblers.SOURCE_SOURCE2,
            InstructionDisassemblers.SOURCE_SOURCE2),
    DIVU(Operation.DIVU, OperandListPrototypes.SOURCE_SOURCE2, InstructionAssemblers.SOURCE_SOURCE2,
            InstructionDisassemblers.SOURCE_SOURCE2),
    DERET(Operation.DERET, OperandListPrototypes.EMPTY, InstructionAssemblers.CO0, InstructionDisassemblers.EMPTY),
    ERET(Operation.ERET, OperandListPrototypes.EMPTY, InstructionAssemblers.CO0, InstructionDisassemblers.EMPTY),
    J(Operation.J, OperandListPrototypes.TARGET, InstructionAssemblers.TARGET_DELAY_SLOT,
            InstructionDisassemblers.TARGET),
    JAL(Operation.JAL, OperandListPrototypes.TARGET, InstructionAssemblers.TARGET_DELAY_SLOT,
            InstructionDisassemblers.TARGET),
    // Destination 31 can be implied, but it is not implemented here.
    JALR(Operation.JALR, OperandListPrototypes.DESTINATION_SOURCE, InstructionAssemblers.DESTINATION_SOURCE_DELAY_SLOT,
            InstructionDisassemblers.DESTINATION_SOURCE),
    JR(Operation.JR, OperandListPrototypes.SOURCE, InstructionAssemblers.SOURCE_DELAY_SLOT,
            InstructionDisassemblers.SOURCE),
    LA(Operation.LA, OperandListPrototypes.SOURCE2_LABEL, InstructionAssemblers.LA, null),
    LB(Operation.LB, OperandListPrototypes.SOURCE2_OFFSET_BASE, InstructionAssemblers.SOURCE2_OFFSET_BASE,
            InstructionDisassemblers.SOURCE2_OFFSET_BASE),
    LBU(Operation.LBU, OperandListPrototypes.SOURCE2_OFFSET_BASE, InstructionAssemblers.SOURCE2_OFFSET_BASE,
            InstructionDisassemblers.SOURCE2_OFFSET_BASE),
    LDC1(Operation.LDC1, OperandListPrototypes.SOURCE2_OFFSET_BASE, InstructionAssemblers.SOURCE2_OFFSET_BASE,
            InstructionDisassemblers.SOURCE2_OFFSET_BASE),
    LDC2(Operation.LDC2, OperandListPrototypes.SOURCE2_OFFSET_BASE, InstructionAssemblers.SOURCE2_OFFSET_BASE,
            InstructionDisassemblers.SOURCE2_OFFSET_BASE),
    LH(Operation.LH, OperandListPrototypes.SOURCE2_OFFSET_BASE, InstructionAssemblers.SOURCE2_OFFSET_BASE,
            InstructionDisassemblers.SOURCE2_OFFSET_BASE),
    LHU(Operation.LHU, OperandListPrototypes.SOURCE2_OFFSET_BASE, InstructionAssemblers.SOURCE2_OFFSET_BASE,
            InstructionDisassemblers.SOURCE2_OFFSET_BASE),
    LI(Operation.LI, OperandListPrototypes.SOURCE2_WORD_IMMEDIATE, InstructionAssemblers.LI, null),
    LL(Operation.LL, OperandListPrototypes.SOURCE2_OFFSET_BASE, InstructionAssemblers.SOURCE2_OFFSET_BASE,
            InstructionDisassemblers.SOURCE2_OFFSET_BASE),
    LUI(Operation.LUI, OperandListPrototypes.SOURCE2_IMMEDIATE, InstructionAssemblers.SOURCE2_IMMEDIATE,
            InstructionDisassemblers.SOURCE2_IMMEDIATE),
    LW(Operation.LW, OperandListPrototypes.SOURCE2_OFFSET_BASE, InstructionAssemblers.SOURCE2_OFFSET_BASE,
            InstructionDisassemblers.SOURCE2_OFFSET_BASE),
    LWC1(Operation.LWC1, OperandListPrototypes.SOURCE2_OFFSET_BASE, InstructionAssemblers.SOURCE2_OFFSET_BASE,
            InstructionDisassemblers.SOURCE2_OFFSET_BASE),
    LWC2(Operation.LWC2, OperandListPrototypes.SOURCE2_OFFSET_BASE, InstructionAssemblers.SOURCE2_OFFSET_BASE,
            InstructionDisassemblers.SOURCE2_OFFSET_BASE),
    LWL(Operation.LWL, OperandListPrototypes.SOURCE2_OFFSET_BASE, InstructionAssemblers.SOURCE2_OFFSET_BASE,
            InstructionDisassemblers.SOURCE2_OFFSET_BASE),
    LWR(Operation.LWR, OperandListPrototypes.SOURCE2_OFFSET_BASE, InstructionAssemblers.SOURCE2_OFFSET_BASE,
            InstructionDisassemblers.SOURCE2_OFFSET_BASE),
    MFC0(Operation.MFC0, OperandListPrototypes.SOURCE2_DESTINATION, InstructionAssemblers.SOURCE2_DESTINATION,
            InstructionDisassemblers.SOURCE2_DESTINATION),
    MFHI(Operation.MFHI, OperandListPrototypes.DESTINATION, InstructionAssemblers.DESTINATION,
            InstructionDisassemblers.DESTINATION),
    MFLO(Operation.MFLO, OperandListPrototypes.DESTINATION, InstructionAssemblers.DESTINATION,
            InstructionDisassemblers.DESTINATION),
    MOVE(Operation.MOVE, OperandListPrototypes.DESTINATION_SOURCE, InstructionAssemblers.MOVE, null),
    MOVN(Operation.MOVN, OperandListPrototypes.DESTINATION_SOURCE_SOURCE2,
            InstructionAssemblers.DESTINATION_SOURCE_SOURCE2, InstructionDisassemblers.DESTINATION_SOURCE_SOURCE2),
    MOVZ(Operation.MOVZ, OperandListPrototypes.DESTINATION_SOURCE_SOURCE2,
            InstructionAssemblers.DESTINATION_SOURCE_SOURCE2, InstructionDisassemblers.DESTINATION_SOURCE_SOURCE2),
    MTC0(Operation.MTC0, OperandListPrototypes.SOURCE2_DESTINATION, InstructionAssemblers.SOURCE2_DESTINATION,
            InstructionDisassemblers.SOURCE2_DESTINATION),
    MTHI(Operation.MTHI, OperandListPrototypes.SOURCE, InstructionAssemblers.SOURCE, InstructionDisassemblers.SOURCE),
    MTLO(Operation.MTLO, OperandListPrototypes.SOURCE, InstructionAssemblers.SOURCE, InstructionDisassemblers.SOURCE),
    MULT(Operation.MULT, OperandListPrototypes.SOURCE_SOURCE2, InstructionAssemblers.SOURCE_SOURCE2,
            InstructionDisassemblers.SOURCE_SOURCE2),
    MULTU(Operation.MULTU, OperandListPrototypes.SOURCE_SOURCE2, InstructionAssemblers.SOURCE_SOURCE2,
            InstructionDisassemblers.SOURCE_SOURCE2),
    NOP(Operation.NOP, OperandListPrototypes.EMPTY, InstructionAssemblers.NOP, null),
    NOR(Operation.NOR, OperandListPrototypes.DESTINATION_SOURCE_SOURCE2,
            InstructionAssemblers.DESTINATION_SOURCE_SOURCE2, InstructionDisassemblers.DESTINATION_SOURCE_SOURCE2),
    OR(Operation.OR, OperandListPrototypes.DESTINATION_SOURCE_SOURCE2, InstructionAssemblers.DESTINATION_SOURCE_SOURCE2,
            InstructionDisassemblers.DESTINATION_SOURCE_SOURCE2),
    ORI(Operation.ORI, OperandListPrototypes.SOURCE2_SOURCE_IMMEDIATE, InstructionAssemblers.SOURCE2_SOURCE_IMMEDIATE,
            InstructionDisassemblers.SOURCE2_SOURCE_IMMEDIATE),
    PREF(Operation.PREF, OperandListPrototypes.HINT_OFFSET_BASE, InstructionAssemblers.HINT_OFFSET_BASE,
            InstructionDisassemblers.HINT_OFFSET_BASE),
    SB(Operation.SB, OperandListPrototypes.SOURCE2_OFFSET_BASE, InstructionAssemblers.SOURCE2_OFFSET_BASE,
            InstructionDisassemblers.SOURCE2_OFFSET_BASE),
    SC(Operation.SC, OperandListPrototypes.SOURCE2_OFFSET_BASE, InstructionAssemblers.SOURCE2_OFFSET_BASE,
            InstructionDisassemblers.SOURCE2_OFFSET_BASE),
    SDC1(Operation.SDC1, OperandListPrototypes.SOURCE2_OFFSET_BASE, InstructionAssemblers.SOURCE2_OFFSET_BASE,
            InstructionDisassemblers.SOURCE2_OFFSET_BASE),
    SDC2(Operation.SDC2, OperandListPrototypes.SOURCE2_OFFSET_BASE, InstructionAssemblers.SOURCE2_OFFSET_BASE,
            InstructionDisassemblers.SOURCE2_OFFSET_BASE),
    SH(Operation.SH, OperandListPrototypes.SOURCE2_OFFSET_BASE, InstructionAssemblers.SOURCE2_OFFSET_BASE,
            InstructionDisassemblers.SOURCE2_OFFSET_BASE),
    SLL(Operation.SLL, OperandListPrototypes.DESTINATION_SOURCE2_SHIFT_AMOUNT,
            InstructionAssemblers.DESTINATION_SOURCE2_SHIFT_AMOUNT,
            InstructionDisassemblers.DESTINATION_SOURCE2_SHIFT_AMOUNT),
    SLLV(Operation.SLLV, OperandListPrototypes.DESTINATION_SOURCE2_SOURCE,
            InstructionAssemblers.DESTINATION_SOURCE2_SOURCE, InstructionDisassemblers.DESTINATION_SOURCE2_SOURCE),
    SLT(Operation.SLT, OperandListPrototypes.DESTINATION_SOURCE_SOURCE2,
            InstructionAssemblers.DESTINATION_SOURCE_SOURCE2, InstructionDisassemblers.DESTINATION_SOURCE_SOURCE2),
    SLTI(Operation.SLTI, OperandListPrototypes.SOURCE2_SOURCE_IMMEDIATE, InstructionAssemblers.SOURCE2_SOURCE_IMMEDIATE,
            InstructionDisassemblers.SOURCE2_SOURCE_IMMEDIATE),
    SLTIU(Operation.SLTIU, OperandListPrototypes.SOURCE2_SOURCE_IMMEDIATE,
            InstructionAssemblers.SOURCE2_SOURCE_IMMEDIATE, InstructionDisassemblers.SOURCE2_SOURCE_IMMEDIATE),
    SLTU(Operation.SLTU, OperandListPrototypes.DESTINATION_SOURCE_SOURCE2,
            InstructionAssemblers.DESTINATION_SOURCE_SOURCE2, InstructionDisassemblers.DESTINATION_SOURCE_SOURCE2),
    SRA(Operation.SRA, OperandListPrototypes.DESTINATION_SOURCE2_SHIFT_AMOUNT,
            InstructionAssemblers.DESTINATION_SOURCE2_SHIFT_AMOUNT,
            InstructionDisassemblers.DESTINATION_SOURCE2_SHIFT_AMOUNT),
    SRAV(Operation.SRAV, OperandListPrototypes.DESTINATION_SOURCE2_SOURCE,
            InstructionAssemblers.DESTINATION_SOURCE2_SOURCE, InstructionDisassemblers.DESTINATION_SOURCE2_SOURCE),
    SRL(Operation.SRL, OperandListPrototypes.DESTINATION_SOURCE2_SHIFT_AMOUNT,
            InstructionAssemblers.DESTINATION_SOURCE2_SHIFT_AMOUNT,
            InstructionDisassemblers.DESTINATION_SOURCE2_SHIFT_AMOUNT),
    SRLV(Operation.SRLV, OperandListPrototypes.DESTINATION_SOURCE2_SOURCE,
            InstructionAssemblers.DESTINATION_SOURCE2_SOURCE, InstructionDisassemblers.DESTINATION_SOURCE2_SOURCE),
    SUB(Operation.SUB, OperandListPrototypes.DESTINATION_SOURCE_SOURCE2,
            InstructionAssemblers.DESTINATION_SOURCE_SOURCE2, InstructionDisassemblers.DESTINATION_SOURCE_SOURCE2),
    SUBU(Operation.SUBU, OperandListPrototypes.DESTINATION_SOURCE_SOURCE2,
            InstructionAssemblers.DESTINATION_SOURCE_SOURCE2, InstructionDisassemblers.DESTINATION_SOURCE_SOURCE2),
    SW(Operation.SW, OperandListPrototypes.SOURCE2_OFFSET_BASE, InstructionAssemblers.SOURCE2_OFFSET_BASE,
            InstructionDisassemblers.SOURCE2_OFFSET_BASE),
    SWC1(Operation.SWC1, OperandListPrototypes.SOURCE2_OFFSET_BASE, InstructionAssemblers.SOURCE2_OFFSET_BASE,
            InstructionDisassemblers.SOURCE2_OFFSET_BASE),
    SWC2(Operation.SWC2, OperandListPrototypes.SOURCE2_OFFSET_BASE, InstructionAssemblers.SOURCE2_OFFSET_BASE,
            InstructionDisassemblers.SOURCE2_OFFSET_BASE),
    SWC3(Operation.SWC3, OperandListPrototypes.SOURCE2_OFFSET_BASE, InstructionAssemblers.SOURCE2_OFFSET_BASE,
            InstructionDisassemblers.SOURCE2_OFFSET_BASE),
    SWL(Operation.SWL, OperandListPrototypes.SOURCE2_OFFSET_BASE, InstructionAssemblers.SOURCE2_OFFSET_BASE,
            InstructionDisassemblers.SOURCE2_OFFSET_BASE),
    SWR(Operation.SWR, OperandListPrototypes.SOURCE2_OFFSET_BASE, InstructionAssemblers.SOURCE2_OFFSET_BASE,
            InstructionDisassemblers.SOURCE2_OFFSET_BASE),
    SYSCALL(Operation.SYSCALL, OperandListPrototypes.EMPTY, InstructionAssemblers.CODE, InstructionDisassemblers.EMPTY),
    TLBP(Operation.TLBP, OperandListPrototypes.EMPTY, InstructionAssemblers.CO0, InstructionDisassemblers.EMPTY),
    TLBR(Operation.TLBR, OperandListPrototypes.EMPTY, InstructionAssemblers.CO0, InstructionDisassemblers.EMPTY),
    TLBWI(Operation.TLBWI, OperandListPrototypes.EMPTY, InstructionAssemblers.CO0, InstructionDisassemblers.EMPTY),
    TLBWR(Operation.TLBWR, OperandListPrototypes.EMPTY, InstructionAssemblers.CO0, InstructionDisassemblers.EMPTY),
    WAIT(Operation.WAIT, OperandListPrototypes.EMPTY, InstructionAssemblers.WAIT, InstructionDisassemblers.WAIT),
    XOR(Operation.XOR, OperandListPrototypes.DESTINATION_SOURCE_SOURCE2,
            InstructionAssemblers.DESTINATION_SOURCE_SOURCE2, InstructionDisassemblers.DESTINATION_SOURCE_SOURCE2),
    XORI(Operation.XORI, OperandListPrototypes.SOURCE2_SOURCE_IMMEDIATE, InstructionAssemblers.SOURCE2_SOURCE_IMMEDIATE,
            InstructionDisassemblers.SOURCE2_SOURCE_IMMEDIATE);

    private static final Map<Operation, InstructionInformation> OPERATION_MAP = new EnumMap<>(Operation.class);
    static {
        for (InstructionInformation instructionInformation : values()) {
            OPERATION_MAP.put(instructionInformation.getOperation(), instructionInformation);
        }
    }

    private Operation operation;
    private OperandPrototype[] operandListPrototype;
    private InstructionAssembler instructionAssembler;
    private InstructionDisassembler instructionDisassembler;

    InstructionInformation(Operation operation, OperandPrototype[] operandListPrototype,
                           InstructionAssembler instructionAssembler, InstructionDisassembler instructionDisassembler) {
        if (!name().equals(operation.name())) {
            throw new IllegalArgumentException(
                    "InstructionInformation and Operation name mismatch, please check the corresponding code, name: "
                            + name() + ", operation: " + operation.name());
        }
        this.operation = operation;
        this.operandListPrototype = operandListPrototype;
        this.instructionAssembler = instructionAssembler;
        this.instructionDisassembler = instructionDisassembler;
    }

    public static InstructionInformation ofOperation(Operation operation) {
        InstructionInformation instructionInformation = OPERATION_MAP.get(operation);
        if (instructionInformation == null) {
            throw new IllegalArgumentException("Operation not found: " + operation);
        }
        return instructionInformation;
    }

    public Operation getOperation() {
        return operation;
    }

    public OperandPrototype[] getOperandListPrototype() {
        return operandListPrototype;
    }

    public InstructionAssembler getAssembler() {
        return instructionAssembler;
    }

    public InstructionDisassembler getDisassembler() {
        if (instructionDisassembler == null) {
            throw new InternalException(new IllegalStateException(
                    "getDisassembler() called on an Operation without an instruction disassembler: "
                            + operation));
        }
        return instructionDisassembler;
    }
}
