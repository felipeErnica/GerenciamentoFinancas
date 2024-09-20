package com.santacarolina.areas.contato.frmManageContato;

import com.formdev.flatlaf.FlatDarkLaf;
import com.santacarolina.dao.ContatoDao;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.util.CustomErrorThrower;

public class ManageContatoForm  {

    public static void main(String[] args) {
        FlatDarkLaf.setup();
        new ManageContatoForm();
    }

    public ManageContatoForm() {
        try {
            ManageContatoView view = new ManageContatoView();
            ContatoTableModel model = new ContatoTableModel(new ContatoDao().findAll());
            ManageContatoController controller = new ManageContatoController(model, view);
            controller.showView();
        } catch (FetchFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }

}
