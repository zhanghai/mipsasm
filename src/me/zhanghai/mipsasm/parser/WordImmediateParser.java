/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.parser;

import me.zhanghai.mipsasm.assembler.WordImmediate;
import me.zhanghai.mipsasm.util.IoUtils;

public class WordImmediateParser {

    private WordImmediateParser() {}

    public static WordImmediate parse(String wordImmediateString) throws ParserException {
        try {
            return WordImmediate.of(IoUtils.decodeUnsignedInt(wordImmediateString));
        } catch (NumberFormatException e) {
            throw new IllegalOperandException("Unable to parse word immediate: " + wordImmediateString, e);
        } catch (IllegalArgumentException e) {
            throw new IllegalOperandException("Word immediate length too long: " + wordImmediateString, e);
        }
    }
}
