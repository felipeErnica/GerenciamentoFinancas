package com.santacarolina.areas.contato.frmEditContato;

import com.santacarolina.model.Contato;

public class EditContatoForm {

    private EditContatoModel model;

    public EditContatoForm(Contato contato) {
        Contato contatoCopy = new Contato().ofContato(contato);
        this.model = new EditContatoModel(contatoCopy);
        EditContatoView view = new EditContatoView();
        EditContatoController controller = new EditContatoController(model, view);
        model.addPropertyChangeListener(view);
        controller.showView();
    }

}
