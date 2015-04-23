/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm;

import me.zhanghai.mipsasm.gui.Ide;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;

public class Main {

    public static void main(String[] args) {

        CommandLine commandLine;
        try {
            commandLine = Cli.parseCommandLine(args);
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }
        if (!Cli.checkCommandLine(commandLine)) {
            return;
        }

        // If OPTION_TERMINAL is present, then there will be an option.
        if (Cli.hasGraphicalOption(commandLine) || !Cli.hasOption(commandLine)) {
            new Ide().run();
        } else {
            Cli.run(commandLine);
        }
    }
}
