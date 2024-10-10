package com.santacarolina.ui;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.swing.*;
import java.awt.event.*;

public class TablePanel {

    private static final Logger logger = LogManager.getLogger();

    private TablePopupMenu tablePopupMenu;
    private JTable table;
    private JPopupMenu popupMenu;
    private JScrollPane tableScrollPane;
    private final KeyStroke delKs = KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0,false);
    private ActionListener delAction;

    public TablePanel() {
        initComponents();
    }

    private void initComponents(){
        tablePopupMenu = new TablePopupMenu();
        popupMenu = tablePopupMenu.getPopupMenu();
        table = new JTable();
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    popupMenu.show(table,e.getX(),e.getY());
                }
            }
        });
        tableScrollPane = new JScrollPane(table);
    }

    public JTable getTable() { return table; }
    public JScrollPane getTableScrollPane() { return tableScrollPane; }
    public JPopupMenu getPopupMenu() { return popupMenu; }

    public void setPopupMenu(JPopupMenu popupMenu) { this.popupMenu = popupMenu; }

    public void setDelAction(ActionListener delAction) {
        this.delAction = delAction;
        table.registerKeyboardAction(delAction, delKs, JComponent.WHEN_FOCUSED);
        tablePopupMenu.getDeleteItem().addActionListener(delAction);
    }

    private class TablePopupMenu  {

        private JPopupMenu popupMenu;
        private JMenuItem deleteItem;

        public TablePopupMenu() { initComponents(); }

        private void initComponents(){
            popupMenu = new JPopupMenu();
            deleteItem = new JMenuItem("Excluir Linha", new FlatSVGIcon("icon/delete_icon.svg", 0.7f));
            deleteItem.addActionListener(delAction);
            popupMenu.add(deleteItem);
            popupMenu.pack();
        }

        public JPopupMenu getPopupMenu() { return popupMenu; }
        public JMenuItem getDeleteItem() { return deleteItem; }

    }

}
