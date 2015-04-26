/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.util;

import org.apache.commons.lang3.text.translate.AggregateTranslator;
import org.apache.commons.lang3.text.translate.CharSequenceTranslator;
import org.apache.commons.lang3.text.translate.LookupTranslator;
import org.apache.commons.lang3.text.translate.OctalUnescaper;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    private static final CharSequenceTranslator UNESCAPE_MIPS = new AggregateTranslator(
            new OctalUnescaper(),
            new HexUnescaper(),
            new LookupTranslator(new String[][] {
                    {"\\a", "\u0007"},
                    {"\\b", "\b"},
                    {"\\f", "\f"},
                    {"\\n", "\n"},
                    {"\\r", "\r"},
                    {"\\t", "\t"},
                    {"\\v", "\u000B"},
                    {"\\\\", "\\"},
                    {"\\\"", "\""},
                    {"\\\'", "\'"},
            })
    );

    private StringUtils() {}

    public static String camelCaseToPhrase(String camelCase) {
        if (isEmpty(camelCase)) {
            return "";
        }
        String camelCaseSpaced = camelCase.replaceAll("([^A-Z])([A-Z])", "$1 $2");
        return camelCaseSpaced.charAt(0) + camelCaseSpaced.substring(1).toLowerCase();
    }

    public static String unescapeMips(String string) {
        return UNESCAPE_MIPS.translate(string);
    }

    public static boolean isEmpty(String string) {
        return string == null || string.isEmpty();
    }

    public static int occurrencesOf(String string, String subString) {
        int lastIndex = 0;
        int occurrences = 0;
        while (true) {
            lastIndex = string.indexOf(subString, lastIndex);
            if (lastIndex == -1) {
                break;
            } else {
                ++occurrences;
                lastIndex += subString.length();
            }
        }
        return occurrences;
    }

    public static Matcher prepareMatcherForSplit(String regex, String[] quotesRegex, String[] leftBracketsRegex,
                                                 String[] rightBracketsRegex) {

        // Prepare all delimiters.
        String[] delimiters = new String[1 + quotesRegex.length + leftBracketsRegex.length + rightBracketsRegex.length];
        delimiters[0] = regex;
        System.arraycopy(quotesRegex, 0, delimiters, 1, quotesRegex.length);
        System.arraycopy(leftBracketsRegex, 0, delimiters, 1 + quotesRegex.length, leftBracketsRegex.length);
        System.arraycopy(rightBracketsRegex, 0, delimiters, 1 + quotesRegex.length + leftBracketsRegex.length,
                rightBracketsRegex.length);

        // Build delimiter matcher.
        StringBuilder delimitersRegexBuilder = new StringBuilder("(?:");
        boolean first = true;
        for (String delimiter : delimiters) {
            if (delimiter.endsWith("\\") && !delimiter.endsWith("\\\\")) {
                throw new IllegalArgumentException("Delimiter contains trailing single \\: " + delimiter);
            }
            if (first) {
                first = false;
            } else {
                delimitersRegexBuilder.append("|");
            }
            delimitersRegexBuilder
                    .append("(")
                    .append(delimiter)
                    .append(")");
        }
        delimitersRegexBuilder.append(")");
        String delimitersRegex = delimitersRegexBuilder.toString();
        Matcher matcher = Pattern.compile(delimitersRegex).matcher("");

        return matcher;
    }

    public static String[] split(String string, String regex, String[] quotesRegex, String[] leftBracketsRegex,
                                 String[] rightBracketsRegex, Matcher delimiterMatcher) {

        if (leftBracketsRegex.length != rightBracketsRegex.length) {
            throw new IllegalArgumentException("Bracket count mismatch, left: " + leftBracketsRegex.length + ", right: "
                    + rightBracketsRegex.length);
        }

        if (delimiterMatcher == null) {
            delimiterMatcher = prepareMatcherForSplit(regex, quotesRegex, leftBracketsRegex, rightBracketsRegex);
        }
        delimiterMatcher.reset(string);

        // Scan.
        int pendingQuoteIndex = -1;
        Deque<Integer> bracketStack = new LinkedList<>();
        StringBuilder pendingSegmentBuilder = new StringBuilder();
        List<String> segmentList = new ArrayList<>();
        int matcherIndex = 0;
        while (delimiterMatcher.find()) {
            pendingSegmentBuilder.append(string.substring(matcherIndex, delimiterMatcher.start()));
            int delimiterIndex = -1;
            for (int i = 1; i <= delimiterMatcher.groupCount(); ++i) {
                if (delimiterMatcher.group(i) != null) {
                    delimiterIndex = i - 1;
                    break;
                }
            }
            if (delimiterIndex < 1) {
                // Regex.
                if (pendingQuoteIndex == -1 && bracketStack.isEmpty()) {
                    segmentList.add(pendingSegmentBuilder.toString());
                    pendingSegmentBuilder.setLength(0);
                } else {
                    pendingSegmentBuilder.append(delimiterMatcher.group());
                }
            } else {
                delimiterIndex -= 1;
                pendingSegmentBuilder.append(delimiterMatcher.group());
                if (delimiterIndex < quotesRegex.length) {
                    // Quote.
                    if (pendingQuoteIndex == -1) {
                        pendingQuoteIndex = delimiterIndex;
                    } else if (pendingQuoteIndex == delimiterIndex) {
                        pendingQuoteIndex = -1;
                    }
                    // Ignore unpaired quotes.
                } else if (pendingQuoteIndex == -1) {
                    delimiterIndex -= quotesRegex.length;
                    if (delimiterIndex < leftBracketsRegex.length) {
                        // Left bracket
                        bracketStack.push(delimiterIndex);
                    } else {
                        delimiterIndex -= leftBracketsRegex.length;
                        // Right bracket
                        int topBracket = bracketStack.peek();
                        // Ignore unbalanced brackets.
                        if (delimiterIndex == topBracket) {
                            bracketStack.pop();
                        }
                    }
                }
            }
            matcherIndex = delimiterMatcher.end();
        }
        pendingSegmentBuilder.append(string.substring(matcherIndex, string.length()));
        segmentList.add(pendingSegmentBuilder.toString());

        int resultSize = segmentList.size();
        while (resultSize > 0 && segmentList.get(resultSize - 1).isEmpty()) {
            --resultSize;
        }
        String result[] = new String[resultSize];
        return segmentList.subList(0, resultSize).toArray(result);
    }

    public static String[] split(String string, String regex, String[] quotesRegex, String[] leftBracketsRegex,
                                     String[] rightBracketsRegex) {
        return split(string, regex, quotesRegex, leftBracketsRegex, rightBracketsRegex, null);
    }
}
