/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.util;

public class BitArray {

    public static final int CAPACITY = Integer.SIZE;

    private int value;

    private int length;

    private BitArray(int value, int length) {
        // FIXME: DEBUG
        if (lengthOf(value) > length) {
            throw new IllegalArgumentException("Value length > specified length, value: " + value + ", length: "
                    + length);
        }
        value = value & makeBitRange(length);
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
     * Obtain a {@code BitArray} with specified {@code BitArray}s concatenated together.
     * @param bitArrays Values for the new {@code BitArray}
     * @return The new {@code BitArray}
     */
    public static BitArray of(BitArray... bitArrays) {
        int value = 0;
        int size = 0;
        for (BitArray bitArray : bitArrays) {
            value |= bitArray.value() >>> size;
            size += bitArray.length();
        }
        if (size > CAPACITY) {
            throw new IllegalArgumentException("BitArray length sum > " + CAPACITY + ": " + size);
        }
        return new BitArray(value, size);
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

    public boolean get(int index) {
        checkOutOfBound(index);
        return (value & makeBit(index)) == 1;
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
        checkRange(fromIndex, fromIndex + length);
        int mask = makeBitRange(fromIndex, fromIndex + length);
        value = (value >>> fromIndex) & mask;
        this.value &= ~mask;
        this.value |= value;
    }

    /**
     * Set the value as {@param value}. The length of this {@code BitArray} will remain unchanged.
     * @param value The value
     */
    public void setTo(int value) {
        value = value & makeBitRange(length);
        this.value = value;
    }

    public void setTo(int fromIndex, BitArray bitArray) {
        setTo(fromIndex, bitArray.value(), bitArray.length());
    }

    /**
     * Set the value as {@param bitArray}. The length of this {@code BitArray} will remain unchanged.
     * @param bitArray The bit array for value
     */
    public void setTo(BitArray bitArray) {
        setTo(bitArray.value());
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
        for (int i = 0; i < length; ++i) {
            builder.append(get(i) ? '1' : '0');
        }
        return builder.toString();
    }

    public static int lengthOf(int value) {
        int length = 0;
        do {
            value /= 2;
            ++length;
        } while (value > 0);
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
        return ((~0) << (CAPACITY - (toIndex - fromIndex))) >>> fromIndex;
    }

    private static int makeBitRange(int length) {
        return makeBitRange(0, length);
    }
}
