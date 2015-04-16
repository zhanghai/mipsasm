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

    private Menu parentMenu;
    private int style;

    private boolean selectionListenerSet;
    private SelectionListener selectionListener;
    private boolean acceleratorSet;
    private int accelerator;
    private boolean menuSet;
    private Menu menu;
    private boolean textSet;
    private String text;

    public MenuItemBuilder(Menu parentMenu, int style) {
        this.parentMenu = parentMenu;
        this.style = style;
    }

    public MenuItemBuilder(Menu parentMenu) {
        this(parentMenu, SWT.NULL);
    }

    public MenuItemBuilder addSelectionListener(SelectionListener selectionListener) {
        this.selectionListener = selectionListener;
        selectionListenerSet = true;
        return this;
    }

    public MenuItemBuilder setAccelerator(int accelerator) {
        this.accelerator = accelerator;
        acceleratorSet = true;
        return this;
    }

    public MenuItemBuilder setMenu(Menu menu) {
        this.menu = menu;
        menuSet = true;
        return this;
    }

    public MenuItemBuilder setText(String text) {
        this.text = text;
        textSet = true;
        return this;
    }

    public MenuItem build() {
        MenuItem menuItem = new MenuItem(parentMenu, style);
        if (selectionListenerSet) {
            menuItem.addSelectionListener(selectionListener);
        }
        if (acceleratorSet) {
            menuItem.setAccelerator(accelerator);
        }
        if (menuSet) {
            menuItem.setMenu(menu);
        }
        if (textSet) {
            menuItem.setText(text);
        }
        return menuItem;
    }
}
