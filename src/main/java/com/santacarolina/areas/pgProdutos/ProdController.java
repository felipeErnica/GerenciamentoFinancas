package com.santacarolina.areas.pgProdutos;

import com.santacarolina.areas.documentos.frmDoc.DocForm;
import com.santacarolina.areas.mainFrame.common.MainPaneController;
import com.santacarolina.areas.mainFrame.common.MainPaneControllerImpl;
import com.santacarolina.interfaces.DoubleClickListener;
import com.santacarolina.model.Produto;
import com.santacarolina.ui.CurrencyCellRenderer;
import com.santacarolina.ui.DateCellRenderer;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.MouseEvent;

public class ProdController implements MainPaneController {

    private ProdView view;
    private ProdTableModel model;

    public ProdController(ProdView view, ProdTableModel model) {
        this.view = view;
        this.model = model;
        initComponents();
    }

    /**
     * 
     */
    private void initComponents() {
        TableColumnModel columnModel = view.getTable().getColumnModel();

        DateCellRenderer dateCellRenderer = new DateCellRenderer();
        dateCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        CurrencyCellRenderer currencyCellRenderer = new CurrencyCellRenderer();

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);

        columnModel.getColumn(0).setCellRenderer(dateCellRenderer);
        columnModel.getColumn(5).setCellRenderer(renderer);
        columnModel.getColumn(6).setCellRenderer(renderer);
        columnModel.getColumn(7).setCellRenderer(currencyCellRenderer);
        columnModel.getColumn(8).setCellRenderer(currencyCellRenderer);

        view.getTable().addMouseListener((DoubleClickListener) this::table_doubleClick);
    }

    private void table_doubleClick(MouseEvent e) {
        EventQueue.invokeLater(() -> {
            int row = view.getTable().rowAtPoint(e.getPoint());
            Produto prod = model.getObject(row).fromDTO();
            DocForm.open(prod.getDocumento());
        });
    }

}
