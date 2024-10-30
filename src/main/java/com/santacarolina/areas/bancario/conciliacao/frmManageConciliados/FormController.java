package com.santacarolina.areas.bancario.conciliacao.frmManageConciliados;

import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.RowSorter;
import javax.swing.table.TableColumnModel;

import com.santacarolina.areas.bancario.conciliacao.frmConciliacao.ConciliacaoForm;
import com.santacarolina.dao.ConciliacaoDAO;
import com.santacarolina.dao.DuplicataDAO;
import com.santacarolina.dao.ExtratoDAO;
import com.santacarolina.dto.ConciliacaoDTO;
import com.santacarolina.exceptions.DeleteFailException;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.exceptions.SaveFailException;
import com.santacarolina.interfaces.ManageController;
import com.santacarolina.model.Conciliacao;
import com.santacarolina.model.Duplicata;
import com.santacarolina.model.Extrato;
import com.santacarolina.ui.ManageControllerImpl;
import com.santacarolina.util.CustomErrorThrower;
import com.santacarolina.util.OptionDialog;
import com.santacarolina.util.ViewInvoker;

/**
 * FormController
 */
@SuppressWarnings("unchecked")
public class FormController implements ManageController {

    private FormView view;
    private ConciliadosTableModel tableModel;
    private RowSorter<ConciliadosTableModel> sorter;

    public FormController(FormView view, ConciliadosTableModel tableModel) {
        this.view = view;
        this.tableModel = tableModel;
        ManageControllerImpl<ConciliacaoDTO> baseController = new ManageControllerImpl<>(tableModel, view, this);
        sorter = baseController.getSorter();
        new FilterController(view.getFilterView(), tableModel.getFilterModel());
        tableModel.getFilterModel().addPropertyChangeListener(view.getFilterView());
        init();
    }

    private void init() {
        ConciliacaoRenderer cellRenderer = new ConciliacaoRenderer();
        TableColumnModel columnModel = view.getTable().getColumnModel();
        columnModel.getColumn(0).setCellRenderer(cellRenderer);
        columnModel.getColumn(1).setCellRenderer(cellRenderer);
        columnModel.getColumn(2).setCellRenderer(cellRenderer);
        columnModel.getColumn(3).setCellRenderer(cellRenderer);
        columnModel.getColumn(4).setCellRenderer(cellRenderer);
        columnModel.getColumn(5).setCellRenderer(cellRenderer);
        columnModel.getColumn(6).setCellRenderer(cellRenderer);
        columnModel.getColumn(7).setCellRenderer(cellRenderer);
        columnModel.getColumn(8).setCellRenderer(cellRenderer);
        columnModel.getColumn(9).setCellRenderer(cellRenderer);
    }

    @Override
    public void showView() { ViewInvoker.showMaximizedView(view.getDialog()); }

    @Override
    public void table_onDoubleClick(MouseEvent e) {}

    @Override
    public void addButton_onClick() {
        try {
            new ConciliacaoForm();
            tableModel.requeryTable();
        } catch (FetchFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }

    @Override
    public void deleteButton_onClick() {
        int[] rows = view.getTable().getSelectedRows();
        int result = OptionDialog.showDeleteCascadeDialog(rows.length);
        if (result != JOptionPane.YES_OPTION) return;
        try {
            for (int i = rows.length - 1; i >= 0; i--) {

                int modelRow = sorter.convertRowIndexToModel(rows[i]);
                Conciliacao conciliacao = tableModel.getObject(modelRow).fromDTO();

                ConciliacaoDAO dao = new ConciliacaoDAO();
                
                List<Conciliacao> conciliacoesExtrato = dao.findByExtrato(conciliacao.getExtratoId());
                List<Conciliacao> conciliacoesDup = dao.findByExtrato(conciliacao.getExtratoId());

                if (conciliacoesExtrato.size() > 1) {
                    Extrato extrato = conciliacao.getExtrato();
                    extrato.setConciliated(false);
                    new ExtratoDAO().save(extrato);
                    deleteConciliacoesExtrato(conciliacoesExtrato);
                } else if (conciliacoesDup.size() > 1) {
                    Duplicata duplicata = conciliacao.getDuplicata();
                    duplicata.setPayed(false);
                    new DuplicataDAO().save(duplicata);
                    deleteConciliacoesDup(conciliacoesDup);
                } else {
                    if (conciliacao.getDuplicata() != null) {
                        Duplicata duplicata = conciliacao.getDuplicata();
                        duplicata.setPayed(false);
                        new DuplicataDAO().save(duplicata);
                    }
                    Extrato extrato = conciliacao.getExtrato();
                    extrato.setConciliated(false);
                    new ExtratoDAO().save(extrato);
                    dao.deleteById(conciliacao.getId());
                }

            }
            tableModel.requeryTable();
        } catch(SaveFailException | DeleteFailException | FetchFailException e) { 
            CustomErrorThrower.throwError(e);
        }
    }

    private void deleteConciliacoesDup(List<Conciliacao> list) throws SaveFailException, DeleteFailException {

        ConciliacaoDAO conciliacaoDAO = new ConciliacaoDAO();
        ExtratoDAO extratoDAO = new ExtratoDAO();
        
        for (Conciliacao conciliacao : list) {
            Extrato extrato = conciliacao.getExtrato();
            extrato.setConciliated(false);
            extratoDAO.save(extrato);
            conciliacaoDAO.deleteById(conciliacao.getId());
        }

    }

    private void deleteConciliacoesExtrato(List<Conciliacao> list) throws SaveFailException, DeleteFailException {

        ConciliacaoDAO conciliacaoDAO = new ConciliacaoDAO();
        DuplicataDAO duplicataDAO = new DuplicataDAO();
        
        for (Conciliacao conciliacao : list) {
            Duplicata duplicata = conciliacao.getDuplicata();
            if (duplicata == null) return;
            duplicata.setPayed(false);
            duplicataDAO.save(duplicata);
            conciliacaoDAO.deleteById(conciliacao.getId());
        }

    }

}
