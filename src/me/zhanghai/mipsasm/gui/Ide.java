/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.gui;

import me.zhanghai.mipsasm.assembler.Assembler;
import me.zhanghai.mipsasm.assembler.AssemblerException;
import me.zhanghai.mipsasm.assembler.AssemblyContext;
import me.zhanghai.mipsasm.parser.Parser;
import me.zhanghai.mipsasm.parser.ParserException;
import me.zhanghai.mipsasm.util.IoUtils;
import me.zhanghai.mipsasm.util.StringUtils;
import me.zhanghai.mipsasm.writer.Writer;
import me.zhanghai.mipsasm.writer.WriterException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.dnd.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.printing.PrintDialog;
import org.eclipse.swt.printing.Printer;
import org.eclipse.swt.printing.PrinterData;
import org.eclipse.swt.widgets.*;

import java.io.*;
import java.util.ResourceBundle;

public class Ide {

    private ResourceBundle resourceBundle;

    private Display display;
    private Image[] icons;
    private Font monospaceFont;

    private Shell shell;
    private Menu menu;
    private SashForm sashForm;
    private StyledText editText;
    private StyledTextUndoRedoHelper undoRedoHelper;
    private StyledText messageText;

    private Runnable updateAssembleMenuRunnable;
    private File file;

    public void run() {

        resourceBundle = ResourceBundle.getBundle("res/string/mipside", new Utf8Control());

        onCreateDisplay();

        icons = new Image[7];
        icons[0] = new Image(display, getClass().getResourceAsStream("/res/drawable/mipside_512.png"));
        icons[1] = new Image(display, getClass().getResourceAsStream("/res/drawable/mipside_256.png"));
        icons[2] = new Image(display, getClass().getResourceAsStream("/res/drawable/mipside_128.png"));
        icons[3] = new Image(display, getClass().getResourceAsStream("/res/drawable/mipside_64.png"));
        icons[4] = new Image(display, getClass().getResourceAsStream("/res/drawable/mipside_48.png"));
        icons[5] = new Image(display, getClass().getResourceAsStream("/res/drawable/mipside_32.png"));
        icons[6] = new Image(display, getClass().getResourceAsStream("/res/drawable/mipside_16.png"));
        monospaceFont = new Font(display, "monospace", 11, SWT.NONE);

        onCreateShell();
        shell.open();

        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }

