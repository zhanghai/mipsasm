/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import java.util.ResourceBundle;

public class AboutDialog extends Dialog {

    private Shell shell;

    private ResourceBundle resourceBundle;

    private Image icon;

    public AboutDialog(Shell shell, ResourceBundle resourceBundle, Image icon) {
        super(shell);
        this.resourceBundle = resourceBundle;
        this.icon = icon;
    }

    public AboutDialog(Shell shell, int style, ResourceBundle resourceBundle, Image icon) {
        super(shell, style);
        this.resourceBundle = resourceBundle;
        this.icon = icon;
    }

    public void open() {

        onCreateShell();

        shell.open();
        Display display = getParent().getDisplay();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }

    public void onCreateShell() {

        shell = new Shell(getParent());

        shell.setText(resourceBundle.getString("about.title"));

        RowLayout rowLayout = new RowLayout();
        rowLayout.marginWidth = rowLayout.marginHeight = 7;
        rowLayout.center = true;
        rowLayout.spacing = 12;
        rowLayout.type = SWT.VERTICAL;
        shell.setLayout(rowLayout);

        Label iconLabel = new Label(shell, SWT.NONE);
        iconLabel.setImage(icon);

        Label nameLabel = new Label(shell, SWT.NONE);
        nameLabel.setText(Display.getAppName());
        SwtUtils.setFontStyle(nameLabel, SWT.BOLD);

        Label versionLabel = new Label(shell, SWT.NONE);
        versionLabel.setText(Display.getAppVersion());

        Label authorLabel = new Label(shell, SWT.NONE);
        authorLabel.setText(resourceBundle.getString("about.author"));

        shell.pack();
    }
}
