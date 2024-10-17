package com.santacarolina.areas.contato.frmAddContato;

import java.util.Optional;

import javax.swing.JOptionPane;

import org.apache.commons.lang3.StringUtils;

import com.santacarolina.areas.contato.common.FormContatoController;
import com.santacarolina.areas.contato.common.FormContatoModel;
import com.santacarolina.areas.contato.common.FormContatoView;
import com.santacarolina.areas.contato.common.IContatoController;
import com.santacarolina.dao.ContatoDAO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.model.Contato;
import com.santacarolina.util.CustomErrorThrower;
import com.santacarolina.util.OptionDialog;
import com.santacarolina.util.ViewInvoker;

public class FormController implements IContatoController {

    private ContatoDAO dao;

    private FormView view;
    private FormModel model;

    public FormController(FormView view, FormModel model) {
        this.view = view;
        this.model = model;
        new FormContatoController(this);
    }

    @Override
    public FormContatoModel getModel() { return model.getBaseModel(); }

    @Override
    public FormContatoView getView() { return view.getBaseView(); }

    @Override
    public void showView() { ViewInvoker.showView(view.getDialog()); }

    @Override
    public boolean nameExists() {
        try {
            Optional<Contato> sameName = dao.findByNome(model.getName());
            if (sameName.isPresent() && sameName.get().getId() != model.getContato().getId()) {
                int result = OptionDialog.showReplaceDialog("Já há um contato de Nome: " +
                        sameName.get().getNome() + ". " +
                        "Deseja substituí-lo por este?");
                if (result == JOptionPane.YES_OPTION) {
                    model.getContato().setId(sameName.get().getId());
                    return false;
                } else return true;
            } else return false;
        } catch (FetchFailException e) {
            CustomErrorThrower.throwError(e);
            return true;
        }
    }

    @Override
    public boolean docsExists() {
        try {

            Contato contato = model.getContato();
            Optional<Contato> sameDocs;

            if (contato.isEmptyDocuments()) return false;

            if (!StringUtils.isBlank(contato.getCpf())) {
                sameDocs = dao.findByCpf(model.getContato().getCpf());
                if (sameDocs.isPresent() && sameDocs.get().getId() != model.getContato().getId()) {
                    return replaceContato(sameDocs.get(), " - CPF: " + sameDocs.get().printCpf());
                }
            }

            if (!StringUtils.isBlank(contato.getCnpj())) {
                sameDocs = dao.findByCnpj(model.getContato().getCnpj());
                if (sameDocs.isPresent() && sameDocs.get().getId() != model.getContato().getId()) {
                    return replaceContato(sameDocs.get(), " - CNPJ: " + sameDocs.get().printCnpj());
                }
            }

            if (StringUtils.isBlank(contato.getIe())) {
                sameDocs = dao.findByIe(contato.getIe());
                if (sameDocs.isPresent() && sameDocs.get().getId() != model.getContato().getId()) {
                    return replaceContato(sameDocs.get(), " - IE: " + sameDocs.get().printIe());
                }
            }
            return false;
        } catch (FetchFailException e) {
            CustomErrorThrower.throwError(e);
            return true;
        }
    }

    private boolean replaceContato(Contato sameDocs, String doc) {
        int result = OptionDialog.showReplaceDialog("Há um contato com o mesmo documento: " +
                sameDocs.getNome() + doc + ". " +
                "Deseja substituí-lo por este?");
        if (result == JOptionPane.YES_OPTION) {
            model.getContato().setId(sameDocs.getId());
            return false;
        } else return true;
    }

}
