package com.santacarolina.areas.bancario.pix.frmManagePix;

import com.santacarolina.areas.bancario.pix.frmAddPix.AddPixForm;
import com.santacarolina.areas.bancario.pix.frmEditPix.EditPixForm;
import com.santacarolina.dao.PixDao;
import com.santacarolina.exceptions.DeleteFailException;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.Controller;
import com.santacarolina.interfaces.DoubleClickListener;
import com.santacarolina.interfaces.OnResize;
import com.santacarolina.model.beans.ChavePix;
import com.santacarolina.util.CustomErrorThrower;
import com.santacarolina.util.OptionDialog;
import com.santacarolina.util.ViewInvoker;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseEvent;

public class ManagePixController implements Controller {

    private PixTableModel model;
    private ManagePixView view;
    private RowSorter<PixTableModel> sorter;

    public ManagePixController(PixTableModel model, ManagePixView view) {
        this.model = model;
        this.view = view;
        initComponents();
    }

    private void initComponents() {
        view.getTable().setModel(model);
        sorter = new TableRowSorter<>(model);
        view.getTable().setRowSorter(sorter);
        view.getDialog().addComponentListener((OnResize) e -> dialog_onResize());
        view.getDeleteButton().addActionListener(e -> deleteButton_onClick());
        view.getAddButton().addActionListener(e -> addButton_onClick());
        view.getTable().addMouseListener((DoubleClickListener) this::table_onDoubleClick);
    }

    private void dialog_onResize() { EventQueue.invokeLater(() -> view.formatColumns()); }

    private void table_onDoubleClick(MouseEvent e) {
        EventQueue.invokeLater(() -> {
            try {
                int viewRow = view.getTable().rowAtPoint(e.getPoint());
                int modelRow = sorter.convertRowIndexToModel(viewRow);
                ChavePix chavePix = model.getObject(modelRow);
                if (new EditPixForm(chavePix).isUpdated()) model.requeryTable();
            } catch (FetchFailException ex) {
                CustomErrorThrower.throwError(ex);
                view.getDialog().dispose();
            }
        });
    }

    private void addButton_onClick() {
        EventQueue.invokeLater(() -> {
            try {
                if (new AddPixForm().isUpdated()) model.requeryTable();
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
                    ChavePix pix = model.getObject(modelRow);
                    new PixDao().deleteById(pix.getId());
                    model.removeRow(modelRow);
                }
            } catch (DeleteFailException e) {
                CustomErrorThrower.throwError(e);
            }
        });
    }

    @Override
    public void showView() {
        ViewInvoker.showMaximizedView(view.getDialog());
    }

}
