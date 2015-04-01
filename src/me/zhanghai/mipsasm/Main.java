/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm;

import me.zhanghai.mipsasm.assembler.Assembler;
import me.zhanghai.mipsasm.assembler.AssemblerException;
import me.zhanghai.mipsasm.assembler.AssemblyContext;
import me.zhanghai.mipsasm.parser.Parser;
import me.zhanghai.mipsasm.parser.ParserException;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {

        //System.setIn();

        AssemblyContext context = new AssemblyContext();

        BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            Parser.parse(inputReader, context);
        } catch (ParserException e) {
            e.printStackTrace();
        }

        try {
            Assembler.assemble(context);
        } catch (AssemblerException e) {
            e.printStackTrace();
        }
    }
}
