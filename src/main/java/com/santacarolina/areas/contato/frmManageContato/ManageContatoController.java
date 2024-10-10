package com.santacarolina.areas.contato.frmManageContato;

import com.santacarolina.areas.contato.frmEditContato.EditContatoForm;
import com.santacarolina.dao.ContatoDAO;
import com.santacarolina.exceptions.DeleteFailException;
import com.santacarolina.interfaces.ManageController;
import com.santacarolina.areas.contato.frmAddContato.AddContatoForm;
import com.santacarolina.model.Contato;
import com.santacarolina.ui.ManageControllerImpl;
import com.santacarolina.util.CustomErrorThrower;
import com.santacarolina.util.OptionDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.MouseEvent;

public class ManageContatoController implements ManageController {

    private ManageControllerImpl<Contato> manageController;
    private ContatoTableModel model;
    private ManageContatoView view;
    private RowSorter<ContatoTableModel> sorter;

    public ManageContatoController(ContatoTableModel model, ManageContatoView view) {
        this.manageController = new ManageControllerImpl<>(model, view, this);
        this.sorter = manageController.getSorter();
        this.model = model;
        this.view = view;
        initComponents();
    }

    private void initComponents() {
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        TableColumnModel columnModel = view.getTable().getColumnModel();
        columnModel.getColumn(1).setCellRenderer(renderer);
        columnModel.getColumn(2).setCellRenderer(renderer);
        columnModel.getColumn(3).setCellRenderer(renderer);
    }

    @Override
    public void table_onDoubleClick(MouseEvent e) {
        int viewRow = view.getTable().rowAtPoint(e.getPoint());
        int modelRow  = sorter.convertRowIndexToModel(viewRow);
        Contato contato = model.getObject(modelRow);
        new EditContatoForm(contato);
    }

    @Override
    public void addButton_onClick() { EventQueue.invokeLater(AddContatoForm::openNew); }

    @Override
    public void deleteButton_onClick() {
        try {
            int[] rows = view.getTable().getSelectedRows();
            if (OptionDialog.showDeleteDialog(rows.length) != JOptionPane.YES_OPTION) return;
            for (int i = rows.length - 1; i >= 0; i--) {
                int viewRow = rows[i];
                int modelRow = sorter.convertRowIndexToModel(viewRow);
                Contato c = model.getObject(modelRow);
                model.removeRow(modelRow);
                new ContatoDAO().deleteById(c.getId());
            }
        } catch (DeleteFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }

    @Override
    public void showView() { manageController.showView(); }

}
