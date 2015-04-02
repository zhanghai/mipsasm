/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.writer;

import me.zhanghai.mipsasm.assembler.AssemblyContext;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class CoeWriter {

    private CoeWriter() {}

    public static void write(OutputStream outputStream, AssemblyContext context) throws WriterException {

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));

        try {
            writer.write("memory_initialization_radix=16;");
            writer.newLine();
            writer.write("memory_initialization_vector=");
            writer.newLine();
        } catch (IOException e) {
            throw new WriterException("Error writing header", e);
        }

        boolean first = true;
        for (Integer assembly : context.getAssembly()) {
            String assemblyString = String.format("%08X", assembly);
            try {
                if (first) {
                    first = false;
                } else {
                    writer.write(",");
                    writer.newLine();
                }
                writer.write(assemblyString);
            } catch (IOException e) {
                throw new WriterException("Error writing " + Integer.toHexString(assembly), e);
            }
        }
        try {
            writer.write(";");
            writer.newLine();
        } catch (IOException e) {
            throw new WriterException("Error writing footer", e);
        }

        try {
            writer.flush();
        } catch (IOException e) {
            throw new WriterException("Error flushing", e);
        }
    }
}
