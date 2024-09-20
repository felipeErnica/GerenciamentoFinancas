package com.santacarolina.areas.contato.frmAddContato;

import com.santacarolina.model.beans.Contato;

public class AddContatoForm {
    public AddContatoForm() {
        AddContatoModel model = new AddContatoModel(new Contato());
        AddContatoView view = new AddContatoView();
        AddContatoController controller = new AddContatoController(view, model);
        model.addPropertyChangeListener(view);
        controller.showView();
    }
}
