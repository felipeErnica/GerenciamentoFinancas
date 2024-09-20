package com.santacarolina.areas.mainFrame.pgBanco;

import com.santacarolina.areas.mainFrame.common.MainPaneController;
import com.santacarolina.areas.mainFrame.common.MainPaneControllerImpl;
import com.santacarolina.dao.ContaDAO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.model.beans.ContaBancaria;
import com.santacarolina.ui.CurrencyCellRenderer;
import com.santacarolina.ui.DateCellRenderer;
import com.santacarolina.util.CustomErrorThrower;
import org.jdesktop.swingx.combobox.ListComboBoxModel;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;

public class ExtratoController implements MainPaneController {

    private ContaDAO contaDao;
    private MainPaneControllerImpl mainPaneController;
    private ExtratoView view;
    private ExtratoTableModel model;

    public ExtratoController(ExtratoTableModel model, ExtratoView view) throws FetchFailException {
        this.model = model;
        this.view = view;
        this.contaDao = new ContaDAO();
        this.mainPaneController = new MainPaneControllerImpl(view, this.model);
        initComponents();
    }

    private void initComponents() throws FetchFailException {

        DateCellRenderer dateCellRenderer = new DateCellRenderer();
        dateCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        CurrencyCellRenderer currencyCellRenderer = new CurrencyCellRenderer();

        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        TableColumnModel columnModel = view.getTable().getColumnModel();

        columnModel.getColumn(0).setCellRenderer(dateCellRenderer);
        columnModel.getColumn(1).setCellRenderer(cellRenderer);
        columnModel.getColumn(4).setCellRenderer(currencyCellRenderer);

        view.getContaComboBox().setModel(new ListComboBoxModel<>(contaDao.findAll()));
        view.getContaComboBox().addActionListener(e -> contaComboBox_afterUpdate());
    }

    private void contaComboBox_afterUpdate() {
        EventQueue.invokeLater(() -> {
            try {
                ContaBancaria conta = (ContaBancaria) view.getContaComboBox().getSelectedItem();
                model.setContaBancaria(conta);
            } catch (FetchFailException e) {
                CustomErrorThrower.throwError(e);
            }
        });
    }


}

