package com.santacarolina.areas.bancario.extrato.frmAddExtrato;

import java.awt.EventQueue;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

import com.santacarolina.dao.ExtratoDAO;
import com.santacarolina.exceptions.SaveFailException;
import com.santacarolina.interfaces.Controller;
import com.santacarolina.interfaces.EditTableController;
import com.santacarolina.model.Extrato;
import com.santacarolina.ui.CurrencyCellRenderer;
import com.santacarolina.ui.DateCellRenderer;
import com.santacarolina.util.CustomErrorThrower;
import com.santacarolina.util.EditTableControllerImpl;
import com.santacarolina.util.OptionDialog;
import com.santacarolina.util.ViewInvoker;

public class FormController implements EditTableController, Controller {

    private FormView view;
    private FormModel model;

    public FormController(FormView view, FormModel model) {
        this.view = view;
        this.model = model;
        new EditTableControllerImpl(view.getTablePanel(), model.getTableModel());
        initComponents();
    }

    private void initComponents() {
        view.getTable().setModel(model.getTableModel().getBaseModel());
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
                new ExtratoDAO().saveAll(list);
                OptionDialog.showSuccessSaveMessage();
                view.getDialog().dispose();
            } catch (SaveFailException e) {
                CustomErrorThrower.throwError(e);
            }
        });
    }

    private void addButton_onClick() {
        Extrato e = new Extrato();
        e.setConta(model.getContaBancaria());
        model.addRow(e);
    }

    @Override
    public void showView() { ViewInvoker.showMaximizedView(view.getDialog()); }

    @Override
    public void addNewRow() { model.addRow(new Extrato()); }

}
