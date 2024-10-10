package com.santacarolina.areas.documentos.frmDoc.frmAddPix;

import com.santacarolina.areas.bancario.pix.frmAddPix.AddPixForm;
import com.santacarolina.dao.ContatoDAO;
import com.santacarolina.dao.PixDAO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.Controller;
import com.santacarolina.interfaces.OnSelectListener;
import com.santacarolina.model.ChavePix;
import com.santacarolina.model.Contato;
import com.santacarolina.util.ViewInvoker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdesktop.swingx.combobox.ListComboBoxModel;

import java.awt.*;

public class FormController implements Controller {

    private final Logger logger = LogManager.getLogger(FormController.class);

    private final PixDAO pixDAO = new PixDAO();
    private final ContatoDAO contatoDAO = new ContatoDAO();
    private FormView view;
    private FormModel model;

    public FormController(FormView view, FormModel model) throws FetchFailException {
        this.view = view;
        this.model = model;
        initComponents();
    }

    public void initComponents() throws FetchFailException {
        view.getContatoComboBox().setModel(new ListComboBoxModel<>(contatoDAO.findAll()));

        view.getContatoComboBox().addActionListener(e -> contatoComboBox_afterUpdate());
        view.getChaveComboBox().addActionListener(e -> chaveComboBox_afterUpdate());
        view.getAgenciaTextField().addFocusListener((OnSelectListener) e -> agenciaTextField_onFocus());
        view.getBancoTextField().addFocusListener((OnSelectListener) e -> bancoTextField_onFocus());
        view.getContaTextField().addFocusListener((OnSelectListener) e -> contaTextField_onFocus());
        view.getAddNewPix().addActionListener(e -> addNewPixButton_onClick());

        view.getAddButton().addActionListener(e -> addButton_onClick());
    }

    private void addButton_onClick() {
        model.getDuplicataList().forEach( d -> d.setPix(model.getChave()));
        view.getDialog().dispose();
    }

    private void addNewPixButton_onClick() { EventQueue.invokeLater(AddPixForm::new); }
    private void bancoTextField_onFocus() { view.getBancoTextField().selectAll(); }
    private void contaTextField_onFocus() { view.getContaTextField().selectAll(); }
    private void agenciaTextField_onFocus() { view.getAgenciaTextField().selectAll(); }

    private void chaveComboBox_afterUpdate() {
        ChavePix chavePix = (ChavePix) view.getChaveComboBox().getSelectedItem();
        model.setChave(chavePix);
    }

    private void contatoComboBox_afterUpdate() {
        Contato contato = (Contato) view.getContatoComboBox().getSelectedItem();
        model.setContato(contato);
    }

    public void showView() { ViewInvoker.showView(view.getDialog()); }

}