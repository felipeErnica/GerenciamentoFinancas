package com.santacarolina.areas.documentos.pgDocumentos;

import com.santacarolina.areas.documentos.frmDoc.DocForm;
import com.santacarolina.areas.mainFrame.common.MainPaneController;
import com.santacarolina.areas.mainFrame.common.MainPaneControllerImpl;
import com.santacarolina.dao.DocumentoDAO;
import com.santacarolina.dto.DocumentoDTO;
import com.santacarolina.exceptions.DeleteFailException;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.DoubleClickListener;
import com.santacarolina.model.DocumentoFiscal;
import com.santacarolina.ui.CurrencyCellRenderer;
import com.santacarolina.ui.DateCellRenderer;
import com.santacarolina.util.CustomErrorThrower;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import java.awt.event.MouseEvent;
import java.util.List;

public class PageController implements MainPaneController<DocumentoDTO> {

    private MainPaneControllerImpl<DocumentoDTO> controller;
    private DocTableModel model;
    private PageView view;
    private RowSorter<TableModel> sorter;

    public PageController(DocTableModel model, PageView view) {
        this.controller = new MainPaneControllerImpl<>(view, model, this);
        this.model = model;
        this.view = view;
        this.sorter = controller.getSorter();
        new FilterController(view.getFilterView(), model.getFilterModel());
        init();
    }

    private void init() {
        DateCellRenderer dateRenderer = new DateCellRenderer();
        dateRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        CurrencyCellRenderer currencyRenderer = new CurrencyCellRenderer();
        DefaultTableCellRenderer defaultRenderer = new DefaultTableCellRenderer();
        defaultRenderer.setHorizontalAlignment(SwingConstants.LEFT);

        TableColumnModel columnModel = view.getTable().getColumnModel();
        columnModel.getColumn(0).setCellRenderer(defaultRenderer);
        columnModel.getColumn(1).setCellRenderer(dateRenderer);
        columnModel.getColumn(4).setCellRenderer(defaultRenderer);
        columnModel.getColumn(6).setCellRenderer(currencyRenderer);

        view.getTable().addMouseListener((DoubleClickListener) this::table_doubleClick);

    }

    private void table_doubleClick(MouseEvent e) {
        try {
            int row = view.getTable().rowAtPoint(e.getPoint());
            int rowModel = sorter.convertRowIndexToModel(row);
            DocumentoFiscal doc = model.getDocumento(rowModel);
            DocForm.open(doc);
        } catch (FetchFailException ex) {
            CustomErrorThrower.throwError(ex);
        }
    }

    @Override
    public void deleteBatch(List<DocumentoDTO> list) {
        DocumentoDAO dao = new DocumentoDAO();
        for (DocumentoDTO dto : list) {
            try {
                System.out.println(dto.toString());
                dao.delete(dto);
            } catch (DeleteFailException e) {
                CustomErrorThrower.throwError(e);
                return;
            }
        }
    }

}
