package com.santacarolina.areas.classificacao.frmManageClassificacao;

import java.awt.event.MouseEvent;

import javax.swing.RowSorter;

import com.santacarolina.dto.ClassificacaoDTO;
import com.santacarolina.interfaces.ManageController;
import com.santacarolina.model.ClassificacaoContabil;
import com.santacarolina.ui.ManageControllerImpl;
import com.santacarolina.util.ViewInvoker;

/**
 * FormController
 */
public class FormController implements ManageController {

    private FormView view;
    private ClassificacaoTableModel tableModel;
    private RowSorter sorter;

    public FormController(FormView view, ClassificacaoTableModel tableModel) {
        this.view = view;
        this.tableModel = tableModel;
        ManageControllerImpl<ClassificacaoDTO> baseController = new ManageControllerImpl<>(tableModel, view, this);
        sorter = baseController.getSorter();
        init();
    }

    private void init() {
        view.getTable().setModel(tableModel.getBaseModel());
    }

    @Override
    public void showView() { ViewInvoker.showMaximizedView(view.getDialog()); }

    @Override
    public void table_onDoubleClick(MouseEvent e) {
        int rowIndex = view.getTable().rowAtPoint(e.getPoint());
        int modelRow = sorter.convertRowIndexToModel(rowIndex);
        ClassificacaoContabil classificacao = tableModel.getObject(modelRow).fromDTO();
    }

    @Override
    public void addButton_onClick() {
    }

    @Override
    public void deleteButton_onClick() {
    }
    
}
