package com.santacarolina.areas.bancario.dadoBancario.frmManageDado;

import com.santacarolina.areas.bancario.dadoBancario.frmAddDado.AddDadoBancarioForm;
import com.santacarolina.areas.bancario.dadoBancario.frmEditDado.EditDadoForm;
import com.santacarolina.dao.DadoDao;
import com.santacarolina.exceptions.DeleteFailException;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.Controller;
import com.santacarolina.interfaces.DoubleClickListener;
import com.santacarolina.model.beans.DadoBancario;
import com.santacarolina.util.CustomErrorThrower;
import com.santacarolina.util.OptionDialog;
import com.santacarolina.util.ViewInvoker;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseEvent;

public class ManageDadoController implements Controller {

    private DadoTableModel model;
    private ManageDadoView view;
    private RowSorter<DadoTableModel> sorter;

    public ManageDadoController(DadoTableModel model, ManageDadoView view) {
        this.model = model;
        this.view = view;
        initComponents();
    }

    private void initComponents() {
        view.getTable().setModel(model);
        view.formatColumns();
        sorter = new TableRowSorter<>(model);
        view.getTable().setRowSorter(sorter);

        view.getDeleteButton().addActionListener(e -> deleteButton_onClick());
        view.getAddButton().addActionListener(e -> addButton_onClick());
        view.getTable().addMouseListener((DoubleClickListener) this::table_onDoubleClick);
    }

    private void table_onDoubleClick(MouseEvent e) {
        EventQueue.invokeLater(() -> {
            int viewRow = view.getTable().rowAtPoint(e.getPoint());
            int modelRow = sorter.convertRowIndexToModel(viewRow);
            DadoBancario dadoBancario = model.getObject(modelRow);
            new EditDadoForm(dadoBancario);
        });
    }

    private void addButton_onClick() {
        EventQueue.invokeLater(() -> {
            try {
                if (new AddDadoBancarioForm().isUpdated()) model.requeryTable();
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
                    DadoBancario dadoBancario = model.getObject(modelRow);
                    new DadoDao().deleteById(dadoBancario.getId());
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
