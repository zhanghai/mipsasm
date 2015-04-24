/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.parser;

import me.zhanghai.mipsasm.util.IoUtils;
import me.zhanghai.mipsasm.util.RegexUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqsMigrator {

    // RegexMigrator is case insensitive.
    private static final Migrator[] MIGRATORS = new Migrator[] {
            new RegexMigrator("#baseAddr", ".text"),
            new ColonedDirectiveMigrator("#DataAddre", "data"),
            new RegexMigrator("\\bdb\\b", ".byte"),
            new RegexMigrator("\\bdw\\b", ".half"),
            new RegexMigrator("\\bdd\\b", ".word"),
            new RegexMigrator("\\bresb\\b", ".space"),
            new RegexMigrator("\\bRESW\\b", ".space 2 *"),
            new RegexMigrator("\\bRESD\\b", ".space 4 *"),
            new MultiplicationMigrator(),
            new RegexMigrator("(?<!(?://.{0,255})|(?:\\$))(?=\\b[0-9a-f]+\\b)", "0x"),
            new RegexMigrator("(?<!(?://.{0,255})|(?:\\$))0x(?=add\\b)", ""),
            new RegexMigrator("\\$?r(?=\\d{1,2})", "\\$"),
            new RegexMigrator(" *, *", ", "),
            new RegexMigrator(":(?=\\S)", ":\t"),
            new RegexMigrator(";(?=\\S)", ";\t"),
            new RegexMigrator(";(\\s*)//", "$1//"),
            new RegexMigrator("//(?=\\S)", "// "),
            new RegexMigrator("(?m);$", ""),
            new RegexMigrator("(?m)[ \t]+$", ""),
            new RegexMigrator("\\s+$", System.lineSeparator())
    };

    public static String migrate(String text) throws MigratorException {
        for (Migrator migrator : MIGRATORS) {
            text = migrator.migrate(text);
        }
        return text;
    }

    private interface Migrator {
        String migrate(String text) throws MigratorException;
    }

    private static class RegexMigrator implements Migrator {

        private ThreadLocal<Matcher> matcher;
        private String replacement;

        public RegexMigrator(String regex, String replacement) {
            this.matcher = RegexUtils.makeThreadLocalMatcher(regex, Pattern.CASE_INSENSITIVE);
            this.replacement = replacement;
        }

        @Override
        public String migrate(String text) {
            return matcher.get().reset(text).replaceAll(replacement);
        }
    }

    private static class ColonedDirectiveMigrator implements Migrator {

        private RegexMigrator migrator1, migrator2;

        protected ColonedDirectiveMigrator(RegexMigrator migrator1, RegexMigrator migrator2) {
            this.migrator1 = migrator1;
            this.migrator2 = migrator2;
        }

        public ColonedDirectiveMigrator(String colonedDirective, String directive) {
            migrator1 = new RegexMigrator(colonedDirective + ":(?=\\S)", "." + directive + " ");
            migrator2 = new RegexMigrator(colonedDirective + ":", "." + directive);
        }

        @Override
        public String migrate(String text) throws MigratorException {
            text = migrator1.migrate(text);
            return migrator2.migrate(text);
        }
    }

    private static class MultiplyingColonedDirectiveMigrator extends ColonedDirectiveMigrator {

        public MultiplyingColonedDirectiveMigrator(String colonedDirective, String directive, int multiplier) {
            super(new RegexMigrator(colonedDirective + ":(\\s+)", "." + directive + "$1" + multiplier + " * "),
                    new RegexMigrator(colonedDirective + ":", "." + directive + multiplier + " * "));
        }
    }

    private static class MultiplicationMigrator implements Migrator {
        @Override
        public String migrate(String text) throws MigratorException {
            // HACK: For ignoring comment.
            Matcher matcher = Pattern.compile("(?<!//.{0,255})\\b(\\S+?)\\s*\\*\\s*(\\S+?)\\b").matcher(text);
            while (matcher.find()) {
                String original = matcher.group();
                String multiplicand = matcher.group(1);
                String multiplier = matcher.group(2);
                int result;
                try {
                    result = IoUtils.parseUnsignedInteger(multiplicand) * IoUtils.parseUnsignedInteger(multiplier);
                } catch (NumberFormatException e) {
                    throw new MigratorException("Error parsing " + original, e);
                }
                text = Pattern.compile(original, Pattern.LITERAL).matcher(text).replaceAll(Integer.toString(result));
            }
            return text;
        }
    }
}
