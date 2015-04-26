/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.parser;

public class Tokens {

    private Tokens() {}

    public static final String COMMENT_REGEX = "(?:#.*)|(?://.*)|(?:/\\*.*?\\*/)";

    public static final String IDENTIFIER_REGEX = "[a-zA-Z._$][0-9a-zA-Z._$]*";

    public static final String STATEMENT_SEPARATOR_REGEX = ";";

    public static final String OPERAND_SEPARATOR_REGEX = ",";

    public static final String STRING_QUOTATION = "\"";
}
