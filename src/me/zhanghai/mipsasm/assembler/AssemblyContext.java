/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.assembler;

import me.zhanghai.mipsasm.parser.ParserException;
import me.zhanghai.mipsasm.util.BitArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AssemblyContext {

    private int offset;

    private Map<String, Integer> labelOffsetMap = new HashMap<>();

    private List<Instruction> instructions = new ArrayList<>();

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

    public void addLabelAtOffset(String name) throws ParserException {
        if (labelOffsetMap.containsKey(name)) {
            throw new LabelAlreadyDefinedException("Label name: " + name + ", old offset: " + labelOffsetMap.get(name)
                    + ", new offset: " + offset);
        }
        labelOffsetMap.put(name, offset);
    }

    public int getLabelOffset(String name) throws UndefinedLabelException {
        if (!labelOffsetMap.containsKey(name)) {
            throw new UndefinedLabelException("Label name: " + name);
        }
        return labelOffsetMap.get(name);
    }

    public void appendInstruction(Instruction instruction) {
        instructions.add(instruction);
    }

    public List<Instruction> getInstructions() {
        return instructions;
    }

    // NOTE: offset is automatically incremented.
    public void appendAssembly(BitArray bitArray) {
        if (bitArray.length() != Integer.SIZE) {
            throw new IllegalArgumentException("bitArray length not equal to " + Integer.SIZE + ": " + bitArray.length());
        }
        assembly.add(bitArray.value());
        offsetByWord();
    }
}
