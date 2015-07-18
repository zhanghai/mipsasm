/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.gui;

import me.zhanghai.mipsasm.Build;
import me.zhanghai.mipsasm.assembler.Assembler;
import me.zhanghai.mipsasm.assembler.AssemblerException;
import me.zhanghai.mipsasm.assembler.AssemblerPreferences;
import me.zhanghai.mipsasm.assembler.AssemblyContext;
import me.zhanghai.mipsasm.disassembler.CoeReader;
import me.zhanghai.mipsasm.disassembler.CoeReaderException;
import me.zhanghai.mipsasm.disassembler.Disassembler;
import me.zhanghai.mipsasm.disassembler.DisassemblerException;
import me.zhanghai.mipsasm.parser.MigratorException;
import me.zhanghai.mipsasm.parser.Parser;
import me.zhanghai.mipsasm.parser.ParserException;
import me.zhanghai.mipsasm.parser.SqsMigrator;
import me.zhanghai.mipsasm.util.IoUtils;
import me.zhanghai.mipsasm.util.StringUtils;
import me.zhanghai.mipsasm.writer.Writer;
import me.zhanghai.mipsasm.writer.WriterException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ST;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.dnd.*;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.printing.PrintDialog;
import org.eclipse.swt.printing.Printer;
import org.eclipse.swt.printing.PrinterData;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.*;

import java.io.*;
import java.util.ResourceBundle;

public class Ide {

    private ResourceBundle resourceBundle;

