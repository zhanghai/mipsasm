/*
 * Copyright (c) 2019 Chen Mouxiang <cmx_1007@foxmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.writer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

import me.zhanghai.mipsasm.assembler.AssemblyContext;
import me.zhanghai.mipsasm.util.BitArray;
import me.zhanghai.mipsasm.util.IoUtils;

public class HexWriter {

    private HexWriter() {}

    public static void write(OutputStream outputStream, AssemblyContext context) throws WriterException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));

        for (BitArray assembly : context.getAssembly()) {
            String assemblyString = IoUtils.wordToHexString(assembly.value());
            try {
                writer.write(assemblyString);
                writer.newLine();
            } catch (IOException e) {
                throw new WriterException("Error writing " + assemblyString, e);
            }
        }

        try {
            writer.flush();
        } catch (IOException e) {
            throw new WriterException("Error flushing", e);
        }
    }
}
