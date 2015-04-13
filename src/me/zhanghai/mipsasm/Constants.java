/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm;

public class Constants {

    public static final int BYTE_LENGTH = 8;
    public static final int HALF_WORD_LENGTH = 16;
    public static final int WORD_LENGTH = 32;
    public static final int ADDRESS_LENGTH = WORD_LENGTH;
    public static final int INSTRUCTION_LENGTH = WORD_LENGTH;
    public static final int BYTES_PER_HALF_WORD = HALF_WORD_LENGTH / BYTE_LENGTH;
    public static final int BYTES_PER_WORD = WORD_LENGTH / BYTE_LENGTH;
    public static final int BYTES_PER_INSTRUCTION = INSTRUCTION_LENGTH / BYTE_LENGTH;
}
