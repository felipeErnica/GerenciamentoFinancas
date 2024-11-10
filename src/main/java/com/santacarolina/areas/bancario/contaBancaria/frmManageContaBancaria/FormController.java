package com.santacarolina.areas.bancario.contaBancaria.frmManageContaBancaria;

import java.awt.EventQueue;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.RowSorter;

import com.santacarolina.areas.bancario.contaBancaria.frmContaBancaria.ContaForm;
import com.santacarolina.dao.ContaDAO;
import com.santacarolina.dto.ContaDTO;
import com.santacarolina.exceptions.DeleteFailException;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.ManageController;
import com.santacarolina.model.ContaBancaria;
import com.santacarolina.ui.ManageControllerImpl;
import com.santacarolina.util.CustomErrorThrower;
import com.santacarolina.util.ViewInvoker;

@SuppressWarnings("rawtypes")
public class FormController implements ManageController<ContaBancaria> {

    private FormView view;
    private ContaTableModel tableModel;
    private RowSorter sorter;

    public FormController(FormView view, ContaTableModel tableModel) {
        ManageControllerImpl<ContaBancaria> baseController = new ManageControllerImpl<>(tableModel, view, this);
        this.view = view;
        this.tableModel = tableModel;
        sorter = baseController.getSorter();
    }

    @Override
    public void showView() { ViewInvoker.showMaximizedView(view.getDialog()); }

    @Override
    public void table_onDoubleClick(MouseEvent e) {
        EventQueue.invokeLater(() -> {
            try {
                int row = view.getTable().rowAtPoint(e.getPoint());
                int modelRow = sorter.convertRowIndexToModel(row);
                ContaBancaria conta = tableModel.getObject(modelRow);
                ContaForm.open(conta);
                tableModel.requeryTable();
            } catch (FetchFailException ex) {
                CustomErrorThrower.throwError(ex);
            }
        });
    }

    @Override
    public void addButton_onClick() { 
        EventQueue.invokeLater(() -> {
            try {
                ContaForm.openNew();
                tableModel.requeryTable();
            } catch (FetchFailException e) {
                CustomErrorThrower.throwError(e);
            }
        }); 
    }

    @Override
    public void callDeleteDAO(List<ContaBancaria> list) {
        List<ContaDTO> listDTO = list.stream()
            .map(conta -> conta.toDTO())
            .collect(Collectors.toList());
        try {
            new ContaDAO().deleteAll(listDTO);
        } catch (DeleteFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }

}

