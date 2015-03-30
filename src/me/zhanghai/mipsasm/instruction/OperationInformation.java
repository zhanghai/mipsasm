/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.instruction;

import java.util.EnumMap;
import java.util.Map;

public enum OperationInformation {

    ADD(Operation.ADD, OperandListPrototypes.DESTINATION_SOURCE_TARGET, InstructionCompilers.DESTINATION_SOURCE_TARGET),
    ADDI(Operation.ADDI, OperandListPrototypes.TARGET_SOURCE_IMMEDIATE, InstructionCompilers.TARGET_SOURCE_IMMEDIATE),
    ADDIU(Operation.ADDIU, OperandListPrototypes.TARGET_SOURCE_IMMEDIATE, InstructionCompilers.TARGET_SOURCE_IMMEDIATE),
    ADDU(Operation.ADDU, OperandListPrototypes.DESTINATION_SOURCE_TARGET, InstructionCompilers.DESTINATION_SOURCE_TARGET),
    AND(Operation.AND, OperandListPrototypes.DESTINATION_SOURCE_TARGET, InstructionCompilers.DESTINATION_SOURCE_TARGET),
    ANDI(Operation.ANDI, OperandListPrototypes.TARGET_SOURCE_IMMEDIATE, InstructionCompilers.TARGET_SOURCE_IMMEDIATE),
    BEQ(Operation.BEQ, OperandListPrototypes.SOURCE_TARGET_OFFSET, InstructionCompilers.SOURCE_TARGET_OFFSET),
    BEQL(Operation.BEQL, OperandListPrototypes.SOURCE_TARGET_OFFSET, InstructionCompilers.SOURCE_TARGET_OFFSET),
    BGEZ(Operation.BGEZ, OperandListPrototypes.SOURCE_OFFSET, InstructionCompilers.SOURCE_OFFSET(Register.of(0b00001))),
    BGEZAL(Operation.BGEZAL, OperandListPrototypes.SOURCE_OFFSET, InstructionCompilers.SOURCE_OFFSET(Register.of(0b10001))),
    BGEZALL(Operation.BGEZALL, OperandListPrototypes.SOURCE_OFFSET, InstructionCompilers.SOURCE_OFFSET(Register.of(0b10011))),
    BGEZL(Operation.BGEZL, OperandListPrototypes.SOURCE_OFFSET, InstructionCompilers.SOURCE_OFFSET(Register.of(0b00011))),
    BGTZ(Operation.BGTZ, OperandListPrototypes.SOURCE_OFFSET, InstructionCompilers.SOURCE_OFFSET(Register.of(0b00000))),
    BGTZL(Operation.BGTZL, OperandListPrototypes.SOURCE_OFFSET, InstructionCompilers.SOURCE_OFFSET(Register.of(0b00000))),
    BLEZ(Operation.BLEZ, OperandListPrototypes.SOURCE_OFFSET, InstructionCompilers.SOURCE_OFFSET(Register.of(0b00000))),
    BLEZL(Operation.BLEZL, OperandListPrototypes.SOURCE_OFFSET, InstructionCompilers.SOURCE_OFFSET(Register.of(0b00000))),
    BLTZ(Operation.BLTZ, OperandListPrototypes.SOURCE_OFFSET, InstructionCompilers.SOURCE_OFFSET(Register.of(0b00000))),
    BLTZAL(Operation.BLTZAL, OperandListPrototypes.SOURCE_OFFSET, InstructionCompilers.SOURCE_OFFSET(Register.of(0b10000))),
    BLTZALL(Operation.BLTZALL, OperandListPrototypes.SOURCE_OFFSET, InstructionCompilers.SOURCE_OFFSET(Register.of(0b10010))),
    BLTZL(Operation.BLTZL, OperandListPrototypes.SOURCE_OFFSET, InstructionCompilers.SOURCE_OFFSET(Register.of(0b00010))),
    BNE(Operation.BNE, OperandListPrototypes.SOURCE_TARGET_OFFSET, InstructionCompilers.SOURCE_TARGET_OFFSET),
    BNEL(Operation.BNEL, OperandListPrototypes.SOURCE_TARGET_OFFSET, InstructionCompilers.SOURCE_TARGET_OFFSET),
    COP0(Operation.COP0, OperandListPrototypes.COPROCESSOR_FUNCTION, InstructionCompilers.COPROCESSOR_FUNCTION),
    COP1(Operation.COP1, OperandListPrototypes.COPROCESSOR_FUNCTION, InstructionCompilers.COPROCESSOR_FUNCTION),
    COP2(Operation.COP2, OperandListPrototypes.COPROCESSOR_FUNCTION, InstructionCompilers.COPROCESSOR_FUNCTION),
    COP3(Operation.COP3, OperandListPrototypes.COPROCESSOR_FUNCTION, InstructionCompilers.COPROCESSOR_FUNCTION),
    DADD(Operation.DADD, OperandListPrototypes.DESTINATION_SOURCE_TARGET, InstructionCompilers.DESTINATION_SOURCE_TARGET),
    DADDI(Operation.DADDI, OperandListPrototypes.TARGET_SOURCE_IMMEDIATE, InstructionCompilers.TARGET_SOURCE_IMMEDIATE),
    DADDIU(Operation.DADDIU, OperandListPrototypes.TARGET_SOURCE_IMMEDIATE, InstructionCompilers.TARGET_SOURCE_IMMEDIATE),
    DADDU(Operation.DADDU, OperandListPrototypes.DESTINATION_SOURCE_TARGET, InstructionCompilers.DESTINATION_SOURCE_TARGET),
    DDIV(Operation.DDIV, OperandListPrototypes.SOURCE_OFFSET, InstructionCompilers.SOURCE_TARGET),
    DDIVU(Operation.DDIVU, OperandListPrototypes.SOURCE_OFFSET, InstructionCompilers.SOURCE_TARGET),
    DIV(Operation.DIV, OperandListPrototypes.SOURCE_TARGET, InstructionCompilers.SOURCE_TARGET),
    DIVU(Operation.DIVU, OperandListPrototypes.SOURCE_TARGET, InstructionCompilers.SOURCE_TARGET),
    DMUL(Operation.DMUL, OperandListPrototypes.SOURCE_TARGET, InstructionCompilers.SOURCE_TARGET),
    DMULU(Operation.DMULU, OperandListPrototypes.SOURCE_TARGET, InstructionCompilers.SOURCE_TARGET),
    DSLL(Operation.DSLL, OperandListPrototypes.DESTINATION_TARGET_SHIFT_AMOUNT, InstructionCompilers.DESTINATION_TARGET_SHIFT_AMOUNT),
    DSLL32(Operation.DSLL32, OperandListPrototypes.DESTINATION_TARGET_SHIFT_AMOUNT, InstructionCompilers.DESTINATION_TARGET_SHIFT_AMOUNT),
    DSLLV(Operation.DSLLV, OperandListPrototypes.DESTINATION_TARGET_SOURCE, InstructionCompilers.DESTINATION_TARGET_SOURCE),
    DSRA(Operation.DSRA, OperandListPrototypes.DESTINATION_TARGET_SHIFT_AMOUNT, InstructionCompilers.DESTINATION_TARGET_SHIFT_AMOUNT),
    DSRA32(Operation.DSRA32, OperandListPrototypes.DESTINATION_TARGET_SHIFT_AMOUNT, InstructionCompilers.DESTINATION_TARGET_SHIFT_AMOUNT),
    DSRAV(Operation.DSRAV, OperandListPrototypes.DESTINATION_TARGET_SOURCE, InstructionCompilers.DESTINATION_TARGET_SOURCE),
    DSRL(Operation.DSRL, OperandListPrototypes.DESTINATION_TARGET_SHIFT_AMOUNT, InstructionCompilers.DESTINATION_TARGET_SHIFT_AMOUNT),
    DSRL32(Operation.DSRL32, OperandListPrototypes.DESTINATION_TARGET_SHIFT_AMOUNT, InstructionCompilers.DESTINATION_TARGET_SHIFT_AMOUNT),
    DSRLV(Operation.DSRLV, OperandListPrototypes.DESTINATION_TARGET_SOURCE, InstructionCompilers.DESTINATION_TARGET_SOURCE),
    DSUB(Operation.DSUB, OperandListPrototypes.DESTINATION_SOURCE_TARGET, InstructionCompilers.DESTINATION_SOURCE_TARGET),
    DSUBU(Operation.DSUBU, OperandListPrototypes.DESTINATION_SOURCE_TARGET, InstructionCompilers.DESTINATION_SOURCE_TARGET),
    J(Operation.J, OperandListPrototypes.LABEL, InstructionCompilers.LABEL),
    JAL(Operation.JAL, OperandListPrototypes.LABEL, InstructionCompilers.LABEL),
    // Destination 31 can be implied, but it is not implemented here.
    JALR(Operation.JALR, OperandListPrototypes.DESTINATION_SOURCE, InstructionCompilers.DESTINATION_SOURCE),
    JR(Operation.JR, OperandListPrototypes.SOURCE, InstructionCompilers.SOURCE),
    LB(Operation.LB, OperandListPrototypes.TARGET_OFFSET_BASE, InstructionCompilers.TARGET_OFFSET_BASE),
    LBU(Operation.LBU, OperandListPrototypes.TARGET_OFFSET_BASE, InstructionCompilers.TARGET_OFFSET_BASE),
    LD(Operation.LD, OperandListPrototypes.TARGET_OFFSET_BASE, InstructionCompilers.TARGET_OFFSET_BASE),
    LDC1(Operation.LDC1, OperandListPrototypes.TARGET_OFFSET_BASE, InstructionCompilers.TARGET_OFFSET_BASE),
    LDC2(Operation.LDC2, OperandListPrototypes.TARGET_OFFSET_BASE, InstructionCompilers.TARGET_OFFSET_BASE),
    LDL(Operation.LDL, OperandListPrototypes.TARGET_OFFSET_BASE, InstructionCompilers.TARGET_OFFSET_BASE),
    LDR(Operation.LDR, OperandListPrototypes.TARGET_OFFSET_BASE, InstructionCompilers.TARGET_OFFSET_BASE),
    LH(Operation.LH, OperandListPrototypes.TARGET_OFFSET_BASE, InstructionCompilers.TARGET_OFFSET_BASE),
    LHU(Operation.LHU, OperandListPrototypes.TARGET_OFFSET_BASE, InstructionCompilers.TARGET_OFFSET_BASE),
    LL(Operation.LL, OperandListPrototypes.TARGET_OFFSET_BASE, InstructionCompilers.TARGET_OFFSET_BASE),
    LLD(Operation.LLD, OperandListPrototypes.TARGET_OFFSET_BASE, InstructionCompilers.TARGET_OFFSET_BASE),
    LUI(Operation.LUI, OperandListPrototypes.TARGET_IMMEDIATE, InstructionCompilers.TARGET_IMMEDIATE),
    LW(Operation.LW, OperandListPrototypes.TARGET_OFFSET_BASE, InstructionCompilers.TARGET_OFFSET_BASE),
    LWC1(Operation.LWC1, OperandListPrototypes.TARGET_OFFSET_BASE, InstructionCompilers.TARGET_OFFSET_BASE),
    LWC2(Operation.LWC2, OperandListPrototypes.TARGET_OFFSET_BASE, InstructionCompilers.TARGET_OFFSET_BASE),
    LWC3(Operation.LWC3, OperandListPrototypes.TARGET_OFFSET_BASE, InstructionCompilers.TARGET_OFFSET_BASE),
    LWL(Operation.LWL, OperandListPrototypes.TARGET_OFFSET_BASE, InstructionCompilers.TARGET_OFFSET_BASE),
    LWR(Operation.LWR, OperandListPrototypes.TARGET_OFFSET_BASE, InstructionCompilers.TARGET_OFFSET_BASE),
    LWU(Operation.LWU, OperandListPrototypes.TARGET_OFFSET_BASE, InstructionCompilers.TARGET_OFFSET_BASE),
    MFHI(Operation.MFHI, OperandListPrototypes.DESTINATION, InstructionCompilers.DESTINATION),
    MFLO(Operation.MFLO, OperandListPrototypes.DESTINATION, InstructionCompilers.DESTINATION),
    MOVN(Operation.MOVN, OperandListPrototypes.DESTINATION_SOURCE_TARGET, InstructionCompilers.DESTINATION_SOURCE_TARGET),
    MOVZ(Operation.MOVZ, OperandListPrototypes.DESTINATION_SOURCE_TARGET, InstructionCompilers.DESTINATION_SOURCE_TARGET),
    MTHI(Operation.MTHI, OperandListPrototypes.SOURCE, InstructionCompilers.SOURCE),
    MTLO(Operation.MTLO, OperandListPrototypes.SOURCE, InstructionCompilers.SOURCE),
    MULT(Operation.MULT, OperandListPrototypes.SOURCE_TARGET, InstructionCompilers.SOURCE_TARGET),
    MULTU(Operation.MULTU, OperandListPrototypes.SOURCE_TARGET, InstructionCompilers.SOURCE_TARGET),
    NOR(Operation.NOR, OperandListPrototypes.DESTINATION_SOURCE_TARGET, InstructionCompilers.DESTINATION_SOURCE_TARGET),
    //TODO: NOOP,
    OR(Operation.OR, OperandListPrototypes.DESTINATION_SOURCE_TARGET, InstructionCompilers.DESTINATION_SOURCE_TARGET),
    ORI(Operation.ORI, OperandListPrototypes.TARGET_SOURCE_IMMEDIATE, InstructionCompilers.TARGET_SOURCE_IMMEDIATE),
    PREF(Operation.PREF, OperandListPrototypes.HINT_OFFSET_BASE, InstructionCompilers.HINT_OFFSET_BASE),
    SB(Operation.SB, OperandListPrototypes.TARGET_OFFSET_BASE, InstructionCompilers.TARGET_OFFSET_BASE),
    SC(Operation.SC, OperandListPrototypes.TARGET_OFFSET_BASE, InstructionCompilers.TARGET_OFFSET_BASE),
    SCD(Operation.SCD, OperandListPrototypes.TARGET_OFFSET_BASE, InstructionCompilers.TARGET_OFFSET_BASE),
    SD(Operation.SD, OperandListPrototypes.TARGET_OFFSET_BASE, InstructionCompilers.TARGET_OFFSET_BASE),
    SDC1(Operation.SDC1, OperandListPrototypes.TARGET_OFFSET_BASE, InstructionCompilers.TARGET_OFFSET_BASE),
    SDC2(Operation.SDC2, OperandListPrototypes.TARGET_OFFSET_BASE, InstructionCompilers.TARGET_OFFSET_BASE),
    SDL(Operation.SDL, OperandListPrototypes.TARGET_OFFSET_BASE, InstructionCompilers.TARGET_OFFSET_BASE),
    SDR(Operation.SDR, OperandListPrototypes.TARGET_OFFSET_BASE, InstructionCompilers.TARGET_OFFSET_BASE),
    SH(Operation.SH, OperandListPrototypes.TARGET_OFFSET_BASE, InstructionCompilers.TARGET_OFFSET_BASE),
    SLL(Operation.SLL, OperandListPrototypes.DESTINATION_TARGET_SHIFT_AMOUNT, InstructionCompilers.DESTINATION_TARGET_SHIFT_AMOUNT),
    SLLV(Operation.SLLV, OperandListPrototypes.DESTINATION_TARGET_SOURCE, InstructionCompilers.DESTINATION_TARGET_SOURCE),
    SLT(Operation.SLT, OperandListPrototypes.DESTINATION_SOURCE_TARGET, InstructionCompilers.DESTINATION_SOURCE_TARGET),
    SLTI(Operation.SLTI, OperandListPrototypes.TARGET_SOURCE_IMMEDIATE, InstructionCompilers.TARGET_SOURCE_IMMEDIATE),
    SLTIU(Operation.SLTIU, OperandListPrototypes.TARGET_SOURCE_IMMEDIATE, InstructionCompilers.TARGET_SOURCE_IMMEDIATE),
    SLTU(Operation.SLTU, OperandListPrototypes.DESTINATION_SOURCE_TARGET, InstructionCompilers.DESTINATION_SOURCE_TARGET),
    SRA(Operation.SRA, OperandListPrototypes.DESTINATION_TARGET_SHIFT_AMOUNT, InstructionCompilers.DESTINATION_TARGET_SHIFT_AMOUNT),
    SRAV(Operation.SRAV, OperandListPrototypes.DESTINATION_TARGET_SOURCE, InstructionCompilers.DESTINATION_TARGET_SOURCE),
    SRL(Operation.SRL, OperandListPrototypes.DESTINATION_TARGET_SHIFT_AMOUNT, InstructionCompilers.DESTINATION_TARGET_SHIFT_AMOUNT),
    SRLV(Operation.SRLV, OperandListPrototypes.DESTINATION_TARGET_SOURCE, InstructionCompilers.DESTINATION_TARGET_SOURCE),
    SUB(Operation.SUB, OperandListPrototypes.DESTINATION_SOURCE_TARGET, InstructionCompilers.DESTINATION_SOURCE_TARGET),
    SUBU(Operation.SUBU, OperandListPrototypes.DESTINATION_SOURCE_TARGET, InstructionCompilers.DESTINATION_SOURCE_TARGET),
    SW(Operation.SW, OperandListPrototypes.TARGET_OFFSET_BASE, InstructionCompilers.TARGET_OFFSET_BASE),
    SWC1(Operation.SWC1, OperandListPrototypes.TARGET_OFFSET_BASE, InstructionCompilers.TARGET_OFFSET_BASE),
    SWC2(Operation.SWC2, OperandListPrototypes.TARGET_OFFSET_BASE, InstructionCompilers.TARGET_OFFSET_BASE),
    SWC3(Operation.SWC3, OperandListPrototypes.TARGET_OFFSET_BASE, InstructionCompilers.TARGET_OFFSET_BASE),
    SWL(Operation.SWL, OperandListPrototypes.TARGET_OFFSET_BASE, InstructionCompilers.TARGET_OFFSET_BASE),
    SWR(Operation.SWR, OperandListPrototypes.TARGET_OFFSET_BASE, InstructionCompilers.TARGET_OFFSET_BASE),
    // TODO: SYSCALL,
    XOR(Operation.XOR, OperandListPrototypes.DESTINATION_SOURCE_TARGET, InstructionCompilers.DESTINATION_SOURCE_TARGET),
    XORI(Operation.XORI, OperandListPrototypes.TARGET_SOURCE_IMMEDIATE, InstructionCompilers.TARGET_SOURCE_IMMEDIATE);

    private static final Map<Operation, OperationInformation> OPERATION_MAP = new EnumMap<>(Operation.class);
    static {
        for (OperationInformation operationInformation : values()) {
            OPERATION_MAP.put(operationInformation.getOperation(), operationInformation);
        }
    }

    private Operation operation;
    private OperandPrototype[] operandListPrototype;
    private InstructionCompiler instructionCompiler;

    OperationInformation(Operation operation, OperandPrototype[] operandListPrototype,
                         InstructionCompiler instructionCompiler) {
        this.operation = operation;
        this.operandListPrototype = operandListPrototype;
        this.instructionCompiler = instructionCompiler;
    }

    public static OperationInformation ofOperation(Operation operation) {
        OperationInformation operationInformation = OPERATION_MAP.get(operation);
        if (operationInformation == null) {
            throw new IllegalArgumentException("Operation not found: " + operation);
        }
        return operationInformation;
    }

    public Operation getOperation() {
        return operation;
    }

    public OperandPrototype[] getOperandListPrototype() {
        return operandListPrototype;
    }

    public InstructionCompiler getInstructionCompiler() {
        return instructionCompiler;
    }
}
