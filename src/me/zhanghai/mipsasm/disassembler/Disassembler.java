/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.disassembler;

import me.zhanghai.mipsasm.Constants;
import me.zhanghai.mipsasm.assembler.Assemblable;
import me.zhanghai.mipsasm.util.BitArray;
import me.zhanghai.mipsasm.util.IoUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class Disassembler {

    public static void disassemble(InputStream inputStream, OutputStream outputStream)
            throws DisassemblerException {

        DisassemblyContext context = new DisassemblyContext();

        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        while (true) {
            BitArray bitArray;
            try {
                bitArray = IoUtils.readBitArray(bufferedInputStream);
            } catch (IOException e) {
                throw new DisassemblerException("Error reading", e);
            }
            if (bitArray == null) {
                break;
            }
            // 0 can be sll $zero, $zero, 0, making all spacing instructions. In this case we prefer interpreting it as
            // spacing.
            if (bitArray.length() == Constants.WORD_LENGTH && !bitArray.isZero()) {
                WordDisassembler.disassemble(bitArray, context);
            } else {
                StorageDirectiveDisassembler.disassemble(bitArray, context);
            }
        }
        context.packStorageDirective();

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
        for (Map.Entry<Integer, Assemblable> entry : context.getAddressAssemblableMap().entrySet()) {
            Integer address = entry.getKey();
            String label = context.getLabel(address);
            if (label != null) {
                try {
                    writer.write(label);
                    writer.write(":");
                    writer.newLine();
                } catch (IOException e) {
                    throw new DisassemblerException("Error writing label: " + label, e);
                }
            }
            Assemblable assemblable = entry.getValue();
            String assemblableString = assemblable.toString();
            try {
                writer.write(assemblableString);
                writer.newLine();
            } catch (IOException e) {
                throw new DisassemblerException("Error writing assemblable: " + assemblableString, e);
            }
        }
        try {
            writer.flush();
        } catch (IOException e) {
            throw new DisassemblerException("Error writing", e);
        }
    }
}
