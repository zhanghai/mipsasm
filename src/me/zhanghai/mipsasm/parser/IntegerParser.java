/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.parser;

import me.zhanghai.mipsasm.util.IoUtils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class IntegerParser {

    private static final ScriptEngine JAVA_SCRIPT_ENGINE = new ScriptEngineManager().getEngineByName("JavaScript");

    private IntegerParser() {}

    private static String eval(String script) throws ScriptException {
        Object result = JAVA_SCRIPT_ENGINE.eval(script);
        // Double is default return type for Number.
        if (result instanceof Double && result.equals(Math.rint((Double) result))) {
            result = ((Double) result).intValue();
        }
        return result.toString();
    }

    public static int parseSignedInteger(String expression) throws ScriptException {
        try {
            return IoUtils.parseSignedInteger(expression);
        } catch (NumberFormatException e) {
            expression = eval(expression);
            return IoUtils.parseSignedInteger(expression);
        }
    }

    public static int parseUnsignedInteger(String expression) throws ScriptException {
        try {
            return IoUtils.parseUnsignedInteger(expression);
        } catch (NumberFormatException e) {
            expression = eval(expression);
            return IoUtils.parseUnsignedInteger(expression);
        }
    }

    public static int parseInteger(String expression) throws ScriptException {
        try {
            return IoUtils.parseInteger(expression);
        } catch (NumberFormatException e) {
            expression = eval(expression);
            return IoUtils.parseInteger(expression);
        }
    }
}
