package com.santacarolina.areas.documentos.frmDoc.dupPanel;

import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

import com.santacarolina.areas.documentos.frmDoc.frmAddPix.AddPixDup;
import com.santacarolina.areas.documentos.frmDoc.frmAddTed.AddTedDup;
import com.santacarolina.dto.DuplicataDTO;
import com.santacarolina.enums.TipoPagamento;
import com.santacarolina.interfaces.DoubleClickListener;
import com.santacarolina.interfaces.OnResize;
import com.santacarolina.util.FileManager;

public class DupController {

    private DupView view;
    private DupModel model;

    public DupController(DupView view, DupModel model) {
        this.view = view;
        this.model = model;
        model.addPropertyChangeListener(view);
        init();
    }

    private void init() {
        view.getTable().setModel(model.getTableModel().getBaseModel());
        view.getMainPanel().addComponentListener((OnResize) e -> view.formatColumns());

        DefaultTableCellRenderer defaultRenderer = new DefaultTableCellRenderer();
        defaultRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        TableColumnModel columnModel = view.getTable().getColumnModel();
        columnModel.getColumn(0).setCellRenderer(defaultRenderer);
        columnModel.getColumn(1).setCellRenderer(defaultRenderer);
        columnModel.getColumn(2).setCellRenderer(defaultRenderer);
        columnModel.getColumn(3).setCellRenderer(defaultRenderer);

        view.getTable().addMouseListener((DoubleClickListener) this::caminhoBoleto_doubleClick);
        view.getPagtoComboBox().addActionListener(e -> pagtoComboBox_afterUpdate());
        view.getAddButton().addActionListener(e -> addButton_onClick());
        view.getAddDadoBancario().addActionListener(e -> addDadoBancario_onClick());
        if (view.getTable().getModel().getRowCount() == 0) model.addNewRow();
    }

    private void addDadoBancario_onClick() {
        EventQueue.invokeLater(() -> {
            if (model.getTipoPagamento() == TipoPagamento.PIX) AddPixDup.open(model.getTableModel().getDuplicataList());
            else if (model.getTipoPagamento() == TipoPagamento.TED) AddTedDup.open(model.getTableModel().getDuplicataList());
        });
    }

    private void pagtoComboBox_afterUpdate() {
        TipoPagamento tipoPagamento = (TipoPagamento) view.getPagtoComboBox().getSelectedItem();
        model.setTipoPagamento(tipoPagamento);
    }

    private void addButton_onClick() { EventQueue.invokeLater(() -> model.addNewRow()); }

    private void caminhoBoleto_doubleClick(MouseEvent e){
        Point point = e.getPoint();
        int column = view.getTable().columnAtPoint(point);
        int row = view.getTable().rowAtPoint(point);
        if (column == 5 && model.getTipoPagamento() == TipoPagamento.BOLETO) {
            DuplicataDTO dup = model.getObject(row);
            if (dup.getBoletoCaminho() == null || dup.getBoletoCaminho().isEmpty()) {
                String path = FileManager.getFile().orElse("");
                dup.setBoletoCaminho(path);
            }
            else FileManager.openFile(dup.getBoletoCaminho());
        }
    }

}
