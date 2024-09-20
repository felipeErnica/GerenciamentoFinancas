package com.santacarolina.areas.frmDoc;

import com.santacarolina.interfaces.DoubleClickListener;
import com.santacarolina.model.beans.DocumentoFiscal;
import com.santacarolina.model.beans.Produto;
import com.santacarolina.model.tablemodels.ProdutoTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

public class ProdutosDocController {

    private ProdutoTableModel model;
    private ProdutosDocPanel panel;

    public ProdutosDocController(DocumentoFiscal doc, ProdutosDocPanel panel) {
        this.panel = panel;
        this.model = (ProdutoTableModel) panel.getTable().getModel();
        model.setDocumentoFiscal(doc);
        model.fireTableDataChanged();

        panel.getTable().setModel(model);
        panel.getTable().addMouseListener((DoubleClickListener) this::classificacaoContabil_doubleClick);
        panel.getAddButton().addActionListener(this::addButton_click);
        if (panel.getTable().getModel().getRowCount() == 0) model.addNewRow();
    }


    private void classificacaoContabil_doubleClick(MouseEvent e) {
        EventQueue.invokeLater(() -> {
            Point point = e.getPoint();
            int column = panel.getTable().columnAtPoint(point);
            int row = panel.getTable().rowAtPoint(point);
            if (column == 0) {
                Produto produto = model.getObject(row);
                new ClassificacaoController(produto,new ClassificacaoForm());
            }
        });
    }

    private void addButton_click(ActionEvent actionEvent) {
        EventQueue.invokeLater(model::addNewRow);
    }

}