        for (Image icon : icons) {
            icon.dispose();
        }
        monospaceFont.dispose();
        display.dispose();
    }

    private void onCreateDisplay() {
        Display.setAppName(resourceBundle.getString("app_name"));
        Display.setAppVersion("1.0.0");
        display = new Display();
    }

    private void onCreateShell() {

        shell = new Shell(display, SWT.SHELL_TRIM);

        shell.setImages(icons);

        shell.setLayout(new FillLayout());

        sashForm = new SashForm(shell, SWT.VERTICAL);

        editText = new StyledText(sashForm, SWT.H_SCROLL | SWT.V_SCROLL);
        editText.setAlwaysShowScrollBars(false);
        editText.setFont(monospaceFont);
        editText.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent modifyEvent) {
                onTextChanged();
            }
        });
        editText.setKeyBinding(SWT.MOD1 | 'A', ST.SELECT_ALL);
        undoRedoHelper = new StyledTextUndoRedoHelper(editText);
        editText.setMenu(StyledTextMenuHelper.createMenu(SWT.POP_UP, editText, undoRedoHelper, resourceBundle));
        DropTarget dropTarget = new DropTarget(editText, DND.DROP_DEFAULT | DND.DROP_COPY | DND.DROP_MOVE
                | DND.DROP_LINK);
        dropTarget.setTransfer(new Transfer[]{FileTransfer.getInstance()});
        dropTarget.addDropListener(new DropTargetAdapter() {
            @Override
            public void drop(DropTargetEvent dropTargetEvent) {
                FileTransfer fileTransfer = FileTransfer.getInstance();
                if (fileTransfer.isSupportedType(dropTargetEvent.currentDataType)) {
                    String filename = ((String[]) dropTargetEvent.data)[0];
                    if (confirmOpen()) {
                        openFile(filename);
                    }
                }
            }
        });

        messageText = new StyledText(sashForm, SWT.H_SCROLL | SWT.V_SCROLL);
        messageText.setAlwaysShowScrollBars(false);
        messageText.setEditable(false);
        messageText.setFont(monospaceFont);
        messageText.setKeyBinding(SWT.MOD1 | 'A', ST.SELECT_ALL);
        messageText.setMenu(StyledTextMenuHelper.createMenu(SWT.POP_UP, messageText, resourceBundle));

        sashForm.setWeights(new int[]{8, 2});

        // Menu depends on editText
        onCreateMenu();
        shell.setMenuBar(menu);

        shell.addListener(SWT.Close, new Listener() {
            @Override
            public void handleEvent(Event event) {
                event.doit = onClose();
            }
        });

        updateTitle();
    }

    private void onCreateMenu() {

        menu = new Menu(shell, SWT.BAR);

        Menu fileMenu = new Menu(shell, SWT.DROP_DOWN);
        new MenuItemBuilder(fileMenu)
                .setText(resourceBundle.getString("menu.file.open"))
                .setAccelerator(SWT.MOD1 + 'O')
                .addSelectionListener(new SelectionAdapter() {
                    @Override
                    public void widgetSelected(SelectionEvent selectionEvent) {
                        onOpen();
                    }
                })
                .build();
        new MenuItemBuilder(fileMenu)
                .setText(resourceBundle.getString("menu.file.save"))
                .setAccelerator(SWT.MOD1 + 'S')
                .addSelectionListener(new SelectionAdapter() {
                    @Override
                    public void widgetSelected(SelectionEvent selectionEvent) {
                        onSave();
                    }
                })
                .build();
        new MenuItemBuilder(fileMenu)
                .setText(resourceBundle.getString("menu.file.save_as"))
                .setAccelerator(SWT.MOD1 | SWT.SHIFT + 'S')
                .addSelectionListener(new SelectionAdapter() {
                    @Override
                    public void widgetSelected(SelectionEvent selectionEvent) {
                        onSaveAs();
                    }
                })
                .build();
        new MenuItemBuilder(fileMenu, SWT.SEPARATOR)
                .build();
        new MenuItemBuilder(fileMenu)
                .setText(resourceBundle.getString("menu.file.print"))
                .setAccelerator(SWT.MOD1 + 'P')
                .addSelectionListener(new SelectionAdapter() {
                    @Override
                    public void widgetSelected(SelectionEvent selectionEvent) {
                        onPrint();
                    }
                })
                .build();
        new MenuItemBuilder(fileMenu, SWT.SEPARATOR)
                .build();
        new MenuItemBuilder(fileMenu)
                .setText(resourceBundle.getString("menu.file.exit"))
                .addSelectionListener(new SelectionAdapter() {
                    @Override
                    public void widgetSelected(SelectionEvent selectionEvent) {
                        onExit();
                    }
                })
                .build();
        new MenuItemBuilder(menu, SWT.CASCADE)
                .setText(resourceBundle.getString("menu.file"))
                .setMenu(fileMenu)
                .build();

        Menu editMenu = StyledTextMenuHelper.createMenu(SWT.DROP_DOWN, editText, undoRedoHelper, resourceBundle);
        new MenuItemBuilder(menu, SWT.CASCADE)
                .setText(resourceBundle.getString("menu.edit"))
                .setMenu(editMenu)
                .build();

        Menu assembleMenu = new Menu(shell, SWT.DROP_DOWN);
        final MenuItem assembleBinaryMenuItem = new MenuItemBuilder(assembleMenu)
                .setText(resourceBundle.getString("menu.assemble.binary"))
                .setAccelerator(SWT.F9)
                .addSelectionListener(new SelectionAdapter() {
                    @Override
                    public void widgetSelected(SelectionEvent selectionEvent) {
                        onAssembleBinary();
                    }
                })
                .build();
        final MenuItem assembleCoeMenuItem = new MenuItemBuilder(assembleMenu)
                .setText(resourceBundle.getString("menu.assemble.coe"))
                .setAccelerator(SWT.F10)
                .addSelectionListener(new SelectionAdapter() {
                    @Override
                    public void widgetSelected(SelectionEvent selectionEvent) {
                        onAssembleCoe();
                    }
                })
                .build();
        new MenuItemBuilder(assembleMenu, SWT.SEPARATOR)
                .build();
        final MenuItem assembleAllMenuItem = new MenuItemBuilder(assembleMenu)
                .setText(resourceBundle.getString("menu.assemble.all"))
                .setAccelerator(SWT.F11)
                .addSelectionListener(new SelectionAdapter() {
                    @Override
                    public void widgetSelected(SelectionEvent selectionEvent) {
                        onAssembleAll();
                    }
                })
                .build();
        new MenuItemBuilder(menu, SWT.CASCADE)
                .setText(resourceBundle.getString("menu.assemble"))
                .setMenu(assembleMenu)
                .build();
        updateAssembleMenuRunnable = new Runnable() {
            @Override
            public void run() {
                boolean hasText = !StringUtils.isEmpty(editText.getText());
                assembleBinaryMenuItem.setEnabled(hasText);
                assembleCoeMenuItem.setEnabled(hasText);
                assembleAllMenuItem.setEnabled(hasText);
            }
        };
        updateAssembleMenuRunnable.run();

        Menu helpMenu = new Menu(shell, SWT.DROP_DOWN);
        new MenuItemBuilder(helpMenu)
                .setText(resourceBundle.getString("menu.help.about"))
                .setAccelerator(SWT.F1)
                .addSelectionListener(new SelectionAdapter() {
                    @Override
                    public void widgetSelected(SelectionEvent selectionEvent) {
                        onAbout();
                    }
                })
                .build();
        new MenuItemBuilder(menu, SWT.CASCADE)
                .setText(resourceBundle.getString("menu.help"))
                .setMenu(helpMenu)
                .build();
    }

    private boolean onClose() {
        if (shell.getModified()) {
            MessageBox messageBox = new MessageBox(shell, SWT.PRIMARY_MODAL | SWT.OK | SWT.CANCEL);
            messageBox.setMessage(resourceBundle.getString("file.exit_without_saving"));
            return messageBox.open() == SWT.OK;
        } else {
            return true;
        }
    }

    private void onOpen() {
        if (!confirmOpen()) {
            return;
        }
        FileDialog fileDialog = new FileDialog(shell, SWT.OPEN);
        fileDialog.setFilterNames(resourceBundle.getString("menu.file.open.filter_names").split("\\|"));
        fileDialog.setFilterExtensions(new String[]{
                "*.s;*.asm",
                "*.txt",
                "*.*"
        });
        String filename = fileDialog.open();
        if (filename == null) {
            return;
        }
        openFile(filename);
    }

    private void onSave() {
        if (file == null) {
            onSaveAs();
            return;
        }
        clearMessage();
        try {
            IoUtils.writeFile(file, editText.getText());
            setModified(false);
        } catch (IOException e) {
            showMessage(e);
        }
    }

    private void onSaveAs() {
        FileDialog fileDialog = new FileDialog(shell, SWT.SAVE);
        if (file != null) {
            fileDialog.setFilterPath(file.getParent());
            fileDialog.setFileName(file.getName());
        }
        String filename = fileDialog.open();
        if (filename == null) {
            return;
        }
        setFile(new File(filename));
        onSave();
    }

    private void onPrint() {
        PrintDialog printDialog = new PrintDialog(shell);
        PrinterData printerData = printDialog.open();
        if (printerData == null) {
            return;
        }
        Printer printer = new Printer(printerData);
        editText.print(printer).run();
        printer.dispose();
    }

    private void onExit() {
        shell.close();
    }

    private void onAssembleBinary() {
        assemble(Writer.BINARY, "bin");
    }

    private void onAssembleCoe() {
        assemble(Writer.COE, "coe");
    }

    private void onAssembleAll() {
        assemble(Writer.BINARY, "bin");
        assemble(Writer.COE, "coe", false);
    }

    private void onAbout() {
        AboutDialog aboutDialog = new AboutDialog(shell, resourceBundle, icons[2]);
        aboutDialog.open();
    }

    private void onTextChanged() {
        setModified(true);
        updateAssembleMenuRunnable.run();
    }

    private void setFile(File newFile) {
        file = newFile;
        updateTitle();
    }

    private void setModified(boolean modified) {
        shell.setModified(modified);
        updateTitle();
    }

    private void updateTitle() {
        String title;
        if (file != null) {
            title = file.getName();
        } else {
            title = resourceBundle.getString("file.unsaved_document");
        }
        if (shell.getModified()) {
            title = title + " *";
        }
        shell.setText(title);
    }

    private boolean confirmOpen() {
        if (shell.getModified()) {
            MessageBox messageBox = new MessageBox(shell, SWT.PRIMARY_MODAL | SWT.OK | SWT.CANCEL);
            messageBox.setMessage(resourceBundle.getString("file.open_without_saving"));
            return messageBox.open() == SWT.OK;
        } else {
            return true;
        }
    }

    private void openFile(File file) {
        clearMessage();
        try {
            String text = IoUtils.readFile(file);
            editText.setText(text);
            // Override modified set by onTextChange().
            setModified(false);
            setFile(file);
        } catch (IOException e) {
            showMessage(e);
        }
    }

    private void openFile(String filename) {
        openFile(new File(filename));
    }

    private void assemble(Writer writer, String extension, boolean shouldClearMessage) {

        if (file == null || shell.getModified()) {
            onSave();
            if (file == null || shell.getModified()) {
                return;
            }
        }

        if (shouldClearMessage) {
            clearMessage();
        }
        try {
            AssemblyContext context = new AssemblyContext();
            Parser.parse(new FileInputStream(file), context);
            Assembler.assemble(context);
            File output = IoUtils.replaceFileExtension(file, extension);
            writer.write(new FileOutputStream(output), context);
            showMessage(String.format(resourceBundle.getString("assemble.ok"), output));
            showMessageNewLine();
        } catch (Exception e) {
            showMessage(String.format(resourceBundle.getString("assemble.error"), file));
            showMessageNewLine();
            showMessage(e);
        }
    }

    private void assemble(Writer writer, String extension) {
        assemble(writer, extension, true);
    }

    private void showMessage(String error) {
        messageText.append(error);
    }

    private void showMessageNewLine() {
        showMessage("\n");
    }

    private void showMessage(Throwable throwable) {
        showMessage(throwableToMessage(throwable));
    }

    private String throwableToMessage(Throwable throwable) {
        if (throwable instanceof ParserException || throwable instanceof AssemblerException
                || throwable instanceof WriterException) {
            StringBuilder builder = new StringBuilder();
            do {
                String cause = StringUtils.camelCaseToPhrase(throwable.getClass().getSimpleName()
                        .replaceFirst("Exception$", ""));
                builder.append(cause)
                        .append(": ")
                        .append(throwable.getMessage())
                        .append('\n');
                throwable = throwable.getCause();
            } while (throwable != null);
            return builder.toString();
        } else {
            return IoUtils.getStackTrace(throwable);
        }
    }

    private void clearMessage() {
        messageText.setText("");
    }
}
