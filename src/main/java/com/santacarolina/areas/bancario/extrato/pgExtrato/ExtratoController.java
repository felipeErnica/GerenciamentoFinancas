package com.santacarolina.areas.bancario.extrato.pgExtrato;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

import javax.swing.RowSorter;
import javax.swing.table.TableColumnModel;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.jdesktop.swingx.combobox.ListComboBoxModel;

import com.santacarolina.areas.bancario.extrato.frmAddExtrato.AddExtratoForm;
import com.santacarolina.areas.mainFrame.common.MainPaneController;
import com.santacarolina.areas.mainFrame.common.MainPaneControllerImpl;
import com.santacarolina.dao.ConciliacaoDAO;
import com.santacarolina.dao.ContaDAO;
import com.santacarolina.dao.DuplicataDAO;
import com.santacarolina.dao.ExtratoDAO;
import com.santacarolina.exceptions.DeleteFailException;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.exceptions.OFXTransformerException;
import com.santacarolina.exceptions.SaveFailException;
import com.santacarolina.model.Conciliacao;
import com.santacarolina.model.ContaBancaria;
import com.santacarolina.model.Duplicata;
import com.santacarolina.model.Extrato;
import com.santacarolina.util.CustomErrorThrower;
import com.santacarolina.util.ValidatorViolations;
import com.santacarolina.util.OfxTransformer.OFXTransformerImpl;

@SuppressWarnings("rawtypes")
public class ExtratoController implements MainPaneController<Extrato> {

    private ExtratoView view;
    private ExtratoModel model;
    private RowSorter sorter;

    public ExtratoController(ExtratoModel model, ExtratoView view) throws FetchFailException {
        this.model = model;
        this.view = view;
        MainPaneControllerImpl<Extrato> baseController = new MainPaneControllerImpl<>(view, model.getTableModel(), this);
        sorter = baseController.getSorter();
        new FilterController(view.getFilterView(), model.getTableModel().getFilterModel());
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() throws FetchFailException {
        ExtratoRenderer cellRenderer = new ExtratoRenderer(model.getTableModel(), sorter);
        TableColumnModel columnModel = view.getTable().getColumnModel();

        columnModel.getColumn(0).setCellRenderer(cellRenderer);
        columnModel.getColumn(1).setCellRenderer(cellRenderer);
        columnModel.getColumn(2).setCellRenderer(cellRenderer);
        columnModel.getColumn(3).setCellRenderer(cellRenderer);
        columnModel.getColumn(4).setCellRenderer(cellRenderer);
        columnModel.getColumn(5).setCellRenderer(cellRenderer);

        view.getContaComboBox().setModel(new ListComboBoxModel<>(new ContaDAO().findAll()));
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
                String filePath = fileDialog.open();

                if (!StringUtils.isBlank(filePath)) {
                    list = ofxTransformer.getExtratoList(filePath);
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
        try {
            ContaBancaria conta = (ContaBancaria) view.getContaComboBox().getSelectedItem();
            model.setContaBancaria(conta);
        } catch (FetchFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }

    @Override
    public void deleteBatch(List<Extrato> list) {
        try {

            for (Extrato extrato : list) {
                if (extrato.isConciliado()) mudaDuplicatas(extrato);
            }

            new ExtratoDAO().deleteAll(list);
        } catch (DeleteFailException | SaveFailException | FetchFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }

    private void mudaDuplicatas(Extrato extrato) throws SaveFailException, FetchFailException {
        List<Conciliacao> conciliacaoList = new ConciliacaoDAO().findByExtrato(extrato.getId());
        for (Conciliacao conciliacao : conciliacaoList) {
            Duplicata duplicata = conciliacao.getDuplicata();
            duplicata.setPaga(false);
            new DuplicataDAO().save(duplicata);
        }
    }

}

