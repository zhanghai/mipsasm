/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ExtendedModifyEvent;
import org.eclipse.swt.custom.ExtendedModifyListener;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;

import java.util.Stack;

/**
 * Adds the Undo-Redo functionality (working Ctrl+Z and Ctrl+Y) to an instance of {@link StyledText}.
 *
 * @author Petr Bodnar
 * @see <a href="http://www.java2s.com/Code/Java/SWT-JFace-Eclipse/SWTUndoRedo.htm">
 *     http://www.java2s.com/Code/Java/SWT-JFace-Eclipse/SWTUndoRedo.htm</a> - inspiration for this code, though not
 *     really functioning - it mainly shows which listeners to use...
 * @see <a href="http://stackoverflow.com/questions/7179464/swt-how-to-recreate-a-default-context-menu-for-text-fields">
 *     http://stackoverflow.com/questions/7179464/swt-how-to-recreate-a-default-context-menu-for-text-fields</a> -
 *     "SWT's StyledText doesn't support Undo-Redo out-of-the-box"
 */
public class StyledTextUndoRedoHelper implements KeyListener, ExtendedModifyListener {

    /**
     * Encapsulation of the Undo and Redo stack(s).
     */
    private static class UndoRedoStack<T> {

        private Stack<T> undo;
        private Stack<T> redo;

        public UndoRedoStack() {
            undo = new Stack<>();
            redo = new Stack<>();
        }

        public void pushUndo(T delta) {
            undo.add(delta);
        }

        public void pushRedo(T delta) {
            redo.add(delta);
        }

        public T popUndo() {
            return undo.pop();
        }

        public T popRedo() {
            return redo.pop();
        }

        public void clearUndo() {
            undo.clear();
        }

        public void clearRedo() {
            redo.clear();
        }

        public void clear() {
            clearUndo();
            clearRedo();
        }

        public boolean hasUndo() {
            return !undo.isEmpty();
        }

        public boolean hasRedo() {
            return !redo.isEmpty();
        }
    }

    private StyledText styledText;

    private UndoRedoStack<ExtendedModifyEvent> stack;

    private boolean isUndo;

    private boolean isRedo;

    /**
     * Creates a new instance of this class. Automatically starts listening to corresponding key and modify events
     * coming from the given <var>styledText</var>.
     *
     * @param styledText the text field to which the Undo-Redo functionality should be added
     */
    public StyledTextUndoRedoHelper(StyledText styledText) {

        this.styledText = styledText;
        stack = new UndoRedoStack<>();

        styledText.addExtendedModifyListener(this);
        styledText.addKeyListener(this);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.swt.events.KeyListener#keyPressed(org.eclipse.swt.events.KeyEvent)
     */
    @Override
    public void keyPressed(KeyEvent e) {
        // Listen to CTRL+Z for Undo, to CTRL+Y or CTRL+SHIFT+Z for Redo
        boolean isCtrl = (e.stateMask & SWT.CTRL) > 0;
        boolean isAlt = (e.stateMask & SWT.ALT) > 0;
        if (isCtrl && !isAlt) {
            boolean isShift = (e.stateMask & SWT.SHIFT) > 0;
            if (!isShift && e.keyCode == 'z') {
                undo();
            } else if (!isShift && e.keyCode == 'y' || isShift
                    && e.keyCode == 'z') {
                redo();
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.swt.events.KeyListener#keyReleased(org.eclipse.swt.events.KeyEvent)
     */
    @Override
    public void keyReleased(KeyEvent e) {}

    /**
     * Creates a corresponding Undo or Redo step from the given event and pushes it to the stack. The Redo stack is,
     * logically, emptied if the event comes from a normal user action.
     *
     * @param event The event
     * @see org.eclipse.swt.custom.ExtendedModifyListener#modifyText(org.eclipse.swt.custom.ExtendedModifyEvent)
     */
    @Override
    public void modifyText(ExtendedModifyEvent event) {
        if (isUndo) {
            stack.pushRedo(event);
        } else { // is Redo or a normal user action
            stack.pushUndo(event);
            if (!isRedo) {
                stack.clearRedo();
                // TODO Switch to treat consecutive characters as one event?
            }
        }
    }

    public boolean canUndo() {
        return stack.hasUndo();
    }

    public boolean canRedo() {
        return stack.hasRedo();
    }

    /**
     * Performs the Undo action. A new corresponding Redo step is automatically pushed to the stack.
     */
    public void undo() {
        if (canUndo()) {
            isUndo = true;
            revertEvent(stack.popUndo());
            isUndo = false;
        }
    }

    /**
     * Performs the Redo action. A new corresponding Undo step is automatically pushed to the stack.
     */
    public void redo() {
        if (canRedo()) {
            isRedo = true;
            revertEvent(stack.popRedo());
            isRedo = false;
        }
    }

    public void clear() {
        stack.clear();
    }

    /**
     * Reverts the given modify event, in the way as the Eclipse text editor does it.
     *
     * @param event The event
     */
    private void revertEvent(ExtendedModifyEvent event) {
        // This causes the modifyText() listener method to be called.
        styledText.replaceTextRange(event.start, event.length, event.replacedText);
        styledText.setSelectionRange(event.start, event.replacedText.length());
    }
}
