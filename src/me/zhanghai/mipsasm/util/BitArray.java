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
        this.value = value;
        this.length = length;
    }

    public static BitArray ofEmpty() {
        return new BitArray(0, 0);
    }

    public static BitArray ofValue(int value) {
        return of(value, CAPACITY);
    }

    public static BitArray ofLength(int length) {
        return of(0, length);
    }

    /**
     * Obtain a {@code BitArray} with specified value and length.
     * @param value Value for the new {@code BitArray}
     * @param length Length for the new {@code BitArray}
     * @return The new {@code BitArray}
     */
    public static BitArray of(int value, int length) {
        if (lengthOf(value) > length) {
            throw new IllegalArgumentException("Value length > specified length, value: " + value + ", length: "
                    + length);
        } else if (length > CAPACITY) {
            throw new IllegalArgumentException("Length > capacity, " + "length: " + length + ", capacity: " + CAPACITY);
        }
        return new BitArray(value, length);
    }

    public static BitArray of(int value, int fromIndex, int toIndex) {
        return of(value, toIndex)
                .rightShift(fromIndex)
                .setLength(toIndex - fromIndex);
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
        BitArray bitArray = ofLength(value.length);
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
        return of(value, size);
    }

    public static BitArray ofInteger(int value, int length) {
        if (value >= 0 && lengthOf(value) > length) {
            throw new IllegalArgumentException("value length > specified length, value: " + value + ", value length: "
                    + lengthOf(value) + ", specified length: " + length);
        } else if (value < 0 && lengthOf(~value) + 1 > length){
            throw new IllegalArgumentException("value length > specified length, value: " + value + ", value length: "
                    + (lengthOf(~value) + 1) + ", specified length: " + length);
        }
        value &= makeBitRange(length);
        return of(value, length);
    }

    public static BitArray copyOf(BitArray bitArray) {
        return of(bitArray.value, bitArray.length);
    }

    /**
     * Get the value of this {@code BitArray} as an int.
     * @return The value of this {@code BitArray}
     */
    public int value() {
        return value;
    }

    public int integerValue() {
        if (get(length - 1)) {
            return makeBitRange(length, CAPACITY) | value;
        } else {
            return value;
        }
    }

    /**
     * Get the length of this {@code BitArray}.
     * @return The length of this {@code BitArray}
     */
    public int length() {
        return length;
    }

    /**
     * Set the length of this {@code BitArray}. Will truncate the value if length is shorten.
     * @param length The desired length
     * @return this, for chaining.
     */
    public BitArray setLength(int length) {
        if (length < 0) {
            throw new IllegalArgumentException("Length < 0: " + length);
       } else if (length > CAPACITY) {
            throw new IllegalArgumentException("Length: " + length + " is greater than capacity :" + CAPACITY);
        }
        this.length = length;
        trimValue();
        return this;
    }

    public boolean get(int index) {
        checkOutOfBound(index);
        return (value & makeBit(index)) != 0;
    }

    public BitArray set(int index) {
        checkOutOfBound(index);
        value |= makeBit(index);
        return this;
    }

    public BitArray set(int fromIndex, int toIndex) {
        checkRange(fromIndex, toIndex);
        value |= makeBitRange(fromIndex, toIndex);
        return this;
    }

    public BitArray clear(int index) {
        checkOutOfBound(index);
        value &= ~makeBit(index);
        return this;
    }

    public BitArray clear(int fromIndex, int toIndex) {
        checkRange(fromIndex, toIndex);
        value &= ~makeBitRange(fromIndex, toIndex);
        return this;
    }

    public BitArray flip(int index) {
        checkOutOfBound(index);
        value ^= makeBit(index);
        return this;
    }

    public BitArray flip(int fromIndex, int toIndex) {
        checkRange(fromIndex, toIndex);
        value ^= makeBitRange(fromIndex, toIndex);
        return this;
    }

    public BitArray leftShift(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount < 0: " + amount);
        }
        value <<= amount;
        trimValue();
        return this;
    }

    public BitArray rightShift(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount < 0: " + amount);
        }
        value >>>= amount;
        return this;
    }

    public BitArray subArray(int fromIndex, int toIndex) {
        checkRange(fromIndex, toIndex);
        return BitArray.copyOf(this).rightShift(fromIndex).setLength(toIndex - fromIndex);
    }

    public BitArray subArray(int fromIndex) {
        return subArray(fromIndex, length);
    }

    public BitArray setTo(int index, boolean value) {
        if (value) {
            set(index);
        } else {
            clear(index);
        }
        return this;
    }

    public BitArray setTo(int fromIndex, int toIndex, boolean value) {
        if (value) {
            set(fromIndex, toIndex);
        } else {
            clear(fromIndex, toIndex);
        }
        return this;
    }

    public BitArray setTo(int fromIndex, int value, int length) {
        if (lengthOf(value) > length) {
            throw new IllegalArgumentException("value :" + value + "length > specified length: " + length);
        }
        int toIndex = fromIndex + length;
        checkRange(fromIndex, toIndex);
        this.value &= ~makeBitRange(fromIndex, toIndex);
        this.value |= value << fromIndex;
        return this;
    }

    /**
     * Set the value as {@param value}. The length of this {@code BitArray} will not change.
     * @param value The value
     * @return this, for chaining.
     */
    public BitArray setTo(int value) {
        if (lengthOf(value) > length) {
            throw new IllegalArgumentException("value length > " + length + ": " + value);
        }
        this.value = value;
        return this;
    }

    public BitArray setTo(int fromIndex, BitArray bitArray) {
        return setTo(fromIndex, bitArray.value(), bitArray.length());
    }

    /**
     * Set the value as {@param bitArray}. The length of this {@code BitArray} will not change.
     * @param bitArray The bit array for value
     * @return this, for chaining.
     */
    public BitArray setTo(BitArray bitArray) {
        return setTo(bitArray.value());
    }

    public BitArray setAs(BitArray bitArray) {
        value = bitArray.value();
        length = bitArray.length();
        return this;
    }

    public boolean isZero() {
        return value == 0;
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

    public String toOctalString() {
        return "0" + Integer.toOctalString(value);
    }

    public String toDecimalString() {
        return Integer.toString(value);
    }

    public String toHexString() {
        return "0x" + Integer.toHexString(value).toUpperCase();
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

    private void trimValue() {
        // clear() checks range.
        value &= ~makeBitRange(length, CAPACITY);
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
