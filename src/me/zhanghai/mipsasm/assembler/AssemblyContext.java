/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.assembler;

import me.zhanghai.mipsasm.Constants;
import me.zhanghai.mipsasm.InternalException;
import me.zhanghai.mipsasm.parser.LabelAlreadyDefinedException;
import me.zhanghai.mipsasm.parser.MultiplePendingLabelException;
import me.zhanghai.mipsasm.parser.ParserException;
import me.zhanghai.mipsasm.parser.PendingLabelException;
import me.zhanghai.mipsasm.util.BitArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AssemblyContext {

    private static final BitArray ZERO_BYTE = BitArray.of(0, Constants.BYTE_LENGTH);
    private static final BitArray ZERO_WORD = BitArray.of(0, Constants.WORD_LENGTH);

    private static final BitArray EMPTY_BIT_ARRAY = BitArray.ofEmpty();

    private String pendingLabel;

    private Map<String, Integer> labelAddressMap = new HashMap<>();

    private int address = 0;

    private List<Assemblable> assemblableList = new ArrayList<>();

    private List<BitArray> assembly = new ArrayList<>();

    public void setPendingLabel(String label) throws ParserException {
        if (labelAddressMap.containsKey(label)) {
            throw new LabelAlreadyDefinedException("Label: " + label + ", old address: " + labelAddressMap.get(label)
                    + ", new address: " + address);
        }
        if (pendingLabel != null) {
            throw new MultiplePendingLabelException("Pending label: " + pendingLabel + ", new label: " + label);
        }
        pendingLabel = label;
    }

    private void addPendingLabelIfHas() {
        if (pendingLabel != null) {
            labelAddressMap.put(pendingLabel, address);
            pendingLabel = null;
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
        if (bytes > Constants.BYTES_PER_WORD) {
            if (bytes % Constants.BYTES_PER_WORD == 0) {
                bytes = Constants.BYTES_PER_WORD;
            } else {
                throw new InternalException(new IllegalArgumentException("bytes > word and not word aligned, bytes: "
                        + bytes));
            }
        }
        return address % bytes;
    }

    private void allocatePaddingForBytes(int bytes) {
        allocateZero(computePaddingForBytes(bytes));
    }

    public void allocateBytes(int bytes) {
        if (bytes <= 0) {
            throw new InternalException(new IllegalArgumentException("bytes <= 0: " + bytes));
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
        if (bits % Constants.BYTE_LENGTH != 0) {
            throw new InternalException(new IllegalArgumentException("bits should be in bytes"));
        }
        allocateBytes(bits / Constants.BYTE_LENGTH);
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
        if (pendingLabel != null) {
            throw new PendingLabelException("Label: " + pendingLabel);
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
            // For word alignment.
            if (bytes >= 4 && address % Constants.BYTES_PER_WORD == 0) {
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
        if (bitArray.length() % Constants.BYTE_LENGTH != 0) {
            throw new InternalException(new IllegalArgumentException("BitArray should be in bytes: " + bitArray));
        }
        int bytes = bitArray.length() / Constants.BYTE_LENGTH;
        writePaddingForBytes(bytes);
        address += bytes;
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

    public void packAssemblyToWords() {
        List<BitArray> packedAssembly = new ArrayList<>();
        BitArray current = EMPTY_BIT_ARRAY;
        for (BitArray bitArray : assembly) {
            if (current.length() < Constants.WORD_LENGTH) {
                current = BitArray.of(current, bitArray);
            }
            if (current.length() == Constants.WORD_LENGTH) {
                packedAssembly.add(current);
                current = EMPTY_BIT_ARRAY;
            }
            if (current.length() > Constants.WORD_LENGTH) {
                throw new InternalException(new IllegalStateException("Word alignment failed"));
            }
        }
        if (current.length() > 0) {
            // Found remainder smaller than a word.
            current = BitArray.of(current, BitArray.ofLength(Constants.WORD_LENGTH - current.length()));
            packedAssembly.add(current);
        }
        assembly = packedAssembly;
    }

    public List<BitArray> getAssembly() {
        return assembly;
    }
}
