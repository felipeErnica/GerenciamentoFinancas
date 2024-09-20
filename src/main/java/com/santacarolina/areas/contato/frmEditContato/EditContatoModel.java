package com.santacarolina.areas.contato.frmEditContato;

import com.santacarolina.areas.contato.common.FormContatoModel;
import com.santacarolina.interfaces.NewFormModel;
import com.santacarolina.model.beans.Contato;

import java.beans.PropertyChangeListener;

public class EditContatoModel implements NewFormModel {

    private FormContatoModel baseModel;

    public EditContatoModel(Contato c) { this.baseModel = new FormContatoModel(c); }

    public FormContatoModel getBaseModel() { return baseModel; }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) { baseModel.addPropertyChangeListener(listener); }

    @Override
    public void fireInitialChanges() { baseModel.fireInitialChanges(); }

    public Contato getContato() { return baseModel.getContato(); }
    public String getCpf() { return baseModel.getCpf(); }
    public String getCnpj() { return baseModel.getCnpj(); }
    public String getIe() { return baseModel.getIe(); }
    public String getName() { return baseModel.getName(); }

    public void setName(String name) { baseModel.setName(name); }
    public void setDocsEnabled(boolean docsEnabled) { baseModel.setDocsEnabled(docsEnabled); }
    public void setCpf(String cpf) { baseModel.setCpf(cpf); }
    public void setCnpj(String cnpj) { baseModel.setCnpj(cnpj); }
    public void setIe(String ie) { baseModel.setIe(ie); }

}
