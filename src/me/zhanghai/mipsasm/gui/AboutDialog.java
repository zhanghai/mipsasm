/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.*;

import java.util.ResourceBundle;

public class AboutDialog extends Dialog {

    private static final String WEBSITE_URL = "https://github.com/zhanghai/mipsasm";

    private static final String LICENSE_URL = "https://www.gnu.org/licenses/gpl.html";

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
        rowLayout.marginWidth = rowLayout.marginHeight = 12;
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

        Label descriptionLabel = new Label(shell, SWT.NONE);
        descriptionLabel.setText(resourceBundle.getString("about.description"));

        Link websiteLink = new Link(shell, SWT.NONE);
        websiteLink.setText("<a>" + resourceBundle.getString("about.website") + "</a>");
        websiteLink.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                Program.launch(WEBSITE_URL);
            }
        });

        Label copyrightLabel = new Label(shell, SWT.NONE);
        copyrightLabel.setText(resourceBundle.getString("about.copyright"));
        SwtUtils.setFontHeight(copyrightLabel, 10);

        Composite licenseComposite = new Composite(shell, SWT.NONE);
        RowLayout licenseCompositeLayout = new RowLayout();
        licenseCompositeLayout.center = true;
        licenseCompositeLayout.type = SWT.VERTICAL;
        licenseComposite.setLayout(licenseCompositeLayout);
        Label warrantyLink = new Label(licenseComposite, SWT.NONE);
        warrantyLink.setText(resourceBundle.getString("about.warranty"));
        SwtUtils.setFontHeight(warrantyLink, 10);
        Link licenseLink = new Link(licenseComposite, SWT.NONE);
        licenseLink.setText(resourceBundle.getString("about.license"));
        licenseLink.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                Program.launch(LICENSE_URL);
            }
        });
        SwtUtils.setFontHeight(licenseLink, 10);

        shell.pack();
    }
}
