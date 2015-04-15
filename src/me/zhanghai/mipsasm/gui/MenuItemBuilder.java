/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

public class MenuItemBuilder {

    private MenuItem menuItem;

    public MenuItemBuilder(Menu menu, int style) {
        menuItem = new MenuItem(menu, style);
    }

    public MenuItemBuilder(Menu menu) {
        this(menu, SWT.NULL);
    }

    public MenuItemBuilder addSelectionListener(SelectionListener listener) {
        menuItem.addSelectionListener(listener);
        return this;
    }

    public MenuItemBuilder setAccelerator(int accelerator) {
        menuItem.setAccelerator(accelerator);
        return this;
    }

    public MenuItemBuilder setMenu(Menu menu) {
        menuItem.setMenu(menu);
        return this;
    }

    public MenuItemBuilder setText(String text) {
        menuItem.setText(text);
        return this;
    }

    public MenuItem build() {
        return menuItem;
    }
}
