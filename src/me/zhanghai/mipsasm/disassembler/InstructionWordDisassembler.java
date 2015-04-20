/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.disassembler;

import me.zhanghai.mipsasm.Constants;
import me.zhanghai.mipsasm.InternalException;
import me.zhanghai.mipsasm.assembler.Operation;
import me.zhanghai.mipsasm.assembler.OperationInformation;
import me.zhanghai.mipsasm.util.BitArray;

public class InstructionWordDisassembler {

    public static void disassemble(BitArray bitArray, DisassemblyContext context) throws DisassemblerException {

        if (bitArray.length() != Constants.INSTRUCTION_LENGTH) {
            throw new InternalException(new IllegalArgumentException(
                    "BitArray cannot be an instruction by its length, BitArray: " + bitArray));
        }

        BitArray operationCode = bitArray.subArray(26, 32);
        BitArray operationFunction = bitArray.subArray(0, 6);
        BitArray operationSource2 = bitArray.subArray(16, 21);
        Operation operation;
        try {
            operation = Operation.of(operationCode, operationFunction, operationSource2);
        } catch (IllegalArgumentException e) {
            throw new NoSuchOperationException("Operation code: " + operationCode + ", function: " + operationFunction,
                    e);
        }

        OperationInformation operationInformation = OperationInformation.of(operation);
        operationInformation.getInstructionDisassembler().disassemble(operationInformation, bitArray, context);
    }
}
