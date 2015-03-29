/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.parser;

import me.zhanghai.mipsasm.instruction.Instruction.Type;
import me.zhanghai.mipsasm.instruction.Operation;
import me.zhanghai.mipsasm.instruction.ShiftAmount;
import me.zhanghai.mipsasm.parser.OperandFormat.OperandType;

public enum OperationInformation {

    ADD(Operation.ADD, Type.Register, new OperandFormat[] {
            new OperandFormat(OperandType.REGISTER),
            new OperandFormat(OperandType.REGISTER),
            new OperandFormat(OperandType.REGISTER),
            new OperandFormat(OperandType.SHIFT_AMOUNT, ShiftAmount.of(0b0))
    }),
    ADDI(Operation.ADDI, Type.Immediate, new OperandFormat[] {
            new OperandFormat(OperandType.REGISTER),
            new OperandFormat(OperandType.REGISTER),
            new OperandFormat(OperandType.IMMEDIATE)
    }),
    ADDIU(0b001001),
    ADDU(0b000000, 0b100001),
    AND(0b000000, 0b100100),
    ANDI(0b001100),
    BEQ(0b000100),
    BGEZ(0b000001),
    BGEZAL(0b000001),
    BGTZ(0b000111),
    BLEZ(0b000110),
    BLTZ(0b000001),
    BLTZAL(0b000001),
    BNE(0b000101),
    DIV(0b000000, 0b011010),
    DIVU(0b000000, 0b011011),
    J(0b000010),
    JAL(0b000011),
    JR(0b000000, 0b001000),
    LB(0b100000),
    LUI(0b001111),
    LW(0b100011),
    MFHI(0b000000, 0b010000),
    MFLO(0b000000, 0b010010),
    MULT(0b000000, 0b011000),
    MULTU(0b000000, 0b011001),
    NOOP(0b000000, 0b000000),
    OR(0b000000, 0b100101),
    ORI(0b001101),
    SB(0b101000),
    SLL(0b000000, 0b000000),
    SLLV(0b000000, 0b000100),
    SLT(0b000000, 0b101010),
    SLTI(0b001010),
    SLTIU(0b001011),
    SLTU(0b000000, 0b101011),
    SRA(0b000000, 0b000011),
    SRL(0b000000, 0b000010),
    SRLV(0b000000, 0b000110),
    SUB(0b000000, 0b100010),
    SUBU(0b000000, 0b100011),
    SW(0b101011),
    SYSCALL(0b000000, 0b001100),
    XOR(0b000000, 0b100110),
    XORI(0b001110);

    private Operation operation;
    private Type type;
    private OperandFormat[] operandFormats;

    OperationInformation(Operation operation, Type type, OperandFormat[] operandFormats) {
        this.operation = operation;
        this.type = type;
        this.operandFormats = operandFormats;
    }
}
