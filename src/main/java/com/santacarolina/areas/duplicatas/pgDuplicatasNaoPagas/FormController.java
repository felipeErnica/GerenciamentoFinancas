package com.santacarolina.areas.duplicatas.pgDuplicatasNaoPagas;

import java.awt.EventQueue;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.RowSorter;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import com.santacarolina.areas.documentos.frmDoc.DocForm;
import com.santacarolina.areas.duplicatas.common.DupTableModel;
import com.santacarolina.areas.duplicatas.common.DupView;
import com.santacarolina.areas.duplicatas.common.FilterController;
import com.santacarolina.areas.mainFrame.common.MainPaneController;
import com.santacarolina.areas.mainFrame.common.MainPaneControllerImpl;
import com.santacarolina.dao.DuplicataDAO;
import com.santacarolina.dto.DuplicataDTO;
import com.santacarolina.exceptions.DeleteFailException;
import com.santacarolina.interfaces.DoubleClickListener;
import com.santacarolina.model.Duplicata;
import com.santacarolina.util.CustomErrorThrower;

public class FormController implements MainPaneController<DuplicataDTO> {

    private MainPaneControllerImpl<DuplicataDTO> controller;
    private DupView view;
    private DupTableModel model;
    private RowSorter<TableModel> sorter;

    public FormController(DupView view, DupTableModel model) {
        controller = new MainPaneControllerImpl<>(view, model, this);
        this.view = view;
        this.model = model;
        this.sorter = controller.getSorter();
        new FilterController(view.getFilterView(), model.getFilterModel());
        initComponents();
    }

    private void initComponents() {
        DupNaoPagaRenderer renderer = new DupNaoPagaRenderer(model);
        TableColumnModel columnModel = view.getTable().getColumnModel();
        columnModel.getColumn(0).setCellRenderer(renderer);
        columnModel.getColumn(1).setCellRenderer(renderer);
        columnModel.getColumn(2).setCellRenderer(renderer);
        columnModel.getColumn(3).setCellRenderer(renderer);
        columnModel.getColumn(4).setCellRenderer(renderer);
        columnModel.getColumn(5).setCellRenderer(renderer);
        view.getTable().addMouseListener((DoubleClickListener) this::table_doubleClick);
    }

    private void table_doubleClick(MouseEvent e) {
        EventQueue.invokeLater(() -> {
            int row = view.getTable().rowAtPoint(e.getPoint());
            int modelRow = sorter.convertRowIndexToModel(row);
            Duplicata dup = model.getObject(modelRow).fromDTO();
            DocForm.open(dup.getDocumento());
        });
    }

    @Override
    public void deleteBatch(List<DuplicataDTO> list) {
        try {
            new DuplicataDAO().deleteAll(list);
        } catch (DeleteFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }

}
