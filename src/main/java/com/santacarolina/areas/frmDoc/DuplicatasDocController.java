package com.santacarolina.areas.frmDoc;

import com.santacarolina.enums.TipoPagamento;
import com.santacarolina.interfaces.DoubleClickListener;
import com.santacarolina.model.beans.DocumentoFiscal;
import com.santacarolina.model.beans.Duplicata;
import com.santacarolina.model.tablemodels.DuplicataTableModel;
import com.santacarolina.util.FileManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

public class DuplicatasDocController {

    private static Logger logger = LogManager.getLogger();

    private TipoPagamento tipoPagamento;
    private DuplicataTableModel model;
    private DuplicatasDocPanel panel;
    private DocumentoFiscal doc;

    public DuplicatasDocController(DocumentoFiscal doc, DuplicatasDocPanel panel) {
        this.panel = panel;
        this.model = (DuplicataTableModel) panel.getTable().getModel();
        this.doc = doc;
        model.setDocumentoFiscal(doc);
        model.fireTableDataChanged();

        panel.getTable().addMouseListener((DoubleClickListener) this::caminhoBoleto_doubleClick);
        panel.getPagtoComboBox().addActionListener(this::pagtoComboBox_afterUpdate);
        panel.getAddButton().addActionListener(this::addButton_click);
        panel.getAddDadoBancario().addActionListener(this::addDadoBancario_Click);
        if (panel.getTable().getModel().getRowCount() == 0) model.addNewRow();
    }

    private void addDadoBancario_Click(ActionEvent e) {
        if (tipoPagamento == TipoPagamento.PIX) new AddPixDupController(doc,new AddPixDuplicataForm()).showView();
        if (tipoPagamento == TipoPagamento.TED) new AddTedDupController(doc, new AddTedDuplicataForm()).showView();
    }

    private void pagtoComboBox_afterUpdate(ActionEvent e) {
        tipoPagamento = (TipoPagamento) panel.getPagtoComboBox().getSelectedItem();
        model.getDuplicataList().forEach(d -> d.setTipoPagamento(tipoPagamento));
        panel.getAddDadoBancario().setEnabled(tipoPagamento == TipoPagamento.PIX || tipoPagamento == TipoPagamento.TED);
        model.fireTableDataChanged();
    }

    private void addButton_click(ActionEvent e) {
        Duplicata d = new Duplicata();
        d.setTipoPagamento(tipoPagamento);
        model.addRow(d);
    }

    private void caminhoBoleto_doubleClick(MouseEvent e){
        Point point = e.getPoint();
        int column = panel.getTable().columnAtPoint(point);
        int row = panel.getTable().rowAtPoint(point);
        if (column == 5 && tipoPagamento == TipoPagamento.BOLETO) {
            Duplicata dup = model.getObject(row);
            if (dup.getBoletoCaminho() == null || dup.getBoletoCaminho().isEmpty()) {
                String path = FileManager.getFile().orElse("");
                model.getDuplicataList().forEach(d -> d.setBoletoCaminho(path));
                model.fireTableDataChanged();
            }
            else FileManager.openFile(dup.getBoletoCaminho());
        }
    }

}
