/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.disassembler;

import me.zhanghai.mipsasm.Constants;
import me.zhanghai.mipsasm.InternalException;
import me.zhanghai.mipsasm.assembler.InstructionInformation;
import me.zhanghai.mipsasm.assembler.Operation;
import me.zhanghai.mipsasm.util.BitArray;

public class InstructionWordDisassembler {

    public static void disassemble(BitArray bitArray, DisassemblyContext context) throws DisassemblerException {

        if (bitArray.length() != Constants.INSTRUCTION_LENGTH) {
            throw new InternalException(new IllegalArgumentException(
                    "BitArray cannot be an instruction by its length, BitArray: " + bitArray));
        }

        BitArray operationCode = bitArray.subArray(26, 32);
        BitArray operationSource = bitArray.subArray(21, 26);
        BitArray operationSource2 = bitArray.subArray(16, 21);
        BitArray operationFunction = bitArray.subArray(0, 6);
        Operation operation;
        try {
            operation = Operation.of(operationCode, operationSource, operationSource2, operationFunction);
        } catch (IllegalArgumentException e) {
            throw new NoSuchOperationException("Instruction: " + bitArray, e);
        }

        InstructionInformation instructionInformation = InstructionInformation.ofOperation(operation);
        InstructionDisassembler instructionDisassembler;
        try {
            instructionDisassembler = instructionInformation.getDisassembler();
        } catch (InternalException e) {
            throw new DisassemblerException("Operation has no instruction disassembler: " + operation, e);
        }
        instructionDisassembler.disassemble(instructionInformation, bitArray, context);
    }
}
