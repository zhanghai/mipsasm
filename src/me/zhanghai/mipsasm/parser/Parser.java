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

public class Parser {

    private Parser() {}

    public static void parse(InputStream inputStream, AssemblyContext context) throws ParserException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        int lineNumber = 0;
        while (true) {
            String line;
            try {
                line = reader.readLine();
            } catch (IOException e) {
                throw new ParserException("Line: " + lineNumber, e);
            }
            if (line == null) {
                break;
            }
            try {
                LineParser.parse(line, context);
            } catch (ParserException e) {
                throw new ParserException("Line: " + lineNumber + ", " + line, e);
            }
            ++lineNumber;
        }
        context.finishAllocation();
    }
}
