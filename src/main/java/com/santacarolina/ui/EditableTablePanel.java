package com.santacarolina.ui;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.santacarolina.util.AbstractCustomModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.*;
import java.io.IOException;

public  class EditableTablePanel<T>  extends JPanel {

    private static final Logger logger = LogManager.getLogger();

    private final TablePopupMenu tablePopupMenu = new TablePopupMenu();
    private JTable table;
    private AbstractCustomModel<T> model;
    private final KeyStroke delKs = KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0,false);
    private final KeyStroke backspaceKs = KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE,0,false);
    private final KeyStroke pasteKs = KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK,false);

    private final ActionListener pasteAction = e -> pasteContent();

    private final ActionListener backspaceAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (table.isEditing()) table.getCellEditor().stopCellEditing();

            int[] selectedRows = table.getSelectedRows();
            int[] selectedColumns = table.getSelectedColumns();

            for (int row : selectedRows) {
                for (int columnIndex : selectedColumns) {
                    Class<?> columnClass = model.getColumnClass(columnIndex);
                    if (columnClass.equals(String.class)) model.setValueAt("", row, columnIndex);
                    else if (columnClass.equals(Number.class)) model.setValueAt(0, row, columnIndex);
                    else model.setValueAt(null, row, columnIndex);
                }
            }

        }
    };

    private final ActionListener delAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            int[] rows = table.getSelectedRows();
            for (int i = rows.length - 1; i >= 0; i--){
                int row = rows[i];
                model.removeRow(row);
            }
        }
    };

    public EditableTablePanel(AbstractCustomModel<T> model) {
        this.model = model;
        initComponents();
    }

    private void initComponents(){
        table = new JTable(model);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    tablePopupMenu.show(table,e.getX(),e.getY());
                }
            }
        });

        table.registerKeyboardAction(pasteAction, pasteKs, JComponent.WHEN_FOCUSED);
        table.registerKeyboardAction(delAction, delKs, JComponent.WHEN_FOCUSED);
        table.registerKeyboardAction(backspaceAction,backspaceKs,JComponent.WHEN_FOCUSED);

        JScrollPane pane = new JScrollPane(table);
        setLayout(new BorderLayout());
        add(pane,BorderLayout.CENTER);
    }

    private void pasteContent(){
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        try {
            String data = clipboard.getData(DataFlavor.stringFlavor).toString();
            String[] dataRow = data.split("\n");
            int row = table.getSelectedRow();
            int column = table.getSelectedColumn();
            for (String s : dataRow) {
                if (row == model.getRowCount()) model.addNewRow();
                model.setValueAt(s,row,column);
                row++;
            }
        } catch (UnsupportedFlavorException | IOException ex) {
            logger.error(ex.getMessage());
        }
    }

    public JTable getTable() {
        return table;
    }

    private class TablePopupMenu extends JPopupMenu {

        public TablePopupMenu() {
            initComponents();
        }

        private void initComponents(){

            JMenuItem deleteItem = new JMenuItem("Excluir Linha", new FlatSVGIcon("icon/delete_doc_icon.svg"));
            JMenuItem pasteItem = new JMenuItem("Colar", new FlatSVGIcon("icon/paste_icon.svg"));

            deleteItem.addActionListener(e -> {
                int[] rows = table.getSelectedRows();
                for (int i = rows.length - 1; i >=0; i--){
                    int row = rows[i];
                    model.removeRow(row);
                }
            });

            pasteItem.addActionListener(e -> pasteContent());

            add(deleteItem);
            add(pasteItem);
            pack();
        }
    }
}
