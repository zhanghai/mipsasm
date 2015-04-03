/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.parser;

import me.zhanghai.mipsasm.assembler.Register;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterParser {

    private static final Pattern PATTERN = Pattern.compile(
            "\\$(\\S+)");

    private static final ThreadLocal<Matcher> MATCHER = new ThreadLocal<Matcher>() {
        @Override
        protected Matcher initialValue() {
            return PATTERN.matcher("");
        }
    };


    private RegisterParser() {}

    public static Register parse(String registerString) throws ParserException {

        Matcher matcher = MATCHER.get().reset(registerString);
        if (!matcher.matches()) {
            throw new IllegalOperandException("Cannot parse register: " + registerString);
        }

        String registerName = matcher.group(1);
        try {
            return Register.values()[Integer.parseInt(registerName)];
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalOperandException("Register index out of bounds: " + registerName, e);
        } catch (NumberFormatException ignored) {
            try {
                return Register.valueOf(registerName.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalOperandException("Invalid register name: " + registerName, e);
            }
        }
    }
}
