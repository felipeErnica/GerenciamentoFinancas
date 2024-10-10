package com.santacarolina.areas.bancario.extrato.frmAddExtrato;

import com.santacarolina.dao.ExtratoDAO;
import com.santacarolina.exceptions.SaveFailException;
import com.santacarolina.interfaces.Controller;
import com.santacarolina.interfaces.OnResize;
import com.santacarolina.model.Extrato;
import com.santacarolina.ui.CurrencyCellRenderer;
import com.santacarolina.ui.DateCellRenderer;
import com.santacarolina.util.CustomErrorThrower;
import com.santacarolina.util.OptionDialog;
import com.santacarolina.util.ViewInvoker;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.List;

public class AddExtratoController implements Controller {

    private ExtratoDAO extratoDAO;
    private AddExtratoView view;
    private AddExtratoModel model;

    public AddExtratoController(AddExtratoView view, AddExtratoModel model) {
        this.view = view;
        this.model = model;
        extratoDAO = new ExtratoDAO();
        initComponents();
    }

    private void initComponents() {
        view.getTable().setModel(model.getTableModel().getBaseModel());
        view.getTablePanel().setDelAction(e -> tableDelAction());
        view.getTablePanel().setPasteAction(e -> tablePasteAction());
        view.getTablePanel().setBackspaceAction(e -> tableBackspaceAction());
        view.getTablePanel().setCutAction(e -> tableCutAction());
        view.getDialog().addComponentListener((OnResize) e -> view.formatColumns());

        TableColumnModel columnModel = view.getTable().getColumnModel();

        DateCellRenderer dateRenderer = new DateCellRenderer();
        dateRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        CurrencyCellRenderer currencyRenderer =  new CurrencyCellRenderer();

        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        columnModel.getColumn(1).setCellRenderer(dateRenderer);
        columnModel.getColumn(1).setCellEditor(new DefaultCellEditor(new JTextField()));
        columnModel.getColumn(2).setCellRenderer(cellRenderer);
        columnModel.getColumn(5).setCellRenderer(currencyRenderer);
        columnModel.getColumn(5).setCellEditor(new DefaultCellEditor(new JTextField()));

        view.getAddButton().addActionListener(e -> addButton_onClick());
        view.getAddExtratos().addActionListener(e -> addExtratos_onClick());
        if (model.getList().isEmpty()) addButton_onClick();
    }

    private void addExtratos_onClick() {
        EventQueue.invokeLater(() -> {
            try {
                List<Extrato> list = model.getList();
                if (!ExtratoValidator.validate(model)) return;
                extratoDAO.saveAll(list);
                OptionDialog.showSuccessSaveMessage();
                view.getDialog().dispose();
            } catch (SaveFailException e) {
                CustomErrorThrower.throwError(e);
            }
        });
    }

    private void addButton_onClick() {
        Extrato e = new Extrato();
        e.setContaBancaria(model.getContaBancaria());
        model.addRow(e);
    }

    private void tableDelAction() {
        EventQueue.invokeLater(() -> {
            int[] rows = view.getTable().getSelectedRows();
            model.removeRows(rows);
        });
    }

    private void tablePasteAction() {
        EventQueue.invokeLater(() -> {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            try {
                String data = clipboard.getData(DataFlavor.stringFlavor).toString();
                String[] dataRow = data.split("\n");
                int row = view.getTable().getSelectedRow();
                int column = view.getTable().getSelectedColumn();
                for (String s : dataRow) {
                    if (row == model.getTableModel().getRowCount()) addButton_onClick();
                    model.setValueAt(s, row, column);
                    row++;
                }
            } catch (UnsupportedFlavorException | IOException ex) {
                OptionDialog.showErrorDialog("Falha ao colar!", "Operação Inválida!");
            }
        });
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
            StringSelection selection = new StringSelection(content.toString().replace("null",""));
            clipboard.setContents(selection, selection);
        });
    }


    private void tableBackspaceAction() {
        EventQueue.invokeLater(() -> {
            int[] rows = view.getTable().getSelectedRows();
            int[] columns = view.getTable().getSelectedColumns();
            for (int row : rows) {
                for (int column : columns) {
                    model.setValueAt(null, row, column);
                }
            }
        });
    }

    @Override
    public void showView() { ViewInvoker.showMaximizedView(view.getDialog()); }

}
