/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.writer;

import me.zhanghai.mipsasm.assembler.AssemblyContext;
import me.zhanghai.mipsasm.util.BitArray;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class BinaryWriter {

    public static void write(OutputStream outputStream, AssemblyContext context) throws WriterException {

        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        for (BitArray assembly : context.getAssembly()) {
            try {
                dataOutputStream.writeInt(assembly.value());
            } catch (IOException e) {
                throw new WriterException("Error writing " + String.format("%08X", assembly), e);
            }
        }

        try {
            dataOutputStream.flush();
        } catch (IOException e) {
            throw new WriterException("Error flushing", e);
        }
    }
}
