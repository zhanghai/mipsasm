/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.parser;

import me.zhanghai.mipsasm.assembler.Address;
import me.zhanghai.mipsasm.assembler.AssemblyContext;
import me.zhanghai.mipsasm.assembler.BackwardAddressException;
import me.zhanghai.mipsasm.assembler.DataDirective;
import me.zhanghai.mipsasm.util.IoUtils;

import java.util.Arrays;

public class DataDirectiveParser {

    private DataDirectiveParser() {}

    public static void parse(String[] operandStringList, AssemblyContext context) throws ParserException {

        if (operandStringList.length != 1) {
            throw new OperandCountMismatchException("Expected: [Address], found: "
                    + Arrays.toString(operandStringList));
        }

        String addressString = operandStringList[0];
        Address address;
        try {
            address = Address.of(IoUtils.decodeUnsignedInteger(addressString));
        } catch (IllegalArgumentException e) {
            throw new IllegalOperandException("Address: " + addressString, e);
        }
        try {
            context.appendAssemblable(DataDirective.of(address));
        } catch (BackwardAddressException e) {
            throw new IllegalOperandException("Address moves backward", e);
        }
    }
}
