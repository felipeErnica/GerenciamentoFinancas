package com.santacarolina.util;

import com.santacarolina.interfaces.EditTableModel;
import com.santacarolina.ui.EditTablePanel;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class EditTableControllerImpl {

    private EditTablePanel view;
    private EditTableModel model;

    public EditTableControllerImpl(EditTablePanel view, EditTableModel model) {
        this.view = view;
        this.model = model;
        init();
    }

    private void init() {
        view.setDelAction(e -> tableDelAction());
        view.setPasteAction(e -> tablePasteAction());
        view.setCutAction(e -> tableCutAction());
        view.setBackspaceAction(e -> tableBackspaceAction());
    }

    private void tableCutAction() {
        EventQueue.invokeLater(() -> {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            int[] rows = view.getTable().getSelectedRows();
            int[] columns = view.getTable().getSelectedColumns();
            StringBuffer content = new StringBuffer();
            for (int row : rows) {
                for (int column : columns) {
                    content.append(model.getValueAt(row, column));
                    content.append("\t");
                    model.setValueAt(null, row, column);
                }
                content.append("\n");
            }
            StringSelection selection = new StringSelection(content.toString().replace("null", ""));
            clipboard.setContents(selection, selection);
        });
    }

    private void tablePasteAction() {
        EventQueue.invokeLater(() -> {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            try {
                String data = clipboard.getData(DataFlavor.stringFlavor).toString();
                String[] dataRow = data.split("\n");
                if (dataRow.length == 1) pasteSingleLine(dataRow[0]);
                else pasteMultipleLines(dataRow);
            } catch (UnsupportedFlavorException | IOException ex) {
                OptionDialog.showErrorDialog("Falha ao colar!", "Operação Inválida!");
            }
        });
    }

    private void pasteSingleLine(String data) {
        int[] rows = view.getTable().getSelectedRows();
        int[] columns = view.getTable().getSelectedColumns();
        for (int row : rows) {
            for (int column : columns) {
                model.setValueAt(data, row, column);
                model.fireTableCellUpdated(row, column);
            }
        }
    }

    private void pasteMultipleLines(String[] data) {
        int row = view.getTable().getSelectedRow();
        int column = view.getTable().getSelectedColumn();
        for (String s : data) {
            if (row == model.getRowCount()) model.addNewRow();
            model.setValueAt(s, row, column);
            row++;
            model.fireTableCellUpdated(row, column);
        }
    }

    private void tableDelAction() {
        EventQueue.invokeLater(() -> {
            int[] rows = view.getTable().getSelectedRows();
            model.removeRows(rows);
        });
    }

    private void tableBackspaceAction() {
        EventQueue.invokeLater(() -> {
            int[] rows = view.getTable().getSelectedRows();
            int[] columns = view.getTable().getSelectedColumns();
            for (int row : rows) {
                for (int column : columns) {
                    model.setValueAt(null, row, column);
                    model.fireTableCellUpdated(row, column);
                }
            }
        });
    }

}
