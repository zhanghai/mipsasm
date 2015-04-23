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

    private static final Migrator[] MIGRATORS = new Migrator[] {
            new ColonedDirectiveMigrator("BaseAddre", "text"),
            new ColonedDirectiveMigrator("DataAddre", "data"),
            new ColonedDirectiveMigrator("DB", "byte"),
            new ColonedDirectiveMigrator("DW", "half"),
            new ColonedDirectiveMigrator("DD", "word"),
            new ColonedDirectiveMigrator("RESB", "space"),
            new MultiplyingColonedDirectiveMigrator("RESW", "space", 2),
            new MultiplyingColonedDirectiveMigrator("RESD", "space", 4),
            new RegexMigrator(" *, *", ", "),
            new RegexMigrator("\\s+$", "")
    };

    public static String migrate(String text) throws MigratorException {
        for (Migrator migrator : MIGRATORS) {
            text = migrator.migrate(text);
        }
        // FIXME: Newlines at EOF is replace to nothing, don't know why, but fixing by this.
        text = text + System.lineSeparator();
        return text;
    }

    private interface Migrator {
        String migrate(String content) throws MigratorException;
    }

    private static class RegexMigrator implements Migrator {

        private ThreadLocal<Matcher> matcher;
        private String replacement;

        public RegexMigrator(String regex, String replacement) {
            this.matcher = RegexUtils.makeThreadLocalMatcher(regex);
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
            migrator1 = new RegexMigrator(colonedDirective + ":(?=\\S)", "\\." + directive + " ");
            migrator2 = new RegexMigrator(colonedDirective + ":", "\\." + directive);
        }

        @Override
        public String migrate(String text) throws MigratorException {
            text = migrator1.migrate(text);
            return migrator2.migrate(text);
        }
    }

    private static class MultiplyingColonedDirectiveMigrator extends ColonedDirectiveMigrator {

        public MultiplyingColonedDirectiveMigrator(String colonedDirective, String directive, int multiplier) {
            super(new RegexMigrator(colonedDirective + ":(\\s+)", "\\." + directive + "$1" + multiplier + " * "),
                    new RegexMigrator(colonedDirective + ":", "\\." + directive + multiplier + " * "));
        }

        @Override
        public String migrate(String text) throws MigratorException {
            text = super.migrate(text);
            Matcher matcher = Pattern.compile("(?<=\\s*)(\\S+?)\\s*\\*\\s*(\\S+?)(?=\\s*)").matcher(text);
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
                text = text.replaceAll(original, Integer.toString(result));
            }
            return text;
        }
    }
}
