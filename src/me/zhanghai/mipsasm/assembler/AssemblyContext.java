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

    public static final int BYTE_LENGTH = 8;
    public static final int HALF_WORD_LENGTH = 16;
    public static final int WORD_LENGTH = 32;
    public static final int ADDRESS_LENGTH = WORD_LENGTH;
    public static final int BYTES_IN_WORD = WORD_LENGTH / BYTE_LENGTH;

    private int address = 0;

    private Map<String, Integer> labelAddressMap = new HashMap<>();

    private List<Assemblable> assemblableList = new ArrayList<>();

    private List<BitArray> assembly = new ArrayList<>();

    public void resetAddress() {
        address = 0;
    }

    public void advanceToAddress(int address) {
        if (address < this.address) {
            throw new BackwardAddressException("Current address: " + this.address + ", new address: " + address);
        }
        this.address = address;
    }

    public void advanceToAddress(WordImmediate address) {
        advanceToAddress(address.getValue().value());
    }

    public void advanceByBits(int bits) {
        if (bits % 8 != 0) {
            throw new IllegalArgumentException("bits should be in bytes");
        }
        advanceByBytes(bits / 8);
    }

    public void advanceByBytes(int bytes) {
        if (bytes < 0) {
            throw new IllegalArgumentException("Cannot advance backward: " + bytes);
        }
        address += bytes;
    }

    public void advanceByByte() {
        advanceByBytes(1);
    }

    public void advanceByHalfWords(int halfWords) {
        advanceByBytes(2);
    }

    public void advanceByHalfWord() {
        advanceByHalfWords(1);
    }

    public void advanceByWords(int words) {
        advanceByBytes(4 * words);
    }

    public void advanceByWord() {
        advanceByWords(1);
    }

    public int getAddress() {
        return address;
    }

    public void addLabel(String name) throws ParserException {
        if (labelAddressMap.containsKey(name)) {
            throw new LabelAlreadyDefinedException("Label name: " + name + ", old address: " + labelAddressMap.get(name)
                    + ", new address: " + address);
        }
        labelAddressMap.put(name, address);
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

    public void appendAssemblable(Assemblable assemblable) {
        assemblableList.add(assemblable);
        assemblable.locate(this);
    }

    public List<Assemblable> getAssemblableList() {
        return assemblableList;
    }

    // NOTE: address is automatically advanced.
    public void appendAssemblyByByte(BitArray theByte) {
        if (theByte.length() != BYTE_LENGTH) {
            throw new IllegalArgumentException("bitArray is not a byte: " + theByte.length());
        }
        boolean appended = false;
        if (assembly.size() > 0) {
            BitArray last = assembly.get(assembly.size() - 1);
            if (last.length() <= BYTE_LENGTH) {
                last.setLength(2 * BYTE_LENGTH);
                last.setTo(BYTE_LENGTH, theByte);
                appended = true;
            } else if (last.length() <= 2 * BYTE_LENGTH) {
                last.setLength(3 * BYTE_LENGTH);
                last.setTo(2 * BYTE_LENGTH, theByte);
                appended = true;
            } else if (last.length() <= 3 * BYTE_LENGTH) {
                last.setLength(4 * BYTE_LENGTH);
                last.setTo(3 * BYTE_LENGTH, theByte);
                appended = true;
            } else {
                last.setLength(WORD_LENGTH);
            }
        }
        if (!appended) {
            assembly.add(BitArray.copyOf(theByte));
        }
        advanceByByte();
    }

    // NOTE: offset is automatically incremented.
    public void appendAssemblyByHalfWord(BitArray halfWord) {
        if (halfWord.length() != HALF_WORD_LENGTH) {
            throw new IllegalArgumentException("bitArray is not a half word: " + halfWord.length());
        }
        boolean appended = false;
        if (assembly.size() > 0) {
            BitArray last = assembly.get(assembly.size() - 1);
            if (last.length() <= HALF_WORD_LENGTH) {
                last.setLength(2 * HALF_WORD_LENGTH);
                last.setTo(HALF_WORD_LENGTH, halfWord);
                appended = true;
            } else {
                last.setLength(WORD_LENGTH);
            }
        }
        if (!appended) {
            assembly.add(BitArray.copyOf(halfWord));
        }
        advanceByHalfWord();
    }

    // NOTE: offset is automatically incremented.
    public void appendAssemblyByWord(BitArray word) {
        if (word.length() != 32) {
            throw new IllegalArgumentException("bitArray is not a word: " + word.length());
        }
        if (assembly.size() > 0) {
            assembly.get(assembly.size() - 1).setLength(WORD_LENGTH);
        }
        assembly.add(BitArray.copyOf(word));
        advanceByWord();
    }

    public void appendAssembly(BitArray bitArray) {
        switch (bitArray.length()) {
            case BYTE_LENGTH:
                appendAssemblyByByte(bitArray);
                break;
            case HALF_WORD_LENGTH:
                appendAssemblyByHalfWord(bitArray);
                break;
            case WORD_LENGTH:
                appendAssemblyByWord(bitArray);
                break;
            default:
                throw new IllegalArgumentException("Illegal assembly length: " + bitArray.length());
        }
    }

    public void appendAssemblyByZeroBytes(int bytes) {
        for (; bytes > 0; --bytes) {
            appendAssemblyByByte(BitArray.of(0, BYTE_LENGTH));
        }
    }

    public void appendAssemblyByZeroToAddress(int address) {
        appendAssemblyByZeroBytes(address - this.address);
    }

    public void appendAssemblyByZeroToAddress(WordImmediate address) {
        appendAssemblyByZeroToAddress(address.getValue().value());
    }

    public List<BitArray> getAssembly() {
        return assembly;
    }
}
