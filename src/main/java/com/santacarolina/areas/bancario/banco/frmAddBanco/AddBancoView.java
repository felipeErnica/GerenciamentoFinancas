package com.santacarolina.areas.bancario.banco.frmAddBanco;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.santacarolina.ui.AddView;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class AddBancoView {

    private JDialog dialog;
    private JButton addBanco;

    private JTextField nomeBancoTextField;
    private JTextField apelidoBancoTextField;

    public AddBancoView() {
        AddView view = new AddView();
        dialog = view.getDialog();
        addBanco = view.getAddButton();
        initComponents();
    }

    private void initComponents() {

        FlatSVGIcon icon = new FlatSVGIcon("icon/bank_icon.svg");

        dialog.setTitle("Adicionar Banco");
        dialog.setIconImage(icon.getImage());
        addBanco.setText("Adicionar Banco");

        nomeBancoTextField = new JTextField();
        JLabel nomeLabel = new JLabel("Nome do Banco:");
        nomeLabel.setLabelFor(nomeBancoTextField);

        apelidoBancoTextField = new JTextField();
        JLabel apelidoLabel = new JLabel("Apelido do Banco:");
        apelidoLabel.setLabelFor(apelidoBancoTextField);

        JPanel centerPane = new JPanel(new MigLayout("insets 25, wrap",
                "[right][400!, fill]",
                "[][]"
        ));

        centerPane.add(nomeLabel);
        centerPane.add(nomeBancoTextField);
        centerPane.add(apelidoLabel);
        centerPane.add(apelidoBancoTextField);

        dialog.add(centerPane, BorderLayout.CENTER);
    }

    public JDialog getDialog() { return dialog; }
    public JButton getAddBanco() { return addBanco; }
    public JTextField getNomeBancoTextField() { return nomeBancoTextField; }
    public JTextField getApelidoBancoTextField() { return apelidoBancoTextField; }

}
