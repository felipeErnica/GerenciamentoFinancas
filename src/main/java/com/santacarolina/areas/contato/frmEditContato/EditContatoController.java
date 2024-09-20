package com.santacarolina.areas.contato.frmEditContato;

import com.santacarolina.areas.contato.common.FormContatoController;
import com.santacarolina.areas.contato.common.FormContatoModel;
import com.santacarolina.areas.contato.common.FormContatoView;
import com.santacarolina.areas.contato.common.IContatoController;
import com.santacarolina.dao.ContatoDao;
import com.santacarolina.exceptions.DeleteFailException;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.model.beans.Contato;
import com.santacarolina.util.CustomErrorThrower;
import com.santacarolina.util.OptionDialog;
import org.apache.commons.lang3.StringUtils;
import javax.swing.*;
import java.util.Optional;

public class EditContatoController implements IContatoController {

    private ContatoDao dao;
    private FormContatoController commonController;
    private EditContatoModel model;
    private EditContatoView view;

    public EditContatoController(EditContatoModel model, EditContatoView view) {
        this.view = view;
        this.model = model;
        commonController = new FormContatoController(this);
        this.dao = commonController.getDao();
    }

    public FormContatoModel getModel() { return model.getBaseModel(); }
    public FormContatoView getView() { return view.getBaseView(); }

    public boolean nameExists() {
        try {
            Optional<Contato> sameName = dao.findByNome(model.getName());
            if (sameName.isPresent() && sameName.get().getId() != model.getContato().getId()) {
                int result = OptionDialog.showReplaceDialog("Já há um contato de Nome: " +
                        sameName.get().getNome() + ". " +
                        "Deseja substituí-lo por este?");
                if (result == JOptionPane.YES_OPTION) {
                    dao.deleteById(model.getContato().getId());
                    model.getContato().setId(sameName.get().getId());
                    return false;
                } else return true;
            } else return false;
        } catch (FetchFailException | DeleteFailException e) {
            CustomErrorThrower.throwError(e);
            return true;
        }
    }

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
        } catch (FetchFailException | DeleteFailException e) {
            CustomErrorThrower.throwError(e);
            return true;
        }
    }

    private boolean replaceContato(Contato sameDocs, String doc) throws DeleteFailException {
        int result = OptionDialog.showReplaceDialog("Há um contato com o mesmo documento: " +
                sameDocs.getNome() + doc + ". " +
                "Deseja substituí-lo por este?");
        if (result == JOptionPane.YES_OPTION) {
            dao.deleteById(model.getContato().getId());
            model.getContato().setId(sameDocs.getId());
            return false;
        } else return true;
    }

    @Override
    public void showView() { commonController.showView(); }

}