    private Display display;
    private Image[] icons;
    private Font editFont;

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
        editFont.dispose();
        display.dispose();
    }

    private void onCreateDisplay() {

        Display.setAppName(resourceBundle.getString("app_name"));
        Display.setAppVersion(Build.VERSION_NAME);
        display = new Display();

        icons = SwtUtils.loadImageArray(new String[] {
                "/res/drawable/mipside_512.png",
                "/res/drawable/mipside_256.png",
                "/res/drawable/mipside_128.png",
                "/res/drawable/mipside_64.png",
                "/res/drawable/mipside_48.png",
                "/res/drawable/mipside_32.png",
                "/res/drawable/mipside_16.png"
        });
        try {
            SwtUtils.loadFont("/res/font/SourceCodePro-Regular.ttf");
            SwtUtils.loadFont("/res/font/SourceCodePro-Bold.ttf");
        } catch (IOException e) {
            e.printStackTrace();
        }
        editFont = new Font(display, new FontData[] {
                new FontDataBuilder().setName("Source Code Pro").setHeight(11).build(),
                new FontDataBuilder().setName("monospace").setHeight(11).build(),
        });

    }

    private void onCreateShell() {

        shell = new Shell(display, SWT.SHELL_TRIM);

        shell.setImages(icons);

        shell.setLayout(new FillLayout());

        sashForm = new SashForm(shell, SWT.VERTICAL);

        editText = new StyledText(sashForm, SWT.H_SCROLL | SWT.V_SCROLL);
        editText.setAlwaysShowScrollBars(false);
        editText.setFont(editFont);
        editText.setTabs(8);
        editText.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent modifyEvent) {
                onTextChanged();
            }
        });
        editText.setKeyBinding(SWT.MOD1 | 'A', ST.SELECT_ALL);
        StyledTextStyleHelper.setup(editText);
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
        messageText.setFont(editFont);
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
                .setText(resourceBundle.getString("menu.file.import"))
                .setAccelerator(SWT.MOD1 + SWT.ALT + 'O')
                .addSelectionListener(new SelectionAdapter() {
                    @Override
                    public void widgetSelected(SelectionEvent selectionEvent) {
                        onImport();
                    }
                })
                .build();
        new MenuItemBuilder(fileMenu)
                .setText(resourceBundle.getString("menu.file.disassemble"))
                .setAccelerator(SWT.MOD1 + SWT.SHIFT + 'O')
                .addSelectionListener(new SelectionAdapter() {
                    @Override
                    public void widgetSelected(SelectionEvent selectionEvent) {
                        onDisassemble();
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
        final MenuItem assembleDebugMenuItem = new MenuItemBuilder(assembleMenu)
                .setText(resourceBundle.getString("menu.assemble.debug"))
                .setAccelerator(SWT.F11)
                .addSelectionListener(new SelectionAdapter() {
                    @Override
                    public void widgetSelected(SelectionEvent selectionEvent) {
                        onAssembleDebug();
                    }
                })
                .build();
        final MenuItem assembleAllMenuItem = new MenuItemBuilder(assembleMenu)
                .setText(resourceBundle.getString("menu.assemble.all"))
                .setAccelerator(SWT.F12)
                .addSelectionListener(new SelectionAdapter() {
                    @Override
                    public void widgetSelected(SelectionEvent selectionEvent) {
                        onAssembleAll();
                    }
                })
                .build();
        new MenuItemBuilder(assembleMenu, SWT.SEPARATOR)
                .build();
        new MenuItemBuilder(assembleMenu)
                .setText(resourceBundle.getString("menu.assemble.open_directory"))
                .setAccelerator(SWT.F8)
                .addSelectionListener(new SelectionAdapter() {
                    @Override
                    public void widgetSelected(SelectionEvent selectionEvent) {
                        onOpenAssembleDirectory();
                    }
                })
                .build();
        new MenuItemBuilder(assembleMenu, SWT.SEPARATOR)
                .build();
        new MenuItemBuilder(assembleMenu, SWT.CHECK)
                .setText(resourceBundle.getString("menu.assemble.enable_delay_slot"))
                .addSelectionListener(new SelectionAdapter() {
                    @Override
                    public void widgetSelected(SelectionEvent selectionEvent) {
                        onSetDelaySlotEnabled(((MenuItem) selectionEvent.widget).getSelection());
                    }
                })
                .build()
                .setSelection(AssemblerPreferences.getDelaySlotEnabled());
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
                assembleDebugMenuItem.setEnabled(hasText);
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
            MessageBox messageBox = new MessageBox(shell, SWT.PRIMARY_MODAL | SWT.ICON_WARNING | SWT.OK | SWT.CANCEL);
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

    private void onImport() {
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
        importFile(filename);
    }

    private void onDisassemble() {
        if (!confirmOpen()) {
            return;
        }
        FileDialog fileDialog = new FileDialog(shell, SWT.OPEN);
        fileDialog.setFilterNames(resourceBundle.getString("menu.file.disassemble.filter_names").split("\\|"));
        fileDialog.setFilterExtensions(new String[]{
                "*.bin;*.coe",
                "*.bin",
                "*.coe",
                "*.*"
        });
        String filename = fileDialog.open();
        if (filename == null) {
            return;
        }
        disassemble(filename);
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
            fileDialog.setFileName(file.getName());
            fileDialog.setFilterPath(file.getParent());
            fileDialog.setFilterNames(resourceBundle.getString("menu.file.save_as.filter_names").split("\\|"));
            fileDialog.setFilterExtensions(new String[] {
                    "*.s",
                    "*.asm",
                    "*.txt",
                    "*.*"
            });
        }
        String filename = fileDialog.open();
        if (filename == null) {
            return;
        }
        File file = new File(filename);
        if (file.exists()) {
            MessageBox messageBox = new MessageBox(shell, SWT.PRIMARY_MODAL | SWT.ICON_WARNING | SWT.OK | SWT.CANCEL);
            messageBox.setMessage(String.format(resourceBundle.getString("file.overwrite"), filename));
            if (messageBox.open() != SWT.OK) {
                return;
            }
        }
        setFile(file);
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

    private void onAssembleDebug() {
        assemble(Writer.DEBUG, "txt");
    }

    private void onAssembleAll() {
        assemble(Writer.BINARY, "bin");
        assemble(Writer.COE, "coe", false);
        assemble(Writer.DEBUG, "txt", false);
    }

    private void onOpenAssembleDirectory() {

        if (file == null || shell.getModified()) {
            onSave();
            if (file == null || shell.getModified()) {
                return;
            }
        }

        Program.launch(file.getParent());
    }

    private void onSetDelaySlotEnabled(boolean delaySlotEnabled) {
        AssemblerPreferences.setDelaySlotEnabled(delaySlotEnabled);
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

    private void setDocument(File file, String text, boolean modified) {
        editText.setText(text);
        if (!modified) {
            // Override modified set by onTextChange().
            setModified(false);
        }
        undoRedoHelper.clear();
        setFile(file);
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
            MessageBox messageBox = new MessageBox(shell, SWT.PRIMARY_MODAL | SWT.ICON_WARNING | SWT.OK | SWT.CANCEL);
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
            setDocument(file, text, false);
        } catch (IOException e) {
            showMessage(e);
        }
    }

    private void openFile(String filename) {
        openFile(new File(filename));
    }

    private void importFile(File file) {
        clearMessage();
        try {
            String text = IoUtils.readFile(file);
            text = SqsMigrator.migrate(text);
            setDocument(file, text, true);
        } catch (Exception e) {
            showMessage(e);
        }
    }

    private void importFile(String filename) {
        importFile(new File(filename));
    }

    private void disassemble(File file) {
        clearMessage();
        InputStream inputStream;
        try {
            inputStream = new FileInputStream(file);
        } catch (IOException e) {
            showMessage(e);
            return;
        }
        if (IoUtils.getFileExtension(file).equals("coe")) {
            InputStream fileInputStream = inputStream;
            try {
                inputStream = CoeReader.coeToBytes(fileInputStream);
            } catch (Exception e) {
                showMessage(e);
                return;
            } finally {
                IoUtils.close(fileInputStream);
            }
        }
        OutputStream outputStream = new ByteArrayOutputStream();
        try {
            Disassembler.disassemble(inputStream, outputStream);
            String disassembly = outputStream.toString();
            setDocument(null, disassembly, true);
        } catch (Exception e) {
            showMessage(e);
        } finally {
            IoUtils.close(inputStream);
            IoUtils.close(outputStream);
        }
    }

    private void disassemble(String filename) {
        disassemble(new File(filename));
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
        showMessage(System.lineSeparator());
    }

    private void showMessage(Throwable throwable) {
        showMessage(throwableToMessage(throwable));
    }

    private String throwableToMessage(Throwable throwable) {
        if (throwable instanceof ParserException || throwable instanceof MigratorException
                || throwable instanceof AssemblerException || throwable instanceof WriterException
                || throwable instanceof CoeReaderException || throwable instanceof DisassemblerException) {
            StringBuilder builder = new StringBuilder();
            boolean first = true;
            do {
                if (first) {
                    first = false;
                } else {
                    builder.append('\t');
                }
                String cause = StringUtils.camelCaseToPhrase(throwable.getClass().getSimpleName()
                        .replaceFirst("Exception$", ""));
                builder.append(cause)
                        .append(": ")
                        .append(throwable.getMessage())
                        .append(System.lineSeparator());
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
