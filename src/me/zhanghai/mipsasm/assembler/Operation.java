/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.assembler;

import me.zhanghai.mipsasm.InternalException;
import me.zhanghai.mipsasm.util.BitArray;

public enum Operation {

    ADD(Codes.SPECIAL, 0b100000),
    ADDI(0b001000),
    ADDIU(0b001001),
    ADDU(Codes.SPECIAL, 0b100001),
    AND(Codes.SPECIAL, 0b100100),
    ANDI(0b001100),
    // B, BAL, BC1F, BC1FL, ... , BC2TL
    BEQ(0b000100),
    BEQL(0b010100),
    BGEZ(Codes.REGIMM, 0b00001, true),
    BGEZAL(Codes.REGIMM, 0b10001, true),
    BGEZALL(Codes.REGIMM, 0b10011, true),
    BGEZL(Codes.REGIMM, 0b00011, true),
    BGTZ(0b000111, 0b00000, true),
    BGTZL(0b010111, 0b00000, true),
    BLEZ(0b000110, 0b00000, true),
    BLEZL(0b010110, 0b00000, true),
    BLTZ(Codes.REGIMM, 0b00000, true),
    BLTZAL(Codes.REGIMM, 0b10000, true),
    BLTZALL(Codes.REGIMM, 0b10010, true),
    BLTZL(Codes.REGIMM, 0b00010, true),
    BNE(0b000101),
    BNEL(0b010101),
    // BREAK, C, ... , CLZ
    COP2(Codes.COP2),
    // CTC, ... , DERET
    DERET(Codes.COP0, 0b011111),
    DIV(Codes.SPECIAL, 0b011010),
    DIVU(Codes.SPECIAL, 0b011011),
    ERET(Codes.COP0, 0b011000),
    J(0b000010),
    JAL(0b000011),
    JALR(Codes.SPECIAL, 0b001001),
    JR(Codes.SPECIAL, 0b001000),
    LA,
    LB(0b100000),
    LBU(0b100100),
    LDC1(0b110101),
    LDC2(0b110110),
    LH(0b100001),
    LHU(0b100101),
    LI,
    LL(0b110000),
    LUI(0b001111),
    LW(0b100011),
    LWC1(0b110001),
    LWC2(0b110010),
//    LWC3(0b110011),
    LWL(0b100010),
    LWR(0b100110),
    // MADD, ... MFC2
    MFHI(Codes.SPECIAL, 0b010000),
    MFLO(Codes.SPECIAL, 0b010010),
    MOVE,
    MOVN(Codes.SPECIAL, 0b001011),
    // MOVT
    MOVZ(Codes.SPECIAL, 0b001010),
    // MSUB, ... ,MTC2
    MTHI(Codes.SPECIAL, 0b010001),
    MTLO(Codes.SPECIAL, 0b010011),
    // MUL
    MULT(Codes.SPECIAL, 0b011000),
    MULTU(Codes.SPECIAL, 0b011001),
    NOR(Codes.SPECIAL, 0b100111),
    NOP(),
    OR(Codes.SPECIAL, 0b100101),
    ORI(0b001101),
    PREF(0b110011),
    SB(0b101000),
    SC(0b111000),
    // SDBBP
    SDC1(0b111101),
    SDC2(0b111110),
//    SDL(0b101100),
//    SDR(0b101101),
    SH(0b101001),
    SLL(Codes.SPECIAL, 0b000000),
    SLLV(Codes.SPECIAL, 0b000100),
    SLT(Codes.SPECIAL, 0b101010),
    SLTI(0b001010),
    SLTIU(0b001011),
    SLTU(Codes.SPECIAL, 0b101011),
    SRA(Codes.SPECIAL, 0b000011),
    SRAV(Codes.SPECIAL, 0b000111),
    SRL(Codes.SPECIAL, 0b000010),
    SRLV(Codes.SPECIAL, 0b000110),
    // SSNOP
    SUB(Codes.SPECIAL, 0b100010),
    SUBU(Codes.SPECIAL, 0b100011),
    SW(0b101011),
    SWC1(0b111001),
    SWC2(0b111010),
    SWC3(0b111011),
    SWL(0b101010),
    SWR(0b101110),
    TLBP(Codes.COP0, 0b001000),
    TLBR(Codes.COP0, 0b000001),
    TLBWI(Codes.COP0, 0b000010),
    TLBWR(Codes.COP0, 0b000110),
    // TEQ, ... , TNEI
    // TODO: SYSCALL(Codes.SPECIAL, 0b001100),
    WAIT(Codes.COP0, 0b100000),
    XOR(Codes.SPECIAL, 0b100110),
    XORI(0b001110);

    private static final int CODE_LENGTH = 6;
    private static final int FUNCTION_LENGTH = 6;
    private static final int SOURCE2_LENGTH = 5;

    private BitArray code;
    private BitArray function;
    private BitArray source2;

    private interface Codes {
        int SPECIAL = 0b000000;
        int REGIMM = 0b000001;
        int COP0 = 0b010000;
        int COP1 = 0b010001;
        int COP2 = 0b010010;
    }

    Operation(BitArray code, BitArray function, BitArray source2) {
        this.code = code;
        this.function = function;
        this.source2 = source2;
    }

    Operation(int code, int source2, boolean source2Dummy) {
        this(BitArray.of(code, CODE_LENGTH), null, BitArray.of(source2, SOURCE2_LENGTH));
    }

    Operation(int code, int function) {
        this(BitArray.of(code, CODE_LENGTH), BitArray.of(function, FUNCTION_LENGTH), null);
    }

    Operation(int code) {
        this(BitArray.of(code, CODE_LENGTH), null, null);
    }

    Operation() {
        this(null, null, null);
    }

    public static Operation of(BitArray code, BitArray function, BitArray source2) {
        for (Operation operation : values()) {
            if ((operation.code != null && operation.code.equals(code))
                    && (operation.function == null || operation.function.equals(function))
                    && (operation.source2 == null || operation.source2.equals(source2))) {
                return operation;
            }
        }
        throw new IllegalArgumentException("Unknown operation, code: " + code + ", function: " + function);
    }

    public BitArray getCode() {
        if (code == null) {
            throw new InternalException(new IllegalStateException("getCode() called on an Operation without a code"));
        }
        return code;
    }

    public BitArray getFunction() {
        if (function == null) {
            throw new InternalException(new IllegalStateException(
                    "getFunction() called on an Operation without a function"));
        }
        return function;
    }

    public BitArray getSource2() {
        if (source2 == null) {
            throw new InternalException(new IllegalStateException(
                    "getSource2() called on an Operation without a source2"));
        }
        return source2;
    }
}
