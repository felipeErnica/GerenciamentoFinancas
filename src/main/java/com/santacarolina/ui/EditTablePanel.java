package com.santacarolina.ui;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.security.Key;

public class EditTablePanel {

    private TablePanel panel;
    private JTable table;
    private EditPopupMenu editPopupMenu;
    private JPopupMenu popupMenu;
    private final KeyStroke backspaceKs = KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0, false);
    private final KeyStroke pasteKs = KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK, false);
    private final KeyStroke cutKs = KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK, false);
    private ActionListener backspaceAction;
    private ActionListener pasteAction;
    private ActionListener cutAction;

    public EditTablePanel() { initComponents(); }

    private void initComponents() {
        panel = new TablePanel();
        editPopupMenu = new EditPopupMenu();
        popupMenu = editPopupMenu.getPopupMenu();
        panel.setPopupMenu(popupMenu);
        table = panel.getTable();
        table.setColumnSelectionAllowed(true);
    }

    public void setCutAction(ActionListener cutAction) {
        this.cutAction = cutAction;
        table.registerKeyboardAction(cutAction, cutKs, JComponent.WHEN_FOCUSED);
        editPopupMenu.getCutItem().addActionListener(cutAction);
    }

    public void setBackspaceAction(ActionListener backspaceAction) {
        this.backspaceAction = backspaceAction;
        table.registerKeyboardAction(backspaceAction,backspaceKs,JComponent.WHEN_FOCUSED);
    }

    public void setPasteAction(ActionListener pasteAction) {
        this.pasteAction = pasteAction;
        table.registerKeyboardAction(pasteAction, pasteKs, JComponent.WHEN_FOCUSED);
        editPopupMenu.getPasteItem().addActionListener(pasteAction);
    }

    public void setDelAction(ActionListener delAction) { panel.setDelAction(delAction); }
    public JTable getTable() { return table; }
    public JScrollPane getTableScrollPane() { return panel.getTableScrollPane(); }

    private class EditPopupMenu {

        private JPopupMenu popupMenu;
        private JMenuItem pasteItem;
        private JMenuItem cutItem;

        public EditPopupMenu() {
            this.popupMenu = panel.getPopupMenu();
            initComponents();
        }

        private void initComponents() {
            pasteItem = new JMenuItem("Colar", new FlatSVGIcon("icon/paste_icon.svg", 0.7f));
            cutItem = new JMenuItem("Cortar", new FlatSVGIcon("icon/cut_icon.svg", 0.7f));
            popupMenu.add(pasteItem);
            popupMenu.add(cutItem);
            popupMenu.pack();
        }

        public JPopupMenu getPopupMenu() { return popupMenu; }
        public JMenuItem getPasteItem() { return pasteItem; }
        public JMenuItem getCutItem() { return cutItem; }

    }

}
