/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.assembler;

import me.zhanghai.mipsasm.parser.LabelAlreadyDefinedException;
import me.zhanghai.mipsasm.util.BitArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AssemblyContext {

    private int offset;

    private Map<String, Integer> labelOffsetMap = new HashMap<>();

    private List<Integer> assembly = new ArrayList<>();

    public void offsetBy(int words) {
        offset += words;
    }

    public void offsetByWord() {
        offsetBy(1);
    }

    public int getOffset() {
        return offset;
    }

    public void setLabelAtOffset(String name) {
        if (labelOffsetMap.containsKey(name)) {
            throw new LabelAlreadyDefinedException("Label name: " + name + ", old offset: " + labelOffsetMap.get(name)
                    + ", new offset: " + offset);
        }
        labelOffsetMap.put(name, offset);
    }

    public int getLabelOffset(String name) {
        if (!labelOffsetMap.containsKey(name)) {
            throw new UnknownLabelException("Label name: " + name);
        }
        return labelOffsetMap.get(name);
    }

    // NOTE: offset is automatically incremented.
    public void appendWord(BitArray bitArray) {
        if (bitArray.length() != Integer.SIZE) {
            throw new IllegalArgumentException("bitArray length not equal to " + Integer.SIZE + ": " + bitArray.length());
        }
        assembly.add(bitArray.value());
        offsetByWord();
    }
}
