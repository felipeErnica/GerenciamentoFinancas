package com.santacarolina.areas.documentos.frmDoc.prodPanel;

import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

import com.santacarolina.areas.documentos.frmDoc.frmClassificacao.FrmClassificacao;
import com.santacarolina.interfaces.DoubleClickListener;
import com.santacarolina.interfaces.OnResize;
import com.santacarolina.model.Produto;

public class ProdController {

    private ProdutoTableModel model;
    private ProdView view;

    public ProdController(ProdView view, ProdutoTableModel model) {
        this.view = view;
        this.model = model;
        init();
    }

    private void init() {
        view.getTable().setModel(model.getBaseModel());
        view.getMainPanel().addComponentListener((OnResize) e -> view.formatColumns());

        DefaultTableCellRenderer defaultRenderer = new DefaultTableCellRenderer();
        defaultRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        TableColumnModel columnModel = view.getTable().getColumnModel();
        columnModel.getColumn(0).setCellRenderer(defaultRenderer);
        columnModel.getColumn(2).setCellRenderer(defaultRenderer);
        columnModel.getColumn(3).setCellRenderer(defaultRenderer);

        view.getTable().addMouseListener((DoubleClickListener) this::classificacaoContabil_doubleClick);
        view.getAddButton().addActionListener(e -> addButton_onClick());
        if (view.getTable().getModel().getRowCount() == 0) model.addNewRow();
    }

    private void addButton_onClick() { EventQueue.invokeLater(() -> model.addNewRow()); }

    private void classificacaoContabil_doubleClick(MouseEvent e) {
        Point point = e.getPoint();
        int column = view.getTable().columnAtPoint(point);
        int row = view.getTable().rowAtPoint(point);
        if (column == 0) {
            Produto produto = model.getObject(row);
            FrmClassificacao.open(produto);
            System.out.println(produto);
        }
    }

}
