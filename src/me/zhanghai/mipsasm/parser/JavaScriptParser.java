/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.parser;

import me.zhanghai.mipsasm.util.IoUtils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class JavaScriptParser {

    private static final ScriptEngine JAVA_SCRIPT_ENGINE = new ScriptEngineManager().getEngineByName("JavaScript");

    private JavaScriptParser() {}

    public static Object eval(String script) throws ScriptException {
        return JAVA_SCRIPT_ENGINE.eval(script);
    }

    private static String evalForInteger(String script) throws ScriptException {
        Object result = eval(script);
        // Double is default return type for Number.
        if (result instanceof Double && result.equals(Math.rint((Double) result))) {
            // Java's signed int will make 0x7FFFFFFF for values larger than that if we use Double.intValue().
            result = ((Double) result).longValue();
        }
        return result.toString();
    }

    public static int parseSignedInteger(String expression) throws ScriptException {
        try {
            return IoUtils.parseSignedInteger(expression);
        } catch (NumberFormatException e) {
            expression = evalForInteger(expression);
            return IoUtils.parseSignedInteger(expression);
        }
    }

    public static int parseUnsignedInteger(String expression) throws ScriptException {
        try {
            return IoUtils.parseUnsignedInteger(expression);
        } catch (NumberFormatException e) {
            expression = evalForInteger(expression);
            return IoUtils.parseUnsignedInteger(expression);
        }
    }

    public static int parseInteger(String expression) throws ScriptException {
        try {
            return IoUtils.parseInteger(expression);
        } catch (NumberFormatException e) {
            expression = evalForInteger(expression);
            return IoUtils.parseInteger(expression);
        }
    }
}
