/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.util;

/**
 * Index starts from right to left.
 */
public class BitArray {
    public static final int CAPACITY = Integer.SIZE;

    private int value;

    private int length;

    private BitArray(int value, int length) {
        if (lengthOf(value) > length) {
            throw new IllegalArgumentException("Value length > specified length, value: " + value + ", length: "
                    + length);
        }
        this.value = value;
        this.length = length;
    }

    /**
     * Initializes a {@code BitArray} with specified length.
     * @param length Length of this {@code BitArray}
     */
    public BitArray(int length) {
        this(0, length);
    }

    /**
     * Obtain a {@code BitArray} with specified value and length.
     * @param value Value for the new {@code BitArray}
     * @param length Length for the new {@code BitArray}
     * @return The new {@code BitArray}
     */
    public static BitArray of(int value, int length) {
        return new BitArray(value, length);
    }

    /**
     * Obtain a {@code BitArray} with specified boolean array as value.
     * @param value Value for the new {@code BitArray}
     * @return The new {@code BitArray}
     */
    public static BitArray of(boolean[] value) {
        if (value.length > CAPACITY) {
            throw new IllegalArgumentException("Boolean array length > " + CAPACITY + ": " + value.length);
        }
        BitArray bitArray = new BitArray(value.length);
        for (int i = 0; i < value.length; ++i) {
            if (value[i]) {
                bitArray.set(i);
            }
        }
        return bitArray;
    }

    /**
     * Obtain a {@code BitArray} with specified {@code BitArray}s concatenated together, left to right.
     * @param bitArrays Values for the new {@code BitArray}
     * @return The new {@code BitArray}
     */
    public static BitArray of(BitArray... bitArrays) {
        int value = 0;
        int size = 0;
        for (BitArray bitArray : bitArrays) {
            size += bitArray.length();
            if (size > CAPACITY) {
                throw new IllegalArgumentException("BitArray length sum > " + CAPACITY + ": " + size);
            }
            value <<= bitArray.length();
            value |= bitArray.value();
        }
        return new BitArray(value, size);
    }

    public static BitArray ofInt(int value, int length) {
        if (value >= 0 && lengthOf(value) > length) {
            throw new IllegalArgumentException("value length: " + lengthOf(value) + ">= specified length: " + length
                    + ", with value: " + value);
        } else if (value < 0 && lengthOf(~value) > length){
            throw new IllegalArgumentException("value length: " + lengthOf(~value) + ">= specified length: " + length
                    + ", with value: " + value);
        }
        value &= makeBitRange(length);
        return of(value, length);
    }

    public static BitArray copyOf(BitArray bitArray) {
        return new BitArray(bitArray.value, bitArray.length);
    }

    /**
     * Get the value of this {@code BitArray} as an int.
     * @return The value of this {@code BitArray}
     */
    public int value() {
        return value;
    }

    /**
     * Get the length of this {@code BitArray}.
     * @return The length of this {@code BitArray}
     */
    public int length() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public boolean get(int index) {
        checkOutOfBound(index);
        return (value & makeBit(index)) != 0;
    }

    public void set(int index) {
        checkOutOfBound(index);
        value |= makeBit(index);
    }

    public void set(int fromIndex, int toIndex) {
        checkRange(fromIndex, toIndex);
        value |= makeBitRange(fromIndex, toIndex);
    }

    public void clear(int index) {
        checkOutOfBound(index);
        value &= ~makeBit(index);
    }

    public void clear(int fromIndex, int toIndex) {
        checkRange(fromIndex, toIndex);
        value &= ~makeBitRange(fromIndex, toIndex);
    }

    public void flip(int index) {
        checkOutOfBound(index);
        value ^= makeBit(index);
    }

    public void flip(int fromIndex, int toIndex) {
        checkRange(fromIndex, toIndex);
        value ^= makeBitRange(fromIndex, toIndex);
    }

    public void setTo(int index, boolean value) {
        if (value) {
            set(index);
        } else {
            clear(index);
        }
    }

    public void setTo(int fromIndex, int toIndex, boolean value) {
        if (value) {
            set(fromIndex, toIndex);
        } else {
            clear(fromIndex, toIndex);
        }
    }

    public void setTo(int fromIndex, int value, int length) {
        if (lengthOf(value) > length) {
            throw new IllegalArgumentException("value :" + value + "length > specified length: " + length);
        }
        int toIndex = fromIndex + length;
        checkRange(fromIndex, toIndex);
        this.value &= ~makeBitRange(fromIndex, toIndex);
        this.value |= value << fromIndex;
    }

    /**
     * Set the value as {@param value}. The length of this {@code BitArray} will not change.
     * @param value The value
     */
    public void setTo(int value) {
        if (lengthOf(value) > length) {
            throw new IllegalArgumentException("value length > " + length + ": " + value);
        }
        this.value = value;
    }

    public void setTo(int fromIndex, BitArray bitArray) {
        setTo(fromIndex, bitArray.value(), bitArray.length());
    }

    /**
     * Set the value as {@param bitArray}. The length of this {@code BitArray} will not change.
     * @param bitArray The bit array for value
     */
    public void setTo(BitArray bitArray) {
        setTo(bitArray.value());
    }

    public void setAs(BitArray bitArray) {
        value = bitArray.value();
        length = bitArray.length();
    }

    @Override
    public boolean equals(Object object) {

        if (object == this) {
            return true;
        } else if (object == null || getClass() != object.getClass()) {
            return false;
        }

        BitArray bitArray = (BitArray) object;
        if (bitArray.value != value) {
            return false;
        } else if (bitArray.length != length) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = value;
        result = 31 * result + length;
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(2 + length)
                .append("0b");
        for (int i = length - 1; i >= 0; --i) {
            builder.append(get(i) ? '1' : '0');
        }
        return builder.toString();
    }

    public static int lengthOf(int value) {
        int length = 0;
        do {
            value >>>= 1;
            ++length;
        } while (value != 0);
        return length;
    }

    private void checkOutOfBound(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("index: " + index + " < 0");
        } else if (index >= length) {
            throw new IndexOutOfBoundsException("index: " + index + " >= length: " + length);
        }
    }

    private void checkRange(int fromIndex, int toIndex) {
        if (fromIndex < 0) {
            throw new IndexOutOfBoundsException("fromIndex: " + fromIndex + " < 0");
        } else if (fromIndex > length) {
            throw new IndexOutOfBoundsException("fromIndex: " + fromIndex + " > length: " + length);
        }
        if (toIndex < 0) {
            throw new IndexOutOfBoundsException("toIndex: " + toIndex + " < 0");
        } else if (toIndex > length) {
            throw new IndexOutOfBoundsException("toIndex: " + toIndex + " > length: " + length);
        }
        if (fromIndex > toIndex) {
            throw new IndexOutOfBoundsException("fromIndex: " + fromIndex + " > toIndex: " + toIndex);
        }
    }

    private static int makeBit(int index) {
        return 1 << index;
    }

    private static int makeBitRange(int fromIndex, int toIndex) {
        return ((~0) >>> (CAPACITY - (toIndex - fromIndex))) << fromIndex;
    }

    private static int makeBitRange(int length) {
        return makeBitRange(0, length);
    }
}
