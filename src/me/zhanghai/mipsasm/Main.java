/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm;

import me.zhanghai.mipsasm.assembler.Assembler;
import me.zhanghai.mipsasm.assembler.AssemblerException;
import me.zhanghai.mipsasm.assembler.AssemblyContext;
import me.zhanghai.mipsasm.parser.Parser;
import me.zhanghai.mipsasm.parser.ParserException;
import me.zhanghai.mipsasm.writer.Writer;
import me.zhanghai.mipsasm.writer.WriterException;
import org.apache.commons.cli.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;

public class Main {

    @SuppressWarnings("AccessStaticViaInstance")
    private static final Option OPTION_INPUT = OptionBuilder
            .hasArg()
            .withArgName("FILE")
            .withDescription("Read input from FILE")
            .withLongOpt("input")
            .create("i");

    @SuppressWarnings("AccessStaticViaInstance")
    private static final Option OPTION_OUTPUT = OptionBuilder
            .hasArg()
            .withArgName("FILE")
            .withDescription("Write output to FILE")
            .withLongOpt("output")
            .create("o");

    @SuppressWarnings("AccessStaticViaInstance")
    private static final Option OPTION_WRITER = OptionBuilder
            .hasArg()
            .withArgName("TYPE")
            .withDescription("Use writer of TYPE. TYPE can be 'binary', 'coe', or 'debug' (the default)")
            .withLongOpt("writer")
            .create("w");
    private static final String OPTION_WRITER_ARGUMENT_DEFAULT = "debug";

    @SuppressWarnings("AccessStaticViaInstance")
    private static final Option OPTION_HELP = OptionBuilder
            .withLongOpt("help")
            .create("h");

    private static final Options OPTIONS = new Options()
            .addOption(OPTION_INPUT)
            .addOption(OPTION_OUTPUT)
            .addOption(OPTION_WRITER)
            .addOption(OPTION_HELP);

    public static void main(String[] args) {

        Writer writer;
        try {
            org.apache.commons.cli.Parser commandLineParser = new GnuParser();
            CommandLine commandLine = commandLineParser.parse(OPTIONS, args);
            if (commandLine.hasOption(OPTION_HELP.getOpt())) {
                new HelpFormatter().printHelp("mipsasm [OPTION]...", OPTIONS);
                return;
            }
            if (commandLine.hasOption(OPTION_INPUT.getOpt())) {
                System.setIn(new FileInputStream(commandLine.getOptionValue(OPTION_INPUT.getOpt())));
            }
            if (commandLine.hasOption(OPTION_OUTPUT.getOpt())) {
                System.setOut(new PrintStream(commandLine.getOptionValue(OPTION_OUTPUT.getOpt())));
            }
            writer = Writer.valueOf(commandLine.getOptionValue(OPTION_WRITER.getOpt(), OPTION_WRITER_ARGUMENT_DEFAULT)
                    .toUpperCase());
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            System.exit(1);
            return;
        }

        try {
            AssemblyContext context = new AssemblyContext();

            try {
                Parser.parse(System.in, context);
            } catch (ParserException e) {
                e.printStackTrace();
                System.exit(1);
            }

            try {
                Assembler.assemble(context);
            } catch (AssemblerException e) {
                e.printStackTrace();
                System.exit(1);
            }

            try {
                writer.write(System.out, context);
            } catch (WriterException e) {
                e.printStackTrace();
                System.exit(1);
            }
        } catch (InternalException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
