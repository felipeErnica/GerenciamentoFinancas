package com.santacarolina.areas.contato.frmAddContato;

import com.santacarolina.areas.contato.common.FormContatoController;
import com.santacarolina.areas.contato.common.FormContatoModel;
import com.santacarolina.areas.contato.common.FormContatoView;
import com.santacarolina.areas.contato.common.IContatoController;
import com.santacarolina.dao.ContatoDao;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.model.beans.Contato;
import com.santacarolina.util.CustomErrorThrower;
import com.santacarolina.util.OptionDialog;
import com.santacarolina.util.ViewInvoker;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.swing.*;
import java.util.Optional;

public class AddContatoController implements IContatoController {

    private final Logger logger = LogManager.getLogger();

    private FormContatoController controller;
    private ContatoDao dao;
    private AddContatoView view;
    private AddContatoModel model;

    public AddContatoController(AddContatoView view, AddContatoModel model) {
        this.view = view;
        this.model = model;
        this.controller = new FormContatoController(this);
        this.dao = controller.getDao();
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