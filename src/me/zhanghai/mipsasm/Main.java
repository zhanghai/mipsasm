/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm;

import me.zhanghai.mipsasm.gui.Ide;

public class Main {

    public static void main(String[] args) {
        if (System.console() == null) {
            new Ide().run();
        } else {
            Cli.run(args);
        }
    }
}
