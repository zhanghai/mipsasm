/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.gui;

import org.eclipse.swt.graphics.FontData;

public class FontDataBuilder {

    private boolean nameSet;
    private String name;
    private boolean heightSet;
    private int height;
    private boolean styleSet;
    private int style;
    private boolean localeSet;
    private String locale;

    public FontDataBuilder setName(String name) {
        this.name = name;
        nameSet = true;
        return this;
    }

    public FontDataBuilder setHeight(int height) {
        this.height = height;
        heightSet = true;
        return this;
    }

    public FontDataBuilder setStyle(int style) {
        this.style = style;
        styleSet = true;
        return this;
    }

    public FontDataBuilder setLocale(String locale) {
        this.locale = locale;
        localeSet = true;
        return this;
    }

    public FontData build() {
        FontData fontData = new FontData();
        if (nameSet) {
            fontData.setName(name);
        }
        if (heightSet) {
            fontData.setHeight(height);
        }
        if (styleSet) {
            fontData.setStyle(style);
        }
        if (localeSet) {
            fontData.setLocale(locale);
        }
        return fontData;
    }
}
