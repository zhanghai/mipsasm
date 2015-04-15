/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.gui;

import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

public class SwtUtils {

    private SwtUtils() {}

    public static void setFontStyle(Control control, int style) {
        Font oldFont = control.getFont();
        FontData[] fontDataArray = oldFont.getFontData();
        for (FontData fontData : fontDataArray) {
            fontData.setStyle(style);
        }
        final Font newFont = new Font(Display.getDefault(), fontDataArray);
        control.setFont(newFont);
        control.addDisposeListener(new DisposeListener() {
            @Override
            public void widgetDisposed(DisposeEvent disposeEvent) {
                newFont.dispose();
            }
        });
    }
}
