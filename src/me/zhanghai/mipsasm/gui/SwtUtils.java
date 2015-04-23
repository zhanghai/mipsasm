/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.gui;

import me.zhanghai.mipsasm.util.IoUtils;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class SwtUtils {

    private SwtUtils() {}

    public static void loadFont(String resourceName) throws IOException {
        File file = File.createTempFile(SwtUtils.class.getPackage().getName() + ".", null);
        try (InputStream resourceInputStream  = SwtUtils.class.getResourceAsStream(resourceName)) {
            Files.copy(resourceInputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
        file.deleteOnExit();
        Display.getCurrent().loadFont(file.getPath());
    }

    public static Image loadImage(String resourceName) {
        InputStream inputStream = SwtUtils.class.getResourceAsStream(resourceName);
        Image image = new Image(Display.getCurrent(), inputStream);
        IoUtils.close(inputStream);
        return image;
    }

    public static Image[] loadImageArray(String[] resourceNameArray) {
        Image[] imageArray = new Image[resourceNameArray.length];
        for (int i = 0; i < resourceNameArray.length; ++i) {
            imageArray[i] = loadImage(resourceNameArray[i]);
        }
        return imageArray;
    }

    public static void setFontStyle(Control control, int style) {
        Font oldFont = control.getFont();
        FontData[] fontDataArray = oldFont.getFontData();
        for (FontData fontData : fontDataArray) {
            fontData.setStyle(style);
        }
        final Font newFont = new Font(Display.getCurrent(), fontDataArray);
        control.setFont(newFont);
        control.addDisposeListener(new DisposeListener() {
            @Override
            public void widgetDisposed(DisposeEvent disposeEvent) {
                newFont.dispose();
            }
        });
    }
}
