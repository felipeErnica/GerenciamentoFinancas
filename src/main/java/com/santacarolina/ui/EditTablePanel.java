package com.santacarolina.ui;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class EditTablePanel {

    private TablePanel panel;
    private JTable table;
    private final KeyStroke backspaceKs = KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE,0,false);
    private final KeyStroke pasteKs = KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK,false);
    private ActionListener backspaceAction;
    private ActionListener pasteAction;

    public EditTablePanel() {
        initComponents();
    }

    private void initComponents() {
        panel = new TablePanel();
        table = panel.getTable();
        table.setColumnSelectionAllowed(true);
    }

    public void setBackspaceAction(ActionListener backspaceAction) {
        this.backspaceAction = backspaceAction;
        table.registerKeyboardAction(backspaceAction,backspaceKs,JComponent.WHEN_FOCUSED);
    }

    public void setPasteAction(ActionListener pasteAction) {
        this.pasteAction = pasteAction;
        table.registerKeyboardAction(pasteAction, pasteKs, JComponent.WHEN_FOCUSED);
    }

    public void setDelAction(ActionListener delAction) { panel.setDelAction(delAction); }
    public JTable getTable() { return table; }
    public JScrollPane getTableScrollPane() { return panel.getTableScrollPane(); }

    private class EditPopupMenu {

        private JPopupMenu popupMenu;

        public EditPopupMenu() {
            this.popupMenu = panel.getPopupMenu();
            initComponents();
        }

        private void initComponents() {
            JMenuItem pasteItem = new JMenuItem("Colar", new FlatSVGIcon("icon/paste_icon.svg"));
            popupMenu.add(pasteItem);
            popupMenu.revalidate();
            popupMenu.repaint();
        }

    }

}
