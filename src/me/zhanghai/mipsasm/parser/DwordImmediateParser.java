/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.parser;

import me.zhanghai.mipsasm.assembler.DwordImmediate;
import me.zhanghai.mipsasm.util.IoUtils;

public class DwordImmediateParser {

    private DwordImmediateParser() {}

    public static DwordImmediate parse(String immediateString) throws ParserException {
        try {
            return DwordImmediate.of(IoUtils.decodeUnsignedLong(immediateString));
        } catch (NumberFormatException e) {
            throw new IllegalOperandException("Unable to parse dword immediate: " + immediateString, e);
        } catch (IllegalArgumentException e) {
            throw new IllegalOperandException("Dword immediate length too long: " + immediateString, e);
        }
    }
}
