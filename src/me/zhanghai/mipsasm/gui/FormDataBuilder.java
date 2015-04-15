/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.gui;

import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;

public class FormDataBuilder {

    private FormData formData = new FormData();

    public FormDataBuilder setLeft(FormAttachment left) {
        formData.left = left;
        return this;
    }

    public FormDataBuilder setRight(FormAttachment right) {
        formData.right = right;
        return this;
    }

    public FormDataBuilder setTop(FormAttachment top) {
        formData.top = top;
        return this;
    }

    public FormDataBuilder setBottom(FormAttachment bottom) {
        formData.bottom = bottom;
        return this;
    }

    public FormDataBuilder setWidth(int width) {
        formData.width = width;
        return this;
    }

    public FormDataBuilder setHeight(int height) {
        formData.height = height;
        return this;
    }

    public FormData build() {
        return formData;
    }
}
