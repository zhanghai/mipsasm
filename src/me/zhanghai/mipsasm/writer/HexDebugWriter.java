/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.writer;

import me.zhanghai.mipsasm.Constants;
import me.zhanghai.mipsasm.assembler.AssemblyContext;
import me.zhanghai.mipsasm.util.BitArray;
import me.zhanghai.mipsasm.util.IoUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class HexDebugWriter {

    private HexDebugWriter() {}

    public static void write(OutputStream outputStream, AssemblyContext context) throws WriterException {

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));

        int address = 0;
        for (BitArray assembly : context.getAssembly()) {
            String assemblyString = IoUtils.wordToHexString(assembly.value());
            try {
                if (address % (4 * Constants.BYTES_PER_WORD) == 0) {
                    if (address != 0) {
                        writer.newLine();
                    }
                    writer.write(IoUtils.wordToHexString(address));
                    writer.write(":");
                }
                writer.write(" ");
                writer.write(assemblyString);
            } catch (IOException e) {
                throw new WriterException("Error writing " + assemblyString, e);
            }
            address += assembly.length() / Constants.BYTE_LENGTH;
        }

        try {
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            throw new WriterException("Error writing", e);
        }
    }
}
