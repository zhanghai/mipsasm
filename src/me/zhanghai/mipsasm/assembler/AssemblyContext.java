/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.assembler;

import me.zhanghai.mipsasm.InternalException;
import me.zhanghai.mipsasm.parser.MultiplePendingLabelException;
import me.zhanghai.mipsasm.parser.ParserException;
import me.zhanghai.mipsasm.parser.PendingLabelException;
import me.zhanghai.mipsasm.util.BitArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AssemblyContext {

    public static final int BYTE_LENGTH = 8;
    public static final int HALF_WORD_LENGTH = 16;
    public static final int WORD_LENGTH = 32;
    public static final int ADDRESS_LENGTH = WORD_LENGTH;
    public static final int INSTRUCTION_LENGTH = WORD_LENGTH;
    public static final int BYTES_PER_HALF_WORD = HALF_WORD_LENGTH / BYTE_LENGTH;
    public static final int BYTES_PER_WORD = WORD_LENGTH / BYTE_LENGTH;
    public static final int BYTES_PER_INSTRUCTION = INSTRUCTION_LENGTH / BYTE_LENGTH;

    private static final BitArray ZERO_BYTE = BitArray.of(0, BYTE_LENGTH);
    private static final BitArray ZERO_WORD = BitArray.of(0, WORD_LENGTH);

    private String pendinglabel;

    private Map<String, Integer> labelAddressMap = new HashMap<>();

    private int address = 0;

    private List<Assemblable> assemblableList = new ArrayList<>();

    private List<BitArray> assembly = new ArrayList<>();

    public void setPendingLabel(String label) throws ParserException {
        if (labelAddressMap.containsKey(label)) {
            throw new LabelAlreadyDefinedException("Label: " + label + ", old address: " + labelAddressMap.get(label)
                    + ", new address: " + address);
        }
        if (pendinglabel != null) {
            throw new MultiplePendingLabelException("Pending label: " + pendinglabel + ", new label: " + label);
        }
        pendinglabel = label;
    }

    private void addPendingLabelIfHas() {
        if (pendinglabel != null) {
            labelAddressMap.put(pendinglabel, address);
        }
    }

    public int getLabelAddress(String name) throws UndefinedLabelException {
        if (!labelAddressMap.containsKey(name)) {
            throw new UndefinedLabelException("Label name: " + name);
        }
        return labelAddressMap.get(name);
    }

    public int getLabelAddress(Label label) throws UndefinedLabelException {
        return getLabelAddress(label.getName());
    }

    public int getAddress() {
        return address;
    }

    private void allocateZero(int bytes) {
        address += bytes;
    }

    public void allocateSpace(int bytes) {
        addPendingLabelIfHas();
        allocateZero(bytes);
    }

    private int computePaddingForBytes(int bytes) {
        return address % bytes;
    }

    private void allocatePaddingForBytes(int bytes) {
        allocateZero(computePaddingForBytes(bytes));
    }

    public void allocateBytes(int bytes) {
        if (bytes <= 0) {
            throw new InternalException(new IllegalArgumentException("bytes <= 0: " + bytes));
        } else if (bytes > BYTES_PER_WORD) {
            throw new InternalException(new IllegalArgumentException("bytes > word: " + bytes));
        }
        allocatePaddingForBytes(bytes);
        addPendingLabelIfHas();
        address += bytes;
    }

    public void allocateByte() {
        allocateBytes(1);
    }

    public void allocateHalfWords(int halfWords) {
        allocateBytes(2 * halfWords);
    }

    public void allocateHalfWord() {
        allocateHalfWords(1);
    }

    public void allocateWords(int words) {
        allocateBytes(4 * words);
    }

    public void allocateWord() {
        allocateWords(1);
    }

    public void allocateBits(int bits) {
        if (bits % BYTE_LENGTH != 0) {
            throw new InternalException(new IllegalArgumentException("bits should be in bytes"));
        }
        allocateBytes(bits / BYTE_LENGTH);
    }

    private void checkBackwardAddress(int address) {
        if (address < this.address) {
            throw new BackwardAddressException("Current address: " + this.address + ", new address: " + address);
        }
    }

    public void allocateToAddress(int address) {
        checkBackwardAddress(address);
        allocateZero(address - this.address);
    }

    public void allocateToAddress(WordImmediate address) {
        allocateToAddress(address.getValue().value());
    }

    public void finishAllocation() throws ParserException {
        if (pendinglabel != null) {
            throw new PendingLabelException("Label: " + pendinglabel);
        }
        // Reset address for use in the next pass.
        address = 0;
    }

    public void appendAssemblable(Assemblable assemblable) {
        assemblableList.add(assemblable);
        assemblable.allocate(this);
    }

    public List<Assemblable> getAssemblableList() {
        return assemblableList;
    }

    private void writeZero(int bytes) {
        address += bytes;
        while (bytes > 0) {
            if (bytes > 4) {
                assembly.add(ZERO_WORD);
                bytes -= 4;
            } else {
                assembly.add(ZERO_BYTE);
                --bytes;
            }
        }
    }

    public void writeSpace(int bytes) {
        writeZero(bytes);
    }

    private void writePaddingForBytes(int bytes) {
        writeZero(computePaddingForBytes(bytes));
    }

    public void writeBytes(BitArray bitArray) {
        if (bitArray.length() % BYTE_LENGTH != 0) {
            throw new InternalException(new IllegalArgumentException("BitArray should be in bytes: " + bitArray));
        }
        int bytes = bitArray.length() / BYTE_LENGTH;
        writePaddingForBytes(bytes);
        // Make a copy so it will not be changed accidentally.
        // TODO: Is this necessary?
        assembly.add(BitArray.copyOf(bitArray));
    }

    public void writeToAddress(int address) {
        checkBackwardAddress(address);
        writeZero(address - this.address);
    }

    public void writeToAddress(WordImmediate address) {
        writeToAddress(address.getValue().value());
    }

    public List<BitArray> getAssembly() {
        return assembly;
    }
}
