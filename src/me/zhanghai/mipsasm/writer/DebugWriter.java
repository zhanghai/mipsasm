/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.writer;

import me.zhanghai.mipsasm.assembler.AssemblyContext;
import me.zhanghai.mipsasm.util.IoUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class DebugWriter {

    private DebugWriter() {}

    public static void write(OutputStream outputStream, AssemblyContext context) throws WriterException {

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));

        int offset = 0;
        for (Integer assembly : context.getAssembly()) {
            String assemblyString = IoUtils.toBinaryString(assembly);
            try {
                writer.write("0x");
                writer.write(IoUtils.toHexString(offset));
                writer.write(": ");
                writer.write(assemblyString);
                writer.newLine();
            } catch (IOException e) {
                throw new WriterException("Error writing " + assemblyString, e);
            }
            offset += 4;
        }

        try {
            writer.flush();
        } catch (IOException e) {
            throw new WriterException("Error flushing", e);
        }
    }
}
