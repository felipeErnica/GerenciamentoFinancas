package com.santacarolina.areas.classificacao.frmClassificacao;

import java.awt.EventQueue;
import org.jdesktop.swingx.combobox.ListComboBoxModel;

import com.santacarolina.areas.categoria.frmCategoria.CategoriaForm;
import com.santacarolina.dao.CategoriaDAO;
import com.santacarolina.dao.ClassificacaoDAO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.exceptions.SaveFailException;
import com.santacarolina.interfaces.AfterUpdateListener;
import com.santacarolina.interfaces.Controller;
import com.santacarolina.model.CategoriaContabil;
import com.santacarolina.util.CustomErrorThrower;
import com.santacarolina.util.OptionDialog;
import com.santacarolina.util.ViewInvoker;

/**
 * FormController
 */
@SuppressWarnings("unchecked")
public class FormController implements Controller {

    private FormView view;
    private FormModel model;

    public FormController(FormView view, FormModel model) throws FetchFailException {
        this.view = view;
        this.model = model;
        initComponents();
    }

    private void initComponents() throws FetchFailException {
        view.getCategoriaContabilComboBox().setModel(new ListComboBoxModel<>(new CategoriaDAO().findAll()));
        view.getCategoriaContabilComboBox().addActionListener(e -> categoriaContabil_afterUpdate());
        view.getNumeroTextField().addFocusListener((AfterUpdateListener) e -> numeroText_afterUpdate());
        view.getNomeTextField().addFocusListener((AfterUpdateListener) e -> nomeText_afterUpdate());
        view.getNewCategoriaButton().addActionListener(e -> newCategoriaButton_onClick());
        view.getAddButton().addActionListener(e -> addButton_onClick());
    }

    private void newCategoriaButton_onClick() { EventQueue.invokeLater(CategoriaForm::openNew); }
    private void nomeText_afterUpdate() { model.setNome(view.getNomeTextField().getText()); }
    private void numeroText_afterUpdate() { model.setNumero(view.getNumeroTextField().getText()); }

    private void categoriaContabil_afterUpdate() {
        CategoriaContabil categoriaContabil = (CategoriaContabil) view.getCategoriaContabilComboBox().getSelectedItem();
        model.setCategoriaContabil(categoriaContabil);
    }

    private void addButton_onClick() {
        try {
            if (!ClassificacaoValidator.validate(model)) return;
            new ClassificacaoDAO().save(model.getClassificacao());
            OptionDialog.showSuccessSaveMessage();
        } catch (FetchFailException | SaveFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }

    @Override
    public void showView() { ViewInvoker.showView(view.getDialog()); }

}
