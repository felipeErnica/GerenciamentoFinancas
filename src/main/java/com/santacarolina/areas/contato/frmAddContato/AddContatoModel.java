package com.santacarolina.areas.contato.frmAddContato;

import com.santacarolina.areas.contato.common.FormContatoModel;
import com.santacarolina.interfaces.NewFormModel;
import com.santacarolina.model.beans.Contato;
import java.beans.PropertyChangeListener;

public class AddContatoModel implements NewFormModel {

    private Contato contato;
    private FormContatoModel baseModel;

    public AddContatoModel(Contato contato) {
        this.contato = contato;
        this.baseModel = new FormContatoModel(contato);
    }

    public FormContatoModel getBaseModel() { return baseModel; }
    public Contato getContato() { return contato; }
    public String getName() { return baseModel.getName(); }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) { baseModel.addPropertyChangeListener(listener); }

    @Override
    public void fireInitialChanges() { baseModel.fireInitialChanges(); }

}