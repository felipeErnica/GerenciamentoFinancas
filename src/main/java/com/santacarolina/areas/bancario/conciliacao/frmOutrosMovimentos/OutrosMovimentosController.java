package com.santacarolina.areas.bancario.conciliacao.frmOutrosMovimentos;

import com.santacarolina.dao.ConciliacaoDAO;
import com.santacarolina.dao.ExtratoDao;
import com.santacarolina.enums.TipoMovimento;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.exceptions.SaveFailException;
import com.santacarolina.interfaces.Controller;
import com.santacarolina.interfaces.OnResize;
import com.santacarolina.model.beans.Conciliacao;
import com.santacarolina.model.beans.Extrato;
import com.santacarolina.ui.CurrencyCellRenderer;
import com.santacarolina.ui.DateCellRenderer;
import com.santacarolina.util.CustomErrorThrower;
import com.santacarolina.util.OptionDialog;
import com.santacarolina.util.ViewInvoker;
import org.jdesktop.swingx.combobox.ListComboBoxModel;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.util.ArrayList;
import java.util.List;

public class OutrosMovimentosController implements Controller {

    private ExtratoDao extratoDao;
    private ConciliacaoDAO dao;
    private OutrosMovimentosView view;
    private OutrosMovimentosModel model;

    public OutrosMovimentosController(OutrosMovimentosView view, OutrosMovimentosModel model) {
        this.view = view;
        this.model = model;
        extratoDao = new ExtratoDao();
        dao = new ConciliacaoDAO();
        initComponents();
    }

    private void initComponents() {
        view.getExtratoTable().setModel(model.getExtratoTableModel().getBaseModel());

        List<TipoMovimento> comboList = new ArrayList<>();
        for (TipoMovimento tipo : TipoMovimento.values()) {
            if (tipo != TipoMovimento.COMUM) comboList.add(tipo);
        }

        view.getMovimentosComboBox().setModel(new ListComboBoxModel<>(comboList));

        DateCellRenderer dateCellRenderer = new DateCellRenderer();
        dateCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        CurrencyCellRenderer currencyCellRenderer = new CurrencyCellRenderer();

        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        TableColumnModel extratoModel = view.getExtratoTable().getColumnModel();
        extratoModel.getColumn(0).setCellRenderer(dateCellRenderer);
        extratoModel.getColumn(1).setCellRenderer(cellRenderer);
        extratoModel.getColumn(4).setCellRenderer(currencyCellRenderer);

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

