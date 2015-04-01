/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.parser;

import me.zhanghai.mipsasm.assembler.Immediate;
import me.zhanghai.mipsasm.util.IoUtils;

public class ImmediateParser {

    private ImmediateParser() {}

    public static Immediate parse(String immediateString) {
        try {
            return Immediate.of(IoUtils.decodeInteger(immediateString));
        } catch (NumberFormatException e) {
            throw new InvalidOperandException("Unable to parse immediate: " + immediateString, e);
        } catch (IllegalArgumentException e) {
            throw new InvalidOperandException("Immediate length too long: " + immediateString, e);
        }
    }
}
