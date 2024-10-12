package com.santacarolina.areas.pastaContabil.frmAddPastaContabil;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JTextField;

import com.santacarolina.areas.pastaContabil.common.PastaContabilView;
import com.santacarolina.model.ContaBancaria;

/**
 * AddPastaContabilView
 */
public class AddPastaContabilView {
    
    private PastaContabilView baseView;

    public AddPastaContabilView() {
        this.baseView = new PastaContabilView();
        baseView.getDialog().setTitle("Adicionar Pasta Contábil");
        baseView.getAddFolder().setText("Adicionar Pasta");
    }

    public PastaContabilView getBaseView() { return baseView; }
    public JDialog getDialog() { return baseView.getDialog(); }
    public JTextField getFolderTextField() { return baseView.getFolderTextField(); }
    public JTextField getPathTextField() { return baseView.getPathTextField(); }
    public JButton getSelectPathButton() { return baseView.getSelectPathButton(); }
    public JComboBox<ContaBancaria> getBankAccountComboBox() { return baseView.getBankAccountComboBox(); }
    public JButton getAddAccount() { return baseView.getAddAccount(); }
    public JButton getAddFolder() { return baseView.getAddFolder(); }

}
