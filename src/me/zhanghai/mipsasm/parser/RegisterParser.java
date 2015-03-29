/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.parser;

import me.zhanghai.mipsasm.instruction.Register;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterParser {

    private static final Pattern REGISTER_PATTERN = Pattern.compile(
            "\\$(?:([0-9a-zA-Z]+)|(\\d+))");

    private static final ThreadLocal<Matcher> REGISTER_MATCHER = new ThreadLocal<Matcher>() {
        @Override
        protected Matcher initialValue() {
            return REGISTER_PATTERN.matcher("");
        }
    };


    private RegisterParser() {}

    public static Register parse(String registerString) {

        registerString = registerString.trim();

        Matcher matcher = REGISTER_MATCHER.get();
        matcher.reset(registerString);
        if (!matcher.matches()) {
            throw new InvalidOperandException("Cannot parse register: " + registerString);
        }

        String registerName = matcher.group(1);
        if (registerName != null) {
            try {
                return Register.valueOf(registerName);
            } catch (IllegalArgumentException e) {
                throw new InvalidOperandException("Invalid register name: " + registerName, e);
            }
        }

        String registerIndexString = matcher.group(2);
        if (registerIndexString != null) {
            try {
                return Register.values()[Integer.parseInt(registerIndexString)];
            } catch (NumberFormatException e) {
                throw new InvalidOperandException("Register index cannot be parsed: " + registerIndexString, e);
            } catch (IndexOutOfBoundsException e) {
                throw new InvalidOperandException("Register index out of bounds: " + registerIndexString, e);
            }
        }

        throw new InvalidOperandException("Cannot parse register: " + registerString);
    }
}
