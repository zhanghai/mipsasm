/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.assembler;

import java.util.EnumMap;
import java.util.Map;

public enum OperationInformation {

    ADD(Operation.ADD, OperandListPrototypes.DESTINATION_SOURCE_SOURCE2, InstructionAssemblers.DESTINATION_SOURCE_SOURCE2),
    ADDI(Operation.ADDI, OperandListPrototypes.SOURCE2_SOURCE_IMMEDIATE, InstructionAssemblers.SOURCE2_SOURCE_IMMEDIATE),
    ADDIU(Operation.ADDIU, OperandListPrototypes.SOURCE2_SOURCE_IMMEDIATE, InstructionAssemblers.SOURCE2_SOURCE_IMMEDIATE),
    ADDU(Operation.ADDU, OperandListPrototypes.DESTINATION_SOURCE_SOURCE2, InstructionAssemblers.DESTINATION_SOURCE_SOURCE2),
    AND(Operation.AND, OperandListPrototypes.DESTINATION_SOURCE_SOURCE2, InstructionAssemblers.DESTINATION_SOURCE_SOURCE2),
    ANDI(Operation.ANDI, OperandListPrototypes.SOURCE2_SOURCE_IMMEDIATE, InstructionAssemblers.SOURCE2_SOURCE_IMMEDIATE),
    BEQ(Operation.BEQ, OperandListPrototypes.SOURCE_SOURCE2_OFFSET, InstructionAssemblers.SOURCE_SOURCE2_OFFSET),
    BEQL(Operation.BEQL, OperandListPrototypes.SOURCE_SOURCE2_OFFSET, InstructionAssemblers.SOURCE_SOURCE2_OFFSET),
    BGEZ(Operation.BGEZ, OperandListPrototypes.SOURCE_OFFSET, InstructionAssemblers.SOURCE_OFFSET(Register.of(0b00001))),
    BGEZAL(Operation.BGEZAL, OperandListPrototypes.SOURCE_OFFSET, InstructionAssemblers.SOURCE_OFFSET(Register.of(0b10001))),
    BGEZALL(Operation.BGEZALL, OperandListPrototypes.SOURCE_OFFSET, InstructionAssemblers.SOURCE_OFFSET(Register.of(0b10011))),
    BGEZL(Operation.BGEZL, OperandListPrototypes.SOURCE_OFFSET, InstructionAssemblers.SOURCE_OFFSET(Register.of(0b00011))),
    BGTZ(Operation.BGTZ, OperandListPrototypes.SOURCE_OFFSET, InstructionAssemblers.SOURCE_OFFSET(Register.of(0b00000))),
    BGTZL(Operation.BGTZL, OperandListPrototypes.SOURCE_OFFSET, InstructionAssemblers.SOURCE_OFFSET(Register.of(0b00000))),
    BLEZ(Operation.BLEZ, OperandListPrototypes.SOURCE_OFFSET, InstructionAssemblers.SOURCE_OFFSET(Register.of(0b00000))),
    BLEZL(Operation.BLEZL, OperandListPrototypes.SOURCE_OFFSET, InstructionAssemblers.SOURCE_OFFSET(Register.of(0b00000))),
    BLTZ(Operation.BLTZ, OperandListPrototypes.SOURCE_OFFSET, InstructionAssemblers.SOURCE_OFFSET(Register.of(0b00000))),
    BLTZAL(Operation.BLTZAL, OperandListPrototypes.SOURCE_OFFSET, InstructionAssemblers.SOURCE_OFFSET(Register.of(0b10000))),
    BLTZALL(Operation.BLTZALL, OperandListPrototypes.SOURCE_OFFSET, InstructionAssemblers.SOURCE_OFFSET(Register.of(0b10010))),
    BLTZL(Operation.BLTZL, OperandListPrototypes.SOURCE_OFFSET, InstructionAssemblers.SOURCE_OFFSET(Register.of(0b00010))),
    BNE(Operation.BNE, OperandListPrototypes.SOURCE_SOURCE2_OFFSET, InstructionAssemblers.SOURCE_SOURCE2_OFFSET),
    BNEL(Operation.BNEL, OperandListPrototypes.SOURCE_SOURCE2_OFFSET, InstructionAssemblers.SOURCE_SOURCE2_OFFSET),
    COP0(Operation.COP0, OperandListPrototypes.COPROCESSOR_FUNCTION, InstructionAssemblers.COPROCESSOR_FUNCTION),
    COP1(Operation.COP1, OperandListPrototypes.COPROCESSOR_FUNCTION, InstructionAssemblers.COPROCESSOR_FUNCTION),
    COP2(Operation.COP2, OperandListPrototypes.COPROCESSOR_FUNCTION, InstructionAssemblers.COPROCESSOR_FUNCTION),
    COP3(Operation.COP3, OperandListPrototypes.COPROCESSOR_FUNCTION, InstructionAssemblers.COPROCESSOR_FUNCTION),
    DADD(Operation.DADD, OperandListPrototypes.DESTINATION_SOURCE_SOURCE2, InstructionAssemblers.DESTINATION_SOURCE_SOURCE2),
    DADDI(Operation.DADDI, OperandListPrototypes.SOURCE2_SOURCE_IMMEDIATE, InstructionAssemblers.SOURCE2_SOURCE_IMMEDIATE),
    DADDIU(Operation.DADDIU, OperandListPrototypes.SOURCE2_SOURCE_IMMEDIATE, InstructionAssemblers.SOURCE2_SOURCE_IMMEDIATE),
    DADDU(Operation.DADDU, OperandListPrototypes.DESTINATION_SOURCE_SOURCE2, InstructionAssemblers.DESTINATION_SOURCE_SOURCE2),
    DDIV(Operation.DDIV, OperandListPrototypes.SOURCE_OFFSET, InstructionAssemblers.SOURCE_SOURCE2),
    DDIVU(Operation.DDIVU, OperandListPrototypes.SOURCE_OFFSET, InstructionAssemblers.SOURCE_SOURCE2),
    DIV(Operation.DIV, OperandListPrototypes.SOURCE_SOURCE2, InstructionAssemblers.SOURCE_SOURCE2),
    DIVU(Operation.DIVU, OperandListPrototypes.SOURCE_SOURCE2, InstructionAssemblers.SOURCE_SOURCE2),
    DMUL(Operation.DMUL, OperandListPrototypes.SOURCE_SOURCE2, InstructionAssemblers.SOURCE_SOURCE2),
    DMULU(Operation.DMULU, OperandListPrototypes.SOURCE_SOURCE2, InstructionAssemblers.SOURCE_SOURCE2),
    DSLL(Operation.DSLL, OperandListPrototypes.DESTINATION_SOURCE2_SHIFT_AMOUNT, InstructionAssemblers.DESTINATION_SOURCE2_SHIFT_AMOUNT),
    DSLL32(Operation.DSLL32, OperandListPrototypes.DESTINATION_SOURCE2_SHIFT_AMOUNT, InstructionAssemblers.DESTINATION_SOURCE2_SHIFT_AMOUNT),
    DSLLV(Operation.DSLLV, OperandListPrototypes.DESTINATION_SOURCE2_SOURCE, InstructionAssemblers.DESTINATION_SOURCE2_SOURCE),
    DSRA(Operation.DSRA, OperandListPrototypes.DESTINATION_SOURCE2_SHIFT_AMOUNT, InstructionAssemblers.DESTINATION_SOURCE2_SHIFT_AMOUNT),
    DSRA32(Operation.DSRA32, OperandListPrototypes.DESTINATION_SOURCE2_SHIFT_AMOUNT, InstructionAssemblers.DESTINATION_SOURCE2_SHIFT_AMOUNT),
    DSRAV(Operation.DSRAV, OperandListPrototypes.DESTINATION_SOURCE2_SOURCE, InstructionAssemblers.DESTINATION_SOURCE2_SOURCE),
    DSRL(Operation.DSRL, OperandListPrototypes.DESTINATION_SOURCE2_SHIFT_AMOUNT, InstructionAssemblers.DESTINATION_SOURCE2_SHIFT_AMOUNT),
    DSRL32(Operation.DSRL32, OperandListPrototypes.DESTINATION_SOURCE2_SHIFT_AMOUNT, InstructionAssemblers.DESTINATION_SOURCE2_SHIFT_AMOUNT),
    DSRLV(Operation.DSRLV, OperandListPrototypes.DESTINATION_SOURCE2_SOURCE, InstructionAssemblers.DESTINATION_SOURCE2_SOURCE),
    DSUB(Operation.DSUB, OperandListPrototypes.DESTINATION_SOURCE_SOURCE2, InstructionAssemblers.DESTINATION_SOURCE_SOURCE2),
    DSUBU(Operation.DSUBU, OperandListPrototypes.DESTINATION_SOURCE_SOURCE2, InstructionAssemblers.DESTINATION_SOURCE_SOURCE2),
    J(Operation.J, OperandListPrototypes.TARGET, InstructionAssemblers.TARGET),
    JAL(Operation.JAL, OperandListPrototypes.TARGET, InstructionAssemblers.TARGET),
    // Destination 31 can be implied, but it is not implemented here.
    JALR(Operation.JALR, OperandListPrototypes.DESTINATION_SOURCE, InstructionAssemblers.DESTINATION_SOURCE),
    JR(Operation.JR, OperandListPrototypes.SOURCE, InstructionAssemblers.SOURCE),
    LB(Operation.LB, OperandListPrototypes.SOURCE2_OFFSET_BASE, InstructionAssemblers.SOURCE2_OFFSET_BASE),
    LBU(Operation.LBU, OperandListPrototypes.SOURCE2_OFFSET_BASE, InstructionAssemblers.SOURCE2_OFFSET_BASE),
    LD(Operation.LD, OperandListPrototypes.SOURCE2_OFFSET_BASE, InstructionAssemblers.SOURCE2_OFFSET_BASE),
    LDC1(Operation.LDC1, OperandListPrototypes.SOURCE2_OFFSET_BASE, InstructionAssemblers.SOURCE2_OFFSET_BASE),
    LDC2(Operation.LDC2, OperandListPrototypes.SOURCE2_OFFSET_BASE, InstructionAssemblers.SOURCE2_OFFSET_BASE),
    LDL(Operation.LDL, OperandListPrototypes.SOURCE2_OFFSET_BASE, InstructionAssemblers.SOURCE2_OFFSET_BASE),
    LDR(Operation.LDR, OperandListPrototypes.SOURCE2_OFFSET_BASE, InstructionAssemblers.SOURCE2_OFFSET_BASE),
    LH(Operation.LH, OperandListPrototypes.SOURCE2_OFFSET_BASE, InstructionAssemblers.SOURCE2_OFFSET_BASE),
    LHU(Operation.LHU, OperandListPrototypes.SOURCE2_OFFSET_BASE, InstructionAssemblers.SOURCE2_OFFSET_BASE),
    LL(Operation.LL, OperandListPrototypes.SOURCE2_OFFSET_BASE, InstructionAssemblers.SOURCE2_OFFSET_BASE),
    LLD(Operation.LLD, OperandListPrototypes.SOURCE2_OFFSET_BASE, InstructionAssemblers.SOURCE2_OFFSET_BASE),
    LUI(Operation.LUI, OperandListPrototypes.SOURCE2_IMMEDIATE, InstructionAssemblers.SOURCE2_IMMEDIATE),
    LW(Operation.LW, OperandListPrototypes.SOURCE2_OFFSET_BASE, InstructionAssemblers.SOURCE2_OFFSET_BASE),
    LWC1(Operation.LWC1, OperandListPrototypes.SOURCE2_OFFSET_BASE, InstructionAssemblers.SOURCE2_OFFSET_BASE),
    LWC2(Operation.LWC2, OperandListPrototypes.SOURCE2_OFFSET_BASE, InstructionAssemblers.SOURCE2_OFFSET_BASE),
    LWC3(Operation.LWC3, OperandListPrototypes.SOURCE2_OFFSET_BASE, InstructionAssemblers.SOURCE2_OFFSET_BASE),
    LWL(Operation.LWL, OperandListPrototypes.SOURCE2_OFFSET_BASE, InstructionAssemblers.SOURCE2_OFFSET_BASE),
    LWR(Operation.LWR, OperandListPrototypes.SOURCE2_OFFSET_BASE, InstructionAssemblers.SOURCE2_OFFSET_BASE),
    LWU(Operation.LWU, OperandListPrototypes.SOURCE2_OFFSET_BASE, InstructionAssemblers.SOURCE2_OFFSET_BASE),
    MFHI(Operation.MFHI, OperandListPrototypes.DESTINATION, InstructionAssemblers.DESTINATION),
    MFLO(Operation.MFLO, OperandListPrototypes.DESTINATION, InstructionAssemblers.DESTINATION),
    MOVN(Operation.MOVN, OperandListPrototypes.DESTINATION_SOURCE_SOURCE2, InstructionAssemblers.DESTINATION_SOURCE_SOURCE2),
    MOVZ(Operation.MOVZ, OperandListPrototypes.DESTINATION_SOURCE_SOURCE2, InstructionAssemblers.DESTINATION_SOURCE_SOURCE2),
    MTHI(Operation.MTHI, OperandListPrototypes.SOURCE, InstructionAssemblers.SOURCE),
    MTLO(Operation.MTLO, OperandListPrototypes.SOURCE, InstructionAssemblers.SOURCE),
    MULT(Operation.MULT, OperandListPrototypes.SOURCE_SOURCE2, InstructionAssemblers.SOURCE_SOURCE2),
    MULTU(Operation.MULTU, OperandListPrototypes.SOURCE_SOURCE2, InstructionAssemblers.SOURCE_SOURCE2),
    NOR(Operation.NOR, OperandListPrototypes.DESTINATION_SOURCE_SOURCE2, InstructionAssemblers.DESTINATION_SOURCE_SOURCE2),
    //TODO: NOOP,
    OR(Operation.OR, OperandListPrototypes.DESTINATION_SOURCE_SOURCE2, InstructionAssemblers.DESTINATION_SOURCE_SOURCE2),
    ORI(Operation.ORI, OperandListPrototypes.SOURCE2_SOURCE_IMMEDIATE, InstructionAssemblers.SOURCE2_SOURCE_IMMEDIATE),
    PREF(Operation.PREF, OperandListPrototypes.HINT_OFFSET_BASE, InstructionAssemblers.HINT_OFFSET_BASE),
    SB(Operation.SB, OperandListPrototypes.SOURCE2_OFFSET_BASE, InstructionAssemblers.SOURCE2_OFFSET_BASE),
    SC(Operation.SC, OperandListPrototypes.SOURCE2_OFFSET_BASE, InstructionAssemblers.SOURCE2_OFFSET_BASE),
    SCD(Operation.SCD, OperandListPrototypes.SOURCE2_OFFSET_BASE, InstructionAssemblers.SOURCE2_OFFSET_BASE),
    SD(Operation.SD, OperandListPrototypes.SOURCE2_OFFSET_BASE, InstructionAssemblers.SOURCE2_OFFSET_BASE),
    SDC1(Operation.SDC1, OperandListPrototypes.SOURCE2_OFFSET_BASE, InstructionAssemblers.SOURCE2_OFFSET_BASE),
    SDC2(Operation.SDC2, OperandListPrototypes.SOURCE2_OFFSET_BASE, InstructionAssemblers.SOURCE2_OFFSET_BASE),
    SDL(Operation.SDL, OperandListPrototypes.SOURCE2_OFFSET_BASE, InstructionAssemblers.SOURCE2_OFFSET_BASE),
    SDR(Operation.SDR, OperandListPrototypes.SOURCE2_OFFSET_BASE, InstructionAssemblers.SOURCE2_OFFSET_BASE),
    SH(Operation.SH, OperandListPrototypes.SOURCE2_OFFSET_BASE, InstructionAssemblers.SOURCE2_OFFSET_BASE),
    SLL(Operation.SLL, OperandListPrototypes.DESTINATION_SOURCE2_SHIFT_AMOUNT, InstructionAssemblers.DESTINATION_SOURCE2_SHIFT_AMOUNT),
    SLLV(Operation.SLLV, OperandListPrototypes.DESTINATION_SOURCE2_SOURCE, InstructionAssemblers.DESTINATION_SOURCE2_SOURCE),
    SLT(Operation.SLT, OperandListPrototypes.DESTINATION_SOURCE_SOURCE2, InstructionAssemblers.DESTINATION_SOURCE_SOURCE2),
    SLTI(Operation.SLTI, OperandListPrototypes.SOURCE2_SOURCE_IMMEDIATE, InstructionAssemblers.SOURCE2_SOURCE_IMMEDIATE),
    SLTIU(Operation.SLTIU, OperandListPrototypes.SOURCE2_SOURCE_IMMEDIATE, InstructionAssemblers.SOURCE2_SOURCE_IMMEDIATE),
    SLTU(Operation.SLTU, OperandListPrototypes.DESTINATION_SOURCE_SOURCE2, InstructionAssemblers.DESTINATION_SOURCE_SOURCE2),
    SRA(Operation.SRA, OperandListPrototypes.DESTINATION_SOURCE2_SHIFT_AMOUNT, InstructionAssemblers.DESTINATION_SOURCE2_SHIFT_AMOUNT),
    SRAV(Operation.SRAV, OperandListPrototypes.DESTINATION_SOURCE2_SOURCE, InstructionAssemblers.DESTINATION_SOURCE2_SOURCE),
    SRL(Operation.SRL, OperandListPrototypes.DESTINATION_SOURCE2_SHIFT_AMOUNT, InstructionAssemblers.DESTINATION_SOURCE2_SHIFT_AMOUNT),
    SRLV(Operation.SRLV, OperandListPrototypes.DESTINATION_SOURCE2_SOURCE, InstructionAssemblers.DESTINATION_SOURCE2_SOURCE),
    SUB(Operation.SUB, OperandListPrototypes.DESTINATION_SOURCE_SOURCE2, InstructionAssemblers.DESTINATION_SOURCE_SOURCE2),
    SUBU(Operation.SUBU, OperandListPrototypes.DESTINATION_SOURCE_SOURCE2, InstructionAssemblers.DESTINATION_SOURCE_SOURCE2),
    SW(Operation.SW, OperandListPrototypes.SOURCE2_OFFSET_BASE, InstructionAssemblers.SOURCE2_OFFSET_BASE),
    SWC1(Operation.SWC1, OperandListPrototypes.SOURCE2_OFFSET_BASE, InstructionAssemblers.SOURCE2_OFFSET_BASE),
    SWC2(Operation.SWC2, OperandListPrototypes.SOURCE2_OFFSET_BASE, InstructionAssemblers.SOURCE2_OFFSET_BASE),
    SWC3(Operation.SWC3, OperandListPrototypes.SOURCE2_OFFSET_BASE, InstructionAssemblers.SOURCE2_OFFSET_BASE),
    SWL(Operation.SWL, OperandListPrototypes.SOURCE2_OFFSET_BASE, InstructionAssemblers.SOURCE2_OFFSET_BASE),
    SWR(Operation.SWR, OperandListPrototypes.SOURCE2_OFFSET_BASE, InstructionAssemblers.SOURCE2_OFFSET_BASE),
    // TODO: SYSCALL,
    XOR(Operation.XOR, OperandListPrototypes.DESTINATION_SOURCE_SOURCE2, InstructionAssemblers.DESTINATION_SOURCE_SOURCE2),
    XORI(Operation.XORI, OperandListPrototypes.SOURCE2_SOURCE_IMMEDIATE, InstructionAssemblers.SOURCE2_SOURCE_IMMEDIATE);

    private static final Map<Operation, OperationInformation> OPERATION_MAP = new EnumMap<>(Operation.class);
    static {
        for (OperationInformation operationInformation : values()) {
            OPERATION_MAP.put(operationInformation.getOperation(), operationInformation);
        }
    }

    private Operation operation;
    private OperandPrototype[] operandListPrototype;
    private InstructionAssembler instructionAssembler;

    OperationInformation(Operation operation, OperandPrototype[] operandListPrototype,
                         InstructionAssembler instructionAssembler) {
        this.operation = operation;
        this.operandListPrototype = operandListPrototype;
        this.instructionAssembler = instructionAssembler;
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

    public InstructionAssembler getInstructionAssembler() {
        return instructionAssembler;
    }
}
