package com.santacarolina.areas.bancario.extrato.pgExtrato;

import com.santacarolina.areas.bancario.extrato.frmAddExtrato.AddExtratoForm;
import com.santacarolina.areas.mainFrame.common.MainPaneController;
import com.santacarolina.areas.mainFrame.common.MainPaneControllerImpl;
import com.santacarolina.dao.ContaDAO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.exceptions.OFXTransformerException;
import com.santacarolina.model.ContaBancaria;
import com.santacarolina.model.Extrato;
import com.santacarolina.util.CustomErrorThrower;
import com.santacarolina.util.OfxTransformer.OFXTransformerImpl;
import com.santacarolina.util.ValidatorViolations;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.jdesktop.swingx.combobox.ListComboBoxModel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        ExtratoRenderer cellRenderer = new ExtratoRenderer(model);
        TableColumnModel columnModel = view.getTable().getColumnModel();

        columnModel.getColumn(0).setCellRenderer(cellRenderer);
        columnModel.getColumn(1).setCellRenderer(cellRenderer);
        columnModel.getColumn(2).setCellRenderer(cellRenderer);
        columnModel.getColumn(3).setCellRenderer(cellRenderer);
        columnModel.getColumn(4).setCellRenderer(cellRenderer);
        columnModel.getColumn(5).setCellRenderer(cellRenderer);

        view.getContaComboBox().setModel(new ListComboBoxModel<>(contaDao.findAll()));
        view.getContaComboBox().setSelectedItem(null);
        view.getContaComboBox().addActionListener(e -> contaComboBox_afterUpdate());
        view.getAddExtrato().addActionListener(e -> addExtrato_onClick());
        view.getAddOFX().addActionListener(e -> addOFX_onClick());
    }

    private void addOFX_onClick() {
        EventQueue.invokeLater(() -> {
            try {
                if (model.getContaBancaria() == null) {
                    ValidatorViolations.violateEmptyFields("Conta Bancária");
                    return;
                }

                List<Extrato> list;
                OFXTransformerImpl ofxTransformer = new OFXTransformerImpl(model.getContaBancaria());
                Display display = new Display();
                Shell shell = new Shell(display);
                FileDialog fileDialog = new FileDialog(shell);
                fileDialog.setText("Adicionar OFX");
                fileDialog.setFilterNames(new String[]{"Arquivo OFX"});
                fileDialog.setFilterExtensions(new String[]{"*.ofx"});
                Optional<String> filePath = fileDialog.openDialog();

                if (filePath.isPresent()) {
                    list = ofxTransformer.getExtratoList(filePath.get());
                    new AddExtratoForm(model.getContaBancaria(), list);
                }

                display.dispose();
            } catch (OFXTransformerException e) {
                CustomErrorThrower.throwError(e);
            }
        });
    }

    private void addExtrato_onClick() {
        EventQueue.invokeLater(() -> {
            if (model.getContaBancaria() == null) {
                ValidatorViolations.violateEmptyFields("Conta Bancária");
                return;
            }
            new AddExtratoForm(model.getContaBancaria(), new ArrayList<>());
        });
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

