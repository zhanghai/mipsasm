/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.disassembler;

import me.zhanghai.mipsasm.Constants;
import me.zhanghai.mipsasm.util.BitArray;
import me.zhanghai.mipsasm.util.IoUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Disassembler {

    public static void disassemble(InputStream inputStream, DisassemblyContext context) throws DisassemblerException {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        while (true) {
            BitArray bitArray;
            try {
                bitArray = IoUtils.readBitArray(bufferedInputStream);
            } catch (IOException e) {
                throw new DisassemblerException(e);
            }
            if (bitArray == null) {
                break;
            }
            if (bitArray.length() == Constants.WORD_LENGTH) {
                WordDisassembler.disassemble(bitArray, context);
            } else {
                StorageDirectiveDisassembler.disassemble(bitArray, context);
            }
        }
    }
}
