/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm;

import me.zhanghai.mipsasm.assembler.Assembler;
import me.zhanghai.mipsasm.assembler.AssemblerException;
import me.zhanghai.mipsasm.assembler.AssemblyContext;
import me.zhanghai.mipsasm.disassembler.Disassembler;
import me.zhanghai.mipsasm.disassembler.DisassemblerException;
import me.zhanghai.mipsasm.parser.Parser;
import me.zhanghai.mipsasm.parser.ParserException;
import me.zhanghai.mipsasm.util.IoUtils;
import me.zhanghai.mipsasm.writer.Writer;
import me.zhanghai.mipsasm.writer.WriterException;
import org.apache.commons.cli.*;

import java.io.*;
import java.util.List;

public class Cli {

    @SuppressWarnings("AccessStaticViaInstance")
    private static final Option OPTION_GRAPHICAL = OptionBuilder
            .withDescription("Launch graphical user interface")
            .withLongOpt("graphical")
            .create("g");

    @SuppressWarnings("AccessStaticViaInstance")
    private static final Option OPTION_TERMINAL = OptionBuilder
            .withDescription("Launch in terminal mode")
            .withLongOpt("terminal")
            .create("t");

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
    private static final Option OPTION_DISASSEMBLE = OptionBuilder
            .hasArg()
            .withDescription("Disassemble input")
            .withLongOpt("disassemble")
            .create("d");

    @SuppressWarnings("AccessStaticViaInstance")
    private static final Option OPTION_WRITER = OptionBuilder
            .hasArg()
            .withArgName("TYPE")
            .withDescription("Use writer of TYPE. TYPE can be 'binary', 'coe', 'debug' (the default), or 'hexdebug'")
            .withLongOpt("writer")
            .create("w");
    private static final String OPTION_WRITER_ARGUMENT_DEFAULT = "debug";

    @SuppressWarnings("AccessStaticViaInstance")
    private static final Option OPTION_HELP = OptionBuilder
            .withDescription("Display this help and exit")
            .withLongOpt("help")
            .create("h");

    @SuppressWarnings("AccessStaticViaInstance")
    private static final Option OPTION_VERSION = OptionBuilder
            .withDescription("Display version information and exit")
            .withLongOpt("version")
            .create("v");

    private static final Options OPTIONS = new Options()
            .addOption(OPTION_GRAPHICAL)
            .addOption(OPTION_TERMINAL)
            .addOption(OPTION_INPUT)
            .addOption(OPTION_OUTPUT)
            .addOption(OPTION_WRITER)
            .addOption(OPTION_HELP)
            .addOption(OPTION_VERSION);

    public static CommandLine parseCommandLine(String[] args) throws ParseException {
        org.apache.commons.cli.Parser commandLineParser = new GnuParser();
        return commandLineParser.parse(OPTIONS, args);
    }

    public static boolean checkCommandLine(CommandLine commandLine) {
        @SuppressWarnings("unchecked")
        List<String> args = (List<String>) commandLine.getArgList();
        if (!args.isEmpty()) {
            System.err.println("Unknown argument: " + args);
            printHelp();
            return false;
        } else {
            return true;
        }
    }

    public static boolean hasOption(CommandLine commandLine) {
        return commandLine.iterator().hasNext();
    }

    public static boolean hasGraphicalOption(CommandLine commandLine) {
        return commandLine.hasOption(OPTION_GRAPHICAL.getOpt());
    }

    public static boolean hasTerminalOption(CommandLine commandLine) {
        return commandLine.hasOption(OPTION_TERMINAL.getOpt());
    }

    public static void printHelp() {
        new HelpFormatter().printHelp("mipsasm [OPTION]...", OPTIONS);
    }

    public static void printVersion() {
        System.out.print("mipsasm " + Build.VERSION_NAME + "\n" +
                "Copyright (C) 2015 Zhang Hai\n" +
                "License GPLv3+: GNU GPL version 3 or later\n" +
                "<https://www.gnu.org/licenses/gpl.html>.\n" +
                "This program comes with ABSOLUTELY NO WARRANTY.\n" +
                "This is free software, and you are welcome to redistribute it\n" +
                "under certain conditions.\n");
    }

    public static void run(CommandLine commandLine) {

        if (commandLine.hasOption(OPTION_HELP.getOpt())) {
            printHelp();
            return;
        } else if (commandLine.hasOption(OPTION_VERSION.getOpt())) {
            printVersion();
            return;
        }
        InputStream inputStream;
        OutputStream outputStream;
        try {
            if (commandLine.hasOption(OPTION_INPUT.getOpt())) {
                inputStream = new FileInputStream(commandLine.getOptionValue(OPTION_INPUT.getOpt()));
            } else {
                inputStream = System.in;
            }
            if (commandLine.hasOption(OPTION_OUTPUT.getOpt())) {
                outputStream = new PrintStream(commandLine.getOptionValue(OPTION_OUTPUT.getOpt()));
            } else {
                outputStream = System.out;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        boolean disassemble = commandLine.hasOption(OPTION_DISASSEMBLE.getOpt());
        Writer writer = Writer.valueOf(commandLine.getOptionValue(OPTION_WRITER.getOpt(),
                OPTION_WRITER_ARGUMENT_DEFAULT).toUpperCase());

        if (disassemble) {
            try {
                Disassembler.disassemble(inputStream, outputStream);
            } catch (DisassemblerException e) {
                e.printStackTrace();
            } finally {
                if (inputStream != System.in) {
                    IoUtils.close(inputStream);
                }
                if (outputStream != System.out) {
                    IoUtils.close(outputStream);
                }
            }
        } else {
            try {
                AssemblyContext context = new AssemblyContext();
                Parser.parse(inputStream, context);
                Assembler.assemble(context);
                writer.write(outputStream, context);
            } catch (ParserException | AssemblerException | WriterException | InternalException e) {
                e.printStackTrace();
            } finally {
                if (inputStream != System.in) {
                    IoUtils.close(inputStream);
                }
                if (outputStream != System.out) {
                    IoUtils.close(outputStream);
                }
            }
        }
    }
}
