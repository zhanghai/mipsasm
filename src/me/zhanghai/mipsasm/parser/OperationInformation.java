/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.parser;

import me.zhanghai.mipsasm.instruction.*;

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
    private OperandPrototype[] operandListPrototype;
    private InstructionCompiler instructionCompiler;

    OperationInformation(Operation operation, OperandPrototype[] operandListPrototype,
                         InstructionCompiler instructionCompiler) {
        this.operation = operation;
        this.operandListPrototype = operandListPrototype;
        this.instructionCompiler = instructionCompiler;
    }
}
