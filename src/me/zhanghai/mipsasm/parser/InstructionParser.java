/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.parser;

import me.zhanghai.mipsasm.assembler.*;
import me.zhanghai.mipsasm.util.RegexUtils;

import java.util.regex.Matcher;

public class InstructionParser {

    private static final ThreadLocal<Matcher> MATCHER = RegexUtils.makeThreadLocalMatcher("(\\S+)(?:\\s+(.*))?");

    private InstructionParser() {}

    public static void parse(String instructionString, AssemblyContext context) throws ParserException {

        Matcher matcher = MATCHER.get().reset(instructionString);
        if (!matcher.matches()) {
            throw new IllegalInstructionException(instructionString);
        }

        String operationName = matcher.group(1);
        Operation operation;
        try {
            operation = Operation.valueOf(operationName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new NoSuchOperationException(operationName);
        }

        String operandListString = matcher.group(2);
        String[] operandStringList = ParserSplitUtils.splitOperands(operandListString);
        OperandInstance[] operandInstances = OperandListParser.parse(operandStringList,
                InstructionInformation.ofOperation(operation).getOperandListPrototype());

        context.appendAssemblable(Instruction.of(operation, operandInstances));
    }
}
