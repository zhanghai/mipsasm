/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.parser;

import me.zhanghai.mipsasm.assembler.*;
import me.zhanghai.mipsasm.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InstructionParser {

    private static final Pattern PATTERN = Pattern.compile("(\\S+)(?:\\s+(.*))?");

    private static final ThreadLocal<Matcher> MATCHER = new ThreadLocal<Matcher>() {
        @Override
        protected Matcher initialValue() {
            return PATTERN.matcher("");
        }
    };

    private InstructionParser() {}

    public static void parse(String instructionString, AssemblyContext context) throws ParserException {

        Matcher matcher = MATCHER.get().reset(instructionString);
        if (!matcher.matches()) {
            throw new IllegalInstructionException("Instruction: " + instructionString);
        }

        String operationName = matcher.group(1);
        Operation operation;
        try {
            operation = Operation.valueOf(operationName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new NoSuchOperationException("Operation: " + operationName);
        }

        String operandListString = matcher.group(2);
        String[] operandStringList = operandListString != null ?
                StringUtils.splitAndTrim(operandListString, Tokens.OPERAND_SEPARATOR_REGEX) : new String[0];
        OperationInformation operationInformation = OperationInformation.ofOperation(operation);
        OperandInstance[] operandInstances = OperandListParser.parse(operandStringList,
                operationInformation.getOperandListPrototype());

        context.appendAssemblable(Instruction.of(operation, operandInstances,
                operationInformation.getInstructionAssembler()));
    }
}
