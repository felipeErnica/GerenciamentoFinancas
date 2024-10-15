package com.santacarolina.areas.duplicatas.pgDuplicatasPagas;

import com.santacarolina.areas.documentos.frmDoc.DocForm;
import com.santacarolina.areas.duplicatas.common.DupTableModel;
import com.santacarolina.areas.duplicatas.common.DupView;
import com.santacarolina.areas.mainFrame.common.MainPaneControllerImpl;
import com.santacarolina.interfaces.DoubleClickListener;
import com.santacarolina.model.Duplicata;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.MouseEvent;

public class FormController {

    private DupView view;
    private DupTableModel model;

    public FormController(DupView view, DupTableModel model) {
        new MainPaneControllerImpl(view, model);
        this.view = view;
        this.model = model;
        initComponents();
    }

    private void initComponents() {
        DupPagaRenderer renderer = new DupPagaRenderer(model);
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
            Duplicata dup = model.getObject(row).fromDTO();
            DocForm.open(dup.getDocumento());
        });
    }

}
