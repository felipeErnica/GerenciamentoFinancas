package com.santacarolina.areas.bancario.conciliacao.frmOutrosMovimentos;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.TableColumnModel;

import org.jdesktop.swingx.combobox.ListComboBoxModel;

import com.santacarolina.areas.bancario.conciliacao.extratoConciliacao.ExtratoConciliacaoRenderer;
import com.santacarolina.dao.ConciliacaoDAO;
import com.santacarolina.dao.ExtratoDAO;
import com.santacarolina.enums.TipoMovimento;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.exceptions.SaveFailException;
import com.santacarolina.interfaces.Controller;
import com.santacarolina.interfaces.OnResize;
import com.santacarolina.model.Conciliacao;
import com.santacarolina.model.Extrato;
import com.santacarolina.util.CustomErrorThrower;
import com.santacarolina.util.OptionDialog;
import com.santacarolina.util.ViewInvoker;

public class OutrosMovimentosController implements Controller {

    private ExtratoDAO extratoDao;
    private ConciliacaoDAO dao;
    private OutrosMovimentosView view;
    private OutrosMovimentosModel model;

    public OutrosMovimentosController(OutrosMovimentosView view, OutrosMovimentosModel model) {
        this.view = view;
        this.model = model;
        extratoDao = new ExtratoDAO();
        dao = new ConciliacaoDAO();
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        view.getExtratoTable().setModel(model.getExtratoTableModel().getBaseModel());
        ExtratoConciliacaoRenderer cellRenderer = new ExtratoConciliacaoRenderer(model.getExtratoTableModel());
        TableColumnModel extratoModel = view.getExtratoTable().getColumnModel();
        extratoModel.getColumn(0).setCellRenderer(cellRenderer);
        extratoModel.getColumn(1).setCellRenderer(cellRenderer);
        extratoModel.getColumn(2).setCellRenderer(cellRenderer);
        extratoModel.getColumn(3).setCellRenderer(cellRenderer);
        extratoModel.getColumn(4).setCellRenderer(cellRenderer);
        List<TipoMovimento> comboList = new ArrayList<>();
        for (TipoMovimento tipo : TipoMovimento.values()) {
            if (tipo != TipoMovimento.COMUM) comboList.add(tipo);
        }
        view.getMovimentosComboBox().setModel(new ListComboBoxModel<>(comboList));
        view.getDialog().addComponentListener((OnResize) e -> dialog_onResize());
        view.getMovimentosComboBox().addActionListener(e -> movimentosComboBox_afterUpdate());
        view.getConciliarButton().addActionListener(e -> conciliarButton_onClick());
    }

    private void movimentosComboBox_afterUpdate() {
        TipoMovimento tipoMovimento = (TipoMovimento) view.getMovimentosComboBox().getSelectedItem();
        if (tipoMovimento != null) model.setTipoMovimento(tipoMovimento);
    }

    private void dialog_onResize() { view.formatColumns(); }

    private void conciliarButton_onClick() {
        try {
            List<Extrato> listExtrato = new ArrayList<>();
            List<Conciliacao> listConciliacao = new ArrayList<>();
            TipoMovimento tipoMovimento = model.getTipoMovimento();
            int[] extratoRows = view.getExtratoTable().getSelectedRows();

            if (extratoRows.length == 0) {
                OptionDialog.showErrorDialog("Selecione pelo menos um extrato!", "Informação Incompleta");
                return;
            } else if (tipoMovimento == null) {
                OptionDialog.showErrorDialog("Selecione o tipo de movimento!", "Informação Incompleta");
                return;
            }

            for (int extratoRow : extratoRows) {
                Extrato e = model.getExtrato(extratoRow);
                e.setConciliated(true);
                listExtrato.add(e);
                Conciliacao c = new Conciliacao(e, tipoMovimento);
                listConciliacao.add(c);
            }

            extratoDao.saveAll(listExtrato);
            dao.saveAll(listConciliacao);
            model.requeryTables();
        } catch (FetchFailException | SaveFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }

    @Override
    public void showView() { ViewInvoker.showMaximizedView(view.getDialog()); }

}

