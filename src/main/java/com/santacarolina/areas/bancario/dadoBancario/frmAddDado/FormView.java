package com.santacarolina.areas.bancario.dadoBancario.frmAddDado;

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
        baseView = new DadoBancarioFormView();
        baseView.getAddAccount().setText("Adicionar Conta");
        baseView.getDialog().setTitle("Adicionar Dado Bancário");
    }

    public DadoBancarioFormView getBaseView() { return baseView; }
    public JDialog getDialog() { return baseView.getDialog(); }
    public JButton getAddAccount() { return baseView.getAddAccount(); }
    public JComboBox<Contato> getContactComboBox() { return baseView.getContactComboBox(); }
    public JComboBox<Banco> getBankComboBox() { return baseView.getBankComboBox(); }
    public JTextField getAgencyTextField() { return baseView.getAgencyTextField(); }
    public JTextField getContaTextField() { return baseView.getContaTextField(); }
    public JCheckBox getPixCheckBox() { return baseView.getPixCheckBox(); }
    public JComboBox<TipoPix> getPixTypeComboBox() { return baseView.getPixTypeComboBox(); }
    public JTextField getPixKey() { return baseView.getPixKey(); }

    @Override
    public void propertyChange(PropertyChangeEvent evt) { baseView.propertyChange(evt); }

}
