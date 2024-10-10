package com.santacarolina.areas.bancario.dadoBancario.frmEditDado;

import com.santacarolina.areas.bancario.dadoBancario.common.DadoBancarioFormView;
import com.santacarolina.enums.TipoPix;
import com.santacarolina.model.Banco;
import com.santacarolina.model.Contato;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class FormView implements PropertyChangeListener {

    private DadoBancarioFormView baseView;

    public FormView() {
        this.baseView = new DadoBancarioFormView();
        baseView.getDialog().setTitle("Editar Dado Bancário");
        baseView.getAddAccount().setText("Salvar Conta");
    }

    public DadoBancarioFormView getBaseView() { return baseView; }
    public JDialog getDialog() { return baseView.getDialog(); }
    public JComboBox<Contato> getContactComboBox() { return baseView.getContactComboBox(); }
    public JComboBox<Banco> getBankComboBox() { return baseView.getBankComboBox(); }
    public JTextField getAgencyTextField() { return baseView.getAgencyTextField(); }
    public JTextField getContaTextField() { return baseView.getContaTextField(); }
    public JCheckBox getPixCheckBox() { return baseView.getPixCheckBox(); }
    public JComboBox<TipoPix> getPixTypeComboBox() { return baseView.getPixTypeComboBox(); }
    public JTextField getPixKey() { return baseView.getPixKey(); }
    public JButton getAddAccount() { return baseView.getAddAccount(); }

    @Override
    public void propertyChange(PropertyChangeEvent evt) { baseView.propertyChange(evt); }

}