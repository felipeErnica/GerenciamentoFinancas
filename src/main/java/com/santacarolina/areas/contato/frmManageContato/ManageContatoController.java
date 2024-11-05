package com.santacarolina.areas.contato.frmManageContato;

import java.awt.EventQueue;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.RowSorter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

import com.santacarolina.areas.contato.common.ContatoForm;
import com.santacarolina.dao.ContatoDAO;
import com.santacarolina.exceptions.DeleteFailException;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.ManageController;
import com.santacarolina.model.Contato;
import com.santacarolina.ui.ManageControllerImpl;
import com.santacarolina.util.CustomErrorThrower;
import com.santacarolina.util.OptionDialog;

@SuppressWarnings("unchecked")
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
        new FilterController(view.getFilterView(), model.getFilterModel());
        model.getFilterModel().addPropertyChangeListener(view.getFilterView());
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
        ContatoForm.open(contato);
    }

    @Override
    public void addButton_onClick() { 
        ContatoForm.openNew();
        try {
            model.requeryTable();
        } catch (FetchFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }

    @Override
    public void deleteButton_onClick() {
        try {
            int[] rows = view.getTable().getSelectedRows();
            if (OptionDialog.showDeleteCascadeDialog(rows.length) != JOptionPane.YES_OPTION) return;
            for (int i = rows.length - 1; i >= 0; i--) {
                int viewRow = rows[i];
                int modelRow = sorter.convertRowIndexToModel(viewRow);
                Contato c = model.getObject(modelRow);
                model.removeRow(modelRow);
                new ContatoDAO().deleteById(c.getId());
            }
            model.requeryTable();
        } catch (DeleteFailException | FetchFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }

    @Override
    public void showView() { manageController.showView(); }

}
