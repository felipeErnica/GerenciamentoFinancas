package com.santacarolina.areas.contato.frmAddContato;

import com.santacarolina.areas.contato.common.FormContatoModel;
import com.santacarolina.interfaces.ViewUpdater;
import com.santacarolina.model.Contato;
import java.beans.PropertyChangeListener;

public class FormModel implements ViewUpdater {

    private Contato contato;
    private FormContatoModel baseModel;

    public FormModel(Contato contato) {
        this.contato = contato;
        this.baseModel = new FormContatoModel(contato);
    }

    public FormContatoModel getBaseModel() { return baseModel; }
    public Contato getContato() { return contato; }
    public String getName() { return baseModel.getName(); }
    public Contato getContatoSaved() { return baseModel.getContatoSaved(); }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) { baseModel.addPropertyChangeListener(listener); }

    @Override
    public void fireInitialChanges() { baseModel.fireInitialChanges(); }

}