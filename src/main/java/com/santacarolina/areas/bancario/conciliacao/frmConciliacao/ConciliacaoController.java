package com.santacarolina.areas.bancario.conciliacao.frmConciliacao;

import com.santacarolina.areas.bancario.conciliacao.frmOutrosMovimentos.OutrosMovimentosForm;
import com.santacarolina.dao.ConciliacaoDAO;
import com.santacarolina.dao.DuplicataDao;
import com.santacarolina.dao.ExtratoDao;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.exceptions.SaveFailException;
import com.santacarolina.interfaces.Controller;
import com.santacarolina.interfaces.OnResize;
import com.santacarolina.model.beans.Conciliacao;
import com.santacarolina.model.beans.Duplicata;
import com.santacarolina.model.beans.Extrato;
import com.santacarolina.ui.CurrencyCellRenderer;
import com.santacarolina.ui.DateCellRenderer;
import com.santacarolina.util.CustomErrorThrower;
import com.santacarolina.util.OptionDialog;
import com.santacarolina.util.ViewInvoker;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ConciliacaoController implements Controller {

    private ExtratoDao extratoDao;
    private DuplicataDao dupDao;
    private ConciliacaoDAO dao;
    private ConciliacaoView view;
    private ConciliacaoModel model;

    public ConciliacaoController(ConciliacaoView view, ConciliacaoModel model) {
        this.view = view;
        this.model = model;
        dupDao = new DuplicataDao();
        extratoDao = new ExtratoDao();
        dao = new ConciliacaoDAO();
        initComponents();
    }

    private void initComponents() {
        view.getExtratoTable().setModel(model.getExtratoTableModel().getBaseModel());
        view.getDuplicatasTable().setModel(model.getDupTableModel().getBaseModel());

        DateCellRenderer dateCellRenderer = new DateCellRenderer();
        dateCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        CurrencyCellRenderer currencyCellRenderer = new CurrencyCellRenderer();

        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        TableColumnModel extratoModel = view.getExtratoTable().getColumnModel();
        extratoModel.getColumn(0).setCellRenderer(dateCellRenderer);
        extratoModel.getColumn(1).setCellRenderer(cellRenderer);
        extratoModel.getColumn(4).setCellRenderer(currencyCellRenderer);

        TableColumnModel dupModel = view.getDuplicatasTable().getColumnModel();
        dupModel.getColumn(0).setCellRenderer(cellRenderer);
        dupModel.getColumn(1).setCellRenderer(dateCellRenderer);
        dupModel.getColumn(2).setCellRenderer(cellRenderer);
        dupModel.getColumn(3).setCellRenderer(cellRenderer);
        dupModel.getColumn(5).setCellRenderer(currencyCellRenderer);

        view.getDialog().addComponentListener((OnResize) e -> dialog_onResize());
        view.getOneDupForManyExtrato().addActionListener(e -> tableSelectionButtons_onClick(ConciliacaoModel.ONE_DUP_MANY_EXTRATO));
        view.getOneExtratoForManyDup().addActionListener(e -> tableSelectionButtons_onClick(ConciliacaoModel.ONE_EXTRATO_MANY_DUP));
        view.getOneForOneSelection().addActionListener(e -> tableSelectionButtons_onClick(ConciliacaoModel.ONE_TO_ONE));
        view.getOutrasButton().addActionListener(e -> outrasButton_onClick());
        view.getConciliarButton().addActionListener(e -> conciliarButton_onClick());
    }


    private void dialog_onResize() { view.formatColumns(); }

    private void conciliarButton_onClick() {
        try {
            int[] dupRows = view.getDuplicatasTable().getSelectedRows();
            int[] extratoRows = view.getExtratoTable().getSelectedRows();

            if (dupRows.length == 0 || extratoRows.length == 0) {
                OptionDialog.showErrorDialog("Selecione pelo menos um extrato e uma duplicata!", "Informação Incompleta");
                return;
            }

            if (dupRows.length == 1 && extratoRows.length == 1) singleConciliacao(dupRows[0], extratoRows[0]);
            else if (dupRows.length == 1 && extratoRows.length > 1) singleDupConciliacao(dupRows[0], extratoRows);
            else if (dupRows.length > 1 && extratoRows.length == 1) singleExtratoConciliacao(dupRows, extratoRows[0]);
        } catch (FetchFailException | SaveFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }

    private void singleConciliacao(int dupRow, int extratoRow) throws SaveFailException, FetchFailException {
        Duplicata duplicata = model.getDuplicata(dupRow);
        duplicata.setPayed(true);
        dupDao.save(duplicata);

        Extrato extrato = model.getExtrato(extratoRow);
        extrato.setConciliated(true);
        extratoDao.save(extrato);

        Conciliacao conciliacao = new Conciliacao(duplicata, extrato);
        dao.save(conciliacao);
        model.requeryTables();
    }

    private void singleDupConciliacao(int dupRow, int[] extratoRows) throws SaveFailException, FetchFailException {
        List<Conciliacao> conciliacaoList = new ArrayList<>();
        List<Extrato> extratoList = new ArrayList<>();

        Duplicata duplicata = model.getDuplicata(dupRow);
        duplicata.setPayed(true);

        for (int extratoRow : extratoRows) {
            Extrato extrato = model.getExtrato(extratoRow);
            extrato.setConciliated(true);
            extratoList.add(extrato);

            Conciliacao conciliacao = new Conciliacao(duplicata, extrato);
            conciliacaoList.add(conciliacao);
        }

        dupDao.save(duplicata);
        extratoDao.saveAll(extratoList);
        dao.saveAll(conciliacaoList);
        model.requeryTables();
    }

    private void singleExtratoConciliacao(int[] dupRows, int extratoRow) throws SaveFailException, FetchFailException {
        List<Conciliacao> conciliacaoList = new ArrayList<>();
        List<Duplicata> listDup = new ArrayList<>();

        Extrato extrato = model.getExtrato(extratoRow);
        extrato.setConciliated(true);

        for (int dupRow : dupRows) {
            Duplicata dup = model.getDuplicata(dupRow);
            dup.setPayed(true);
            Conciliacao conciliacao = new Conciliacao(dup, extrato);
            conciliacaoList.add(conciliacao);
            listDup.add(dup);
        }

        extratoDao.save(extrato);
        dupDao.saveAll(listDup);
        dao.saveAll(conciliacaoList);
        model.requeryTables();
    }

    private void outrasButton_onClick() { EventQueue.invokeLater(() -> new OutrosMovimentosForm());   }
    private void tableSelectionButtons_onClick(String mode) { EventQueue.invokeLater(() -> model.setTableSelection(mode)); }

    @Override
    public void showView() { ViewInvoker.showMaximizedView(view.getDialog()); }

}
