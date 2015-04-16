/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.gui;

import me.zhanghai.mipsasm.util.StringUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ST;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.events.MenuAdapter;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import java.util.ResourceBundle;

public class StyledTextMenuHelper {

    public static Menu createMenu(int style, final StyledText styledText, ResourceBundle resourceBundle) {

        Menu menu = new Menu(styledText.getShell(), style);
        final MenuItem cutMenuItem = new MenuItemBuilder(menu)
                .setText(resourceBundle.getString("menu.edit.cut"))
                .addSelectionListener(new SelectionAdapter() {
                    @Override
                    public void widgetSelected(SelectionEvent selectionEvent) {
                        styledText.cut();
                    }
                })
                .build();
        final MenuItem copyMenuItem = new MenuItemBuilder(menu)
                .setText(resourceBundle.getString("menu.edit.copy"))
                .addSelectionListener(new SelectionAdapter() {
                    @Override
                    public void widgetSelected(SelectionEvent selectionEvent) {
                        styledText.copy();
                    }
                })
                .build();
        final MenuItem pasteMenuItem = new MenuItemBuilder(menu)
                .setText(resourceBundle.getString("menu.edit.paste"))
                .addSelectionListener(new SelectionAdapter() {
                    @Override
                    public void widgetSelected(SelectionEvent selectionEvent) {
                        styledText.paste();
                    }
                })
                .build();
        final MenuItem deleteMenuItem = new MenuItemBuilder(menu)
                .setText(resourceBundle.getString("menu.edit.delete"))
                .addSelectionListener(new SelectionAdapter() {
                    @Override
                    public void widgetSelected(SelectionEvent selectionEvent) {
                        styledText.invokeAction(ST.DELETE_NEXT);
                    }
                })
                .build();
        new MenuItemBuilder(menu, SWT.SEPARATOR)
                .build();
        final MenuItem selectAllMenuItem = new MenuItemBuilder(menu)
                .setText(resourceBundle.getString("menu.edit.select_all"))
                .addSelectionListener(new SelectionAdapter() {
                    @Override
                    public void widgetSelected(SelectionEvent selectionEvent) {
                        styledText.selectAll();
                    }
                })
                .build();
        menu.addMenuListener(new MenuAdapter() {
            @Override
            public void menuShown(MenuEvent menuEvent) {

                boolean isEditable = styledText.getEditable();
                boolean hasSelection = styledText.isTextSelected();
                cutMenuItem.setEnabled(isEditable && hasSelection);
                copyMenuItem.setEnabled(hasSelection);

                TextTransfer textTransfer = TextTransfer.getInstance();
                Clipboard clipboard = new Clipboard(Display.getCurrent());
                String clipboardText = (String) clipboard.getContents(textTransfer);
                clipboard.dispose();
                boolean hasClipboardText = !StringUtils.isEmpty(clipboardText);
                pasteMenuItem.setEnabled(isEditable && hasClipboardText);

                deleteMenuItem.setEnabled(isEditable && hasSelection);

                boolean hasText = !StringUtils.isEmpty(styledText.getText());
                selectAllMenuItem.setEnabled(hasText);
            }
        });

        return menu;
    }

    public static Menu createMenu(int style, final StyledText styledText, final StyledTextUndoRedoHelper undoRedoHelper,
                                  ResourceBundle resourceBundle) {

        Menu menu = new Menu(styledText.getShell(), style);
        final MenuItem undoMenuItem = new MenuItemBuilder(menu)
                .setText(resourceBundle.getString("menu.edit.undo"))
                .addSelectionListener(new SelectionAdapter() {
                    @Override
                    public void widgetSelected(SelectionEvent selectionEvent) {
                        undoRedoHelper.undo();
                    }
                })
                .build();
        final MenuItem redoMenuItem = new MenuItemBuilder(menu)
                .setText(resourceBundle.getString("menu.edit.redo"))
                .addSelectionListener(new SelectionAdapter() {
                    @Override
                    public void widgetSelected(SelectionEvent selectionEvent) {
                        undoRedoHelper.redo();
                    }
                })
                .build();
        new MenuItemBuilder(menu, SWT.SEPARATOR)
                .build();
        final MenuItem cutMenuItem = new MenuItemBuilder(menu)
                .setText(resourceBundle.getString("menu.edit.cut"))
                .addSelectionListener(new SelectionAdapter() {
                    @Override
                    public void widgetSelected(SelectionEvent selectionEvent) {
                        styledText.cut();
                    }
                })
                .build();
        final MenuItem copyMenuItem = new MenuItemBuilder(menu)
                .setText(resourceBundle.getString("menu.edit.copy"))
                .addSelectionListener(new SelectionAdapter() {
                    @Override
                    public void widgetSelected(SelectionEvent selectionEvent) {
                        styledText.copy();
                    }
                })
                .build();
        final MenuItem pasteMenuItem = new MenuItemBuilder(menu)
                .setText(resourceBundle.getString("menu.edit.paste"))
                .addSelectionListener(new SelectionAdapter() {
                    @Override
                    public void widgetSelected(SelectionEvent selectionEvent) {
                        styledText.paste();
                    }
                })
                .build();
        final MenuItem deleteMenuItem = new MenuItemBuilder(menu)
                .setText(resourceBundle.getString("menu.edit.delete"))
                .addSelectionListener(new SelectionAdapter() {
                    @Override
                    public void widgetSelected(SelectionEvent selectionEvent) {
                        styledText.invokeAction(ST.DELETE_NEXT);
                    }
                })
                .build();
        new MenuItemBuilder(menu, SWT.SEPARATOR)
                .build();
        final MenuItem selectAllMenuItem = new MenuItemBuilder(menu)
                .setText(resourceBundle.getString("menu.edit.select_all"))
                .addSelectionListener(new SelectionAdapter() {
                    @Override
                    public void widgetSelected(SelectionEvent selectionEvent) {
                        styledText.selectAll();
                    }
                })
                .build();
        menu.addMenuListener(new MenuAdapter() {
            @Override
            public void menuShown(MenuEvent menuEvent) {

                undoMenuItem.setEnabled(undoRedoHelper.canUndo());
                redoMenuItem.setEnabled(undoRedoHelper.canRedo());

                boolean isEditable = styledText.getEditable();
                boolean hasSelection = styledText.isTextSelected();
                cutMenuItem.setEnabled(isEditable && hasSelection);
                copyMenuItem.setEnabled(hasSelection);

                TextTransfer textTransfer = TextTransfer.getInstance();
                Clipboard clipboard = new Clipboard(Display.getCurrent());
                String clipboardText = (String) clipboard.getContents(textTransfer);
                clipboard.dispose();
                boolean hasClipboardText = !StringUtils.isEmpty(clipboardText);
                pasteMenuItem.setEnabled(isEditable && hasClipboardText);

                deleteMenuItem.setEnabled(isEditable && hasSelection);

                boolean hasText = !StringUtils.isEmpty(styledText.getText());
                selectAllMenuItem.setEnabled(hasText);
            }
        });

        return menu;
    }
}
