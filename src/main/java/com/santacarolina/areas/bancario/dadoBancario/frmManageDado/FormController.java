package com.santacarolina.areas.bancario.dadoBancario.frmManageDado;

import java.awt.EventQueue;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.RowSorter;
import javax.swing.table.TableRowSorter;

import com.santacarolina.areas.bancario.dadoBancario.frmAddDado.AddDadoBancarioForm;
import com.santacarolina.areas.bancario.dadoBancario.frmEditDado.EditDadoForm;
import com.santacarolina.dao.DadoDAO;
import com.santacarolina.exceptions.DeleteFailException;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.Controller;
import com.santacarolina.interfaces.DoubleClickListener;
import com.santacarolina.model.DadoBancario;
import com.santacarolina.util.CustomErrorThrower;
import com.santacarolina.util.OptionDialog;
import com.santacarolina.util.ViewInvoker;

public class FormController implements Controller {

    private TableModel model;
    private FormView view;
    private RowSorter sorter;

    public FormController(TableModel model, FormView view) {
        this.model = model;
        this.view = view;
        initComponents();
    }

    private void initComponents() {
        view.getTable().setModel(model.getBaseModel());
        view.formatColumns();
        sorter = new TableRowSorter<>(model.getBaseModel());
        view.getTable().setRowSorter(sorter);

        view.getDeleteButton().addActionListener(e -> deleteButton_onClick());
        view.getAddButton().addActionListener(e -> addButton_onClick());
        view.getTable().addMouseListener((DoubleClickListener) this::table_onDoubleClick);
    }

    private void table_onDoubleClick(MouseEvent e) {
        EventQueue.invokeLater(() -> {
            try {
                int viewRow = view.getTable().rowAtPoint(e.getPoint());
                int modelRow = sorter.convertRowIndexToModel(viewRow);
                DadoBancario dadoBancario = model.getObject(modelRow).fromDTO();
                DadoBancario dadoSaved = EditDadoForm.open(dadoBancario);
                if (dadoSaved != null) model.requeryTable();
            } catch (FetchFailException ex) {
                CustomErrorThrower.throwError(ex);
            }
        });
    }

    private void addButton_onClick() {
        EventQueue.invokeLater(() -> {
            try {
                DadoBancario dadoAdded = AddDadoBancarioForm.open();
                if (dadoAdded != null) model.requeryTable();
            } catch (FetchFailException e) {
                CustomErrorThrower.throwError(e);
            }
        });
    }

    private void deleteButton_onClick() {
        EventQueue.invokeLater(() -> {
            try {
                int[] rows = view.getTable().getSelectedRows();
                if (OptionDialog.showDeleteDialog(rows.length) != JOptionPane.YES_OPTION) return;
                for (int i = rows.length - 1; i >= 0; i--) {
                    int viewRow = rows[i];
                    int modelRow = sorter.convertRowIndexToModel(viewRow);
                    DadoBancario dadoBancario = model.getObject(modelRow).fromDTO();
                    new DadoDAO().deleteById(dadoBancario.getId());
                    model.removeRow(modelRow);
                }
            } catch (DeleteFailException e) {
                CustomErrorThrower.throwError(e);
            }
        });
    }

    @Override
    public void showView() { ViewInvoker.showMaximizedView(view.getDialog()); }

}
