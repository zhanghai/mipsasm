/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.parser;

import me.zhanghai.mipsasm.assembler.AssemblyContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Parser {

    private static final String CONTINUATION_STRING = "\\";

    private Parser() {}

    public static void parse(InputStream inputStream, AssemblyContext context) throws ParserException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        int lineNumber = 0;
        String line = "";
        boolean hasContinuation = false;
        while (true) {
            ++lineNumber;
            try {
                if (hasContinuation) {
                    line += reader.readLine();
                } else {
                    line = reader.readLine();
                }
            } catch (IOException e) {
                throw new ParserException("Line " + lineNumber, e);
            }
            if (line == null) {
                break;
            }
            hasContinuation = line.endsWith(CONTINUATION_STRING);
            if (hasContinuation) {
                line = line.substring(0, line.length() - CONTINUATION_STRING.length());
            } else {
                try {
                    LineParser.parse(line, context);
                } catch (ParserException e) {
                    throw new ParserException("Line " + lineNumber + ": " + line, e);
                }
            }
        }
        context.finishAllocation();
    }
}
