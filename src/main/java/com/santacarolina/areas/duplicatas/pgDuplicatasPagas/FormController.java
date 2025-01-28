package com.santacarolina.areas.duplicatas.pgDuplicatasPagas;

import java.awt.EventQueue;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.table.TableColumnModel;

import com.santacarolina.areas.documentos.frmDoc.DocForm;
import com.santacarolina.areas.duplicatas.common.DupTableModel;
import com.santacarolina.areas.duplicatas.common.DupView;
import com.santacarolina.areas.duplicatas.common.FilterController;
import com.santacarolina.areas.mainFrame.common.MainPaneController;
import com.santacarolina.areas.mainFrame.common.MainPaneControllerImpl;
import com.santacarolina.dao.DuplicataDAO;
import com.santacarolina.dao.ExtratoDAO;
import com.santacarolina.exceptions.DeleteFailException;
import com.santacarolina.exceptions.SaveFailException;
import com.santacarolina.interfaces.DoubleClickListener;
import com.santacarolina.model.Conciliacao;
import com.santacarolina.model.Duplicata;
import com.santacarolina.model.Extrato;
import com.santacarolina.util.CustomErrorThrower;

public class FormController implements MainPaneController<Duplicata> {

    private DupView view;
    private DupTableModel model;

    public FormController(DupView view, DupTableModel model) {
        new MainPaneControllerImpl<Duplicata>(view, model, this);
        this.view = view;
        this.model = model;
        new FilterController(view.getFilterView(), model.getFilterModel());
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
            Duplicata dup = model.getObject(row);
            DocForm.open(dup.getDocumento());
        });
    }

    @Override
    public void deleteBatch(List<Duplicata> list) {
        try {
            for (Duplicata duplicata : list) {
                if (duplicata.getListConciliacao() != null) mudaExtratos(duplicata.getListConciliacao());
            }
            new DuplicataDAO().deleteAll(list);
        } catch (DeleteFailException | SaveFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }

    private void mudaExtratos(List<Conciliacao> listConciliacao) throws SaveFailException {
        for (Conciliacao conciliacao : listConciliacao) {
            Extrato extrato = conciliacao.getExtrato();
            extrato.setConciliado(false);
            new ExtratoDAO().save(extrato);
        }
    }

}
