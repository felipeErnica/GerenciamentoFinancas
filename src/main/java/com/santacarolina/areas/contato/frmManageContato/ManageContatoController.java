package com.santacarolina.areas.contato.frmManageContato;

import java.awt.event.MouseEvent;
import java.util.List;

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

@SuppressWarnings("unchecked")
public class ManageContatoController implements ManageController<Contato> {

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
    public void showView() { manageController.showView(); }

    @Override
    public void callDeleteDAO(List<Contato> list) {
        try {
            new ContatoDAO().deleteAll(list);
        } catch (DeleteFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }

}
