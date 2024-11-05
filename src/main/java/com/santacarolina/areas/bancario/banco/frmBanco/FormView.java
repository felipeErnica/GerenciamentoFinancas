package com.santacarolina.areas.bancario.banco.frmBanco;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.santacarolina.ui.AddView;
import com.santacarolina.util.AppIcon;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class FormView implements PropertyChangeListener {

    private JDialog dialog;
    private JButton addBanco;

    private JTextField nomeBancoTextField;
    private JTextField apelidoBancoTextField;

    public FormView(String dialogTitle, String buttonText) {
        AddView view = new AddView();
        dialog = view.getDialog();
        dialog.setTitle(dialogTitle);
        addBanco = view.getAddButton();
        addBanco.setText(buttonText);
        initComponents();
    }

    private void initComponents() {
        FlatSVGIcon icon = AppIcon.paintIcon(new FlatSVGIcon("icon/bank_icon.svg"));
        dialog.setIconImage(icon.getImage());

        nomeBancoTextField = new JTextField();
        JLabel nomeLabel = new JLabel("Nome do Banco:");
        nomeBancoTextField.putClientProperty(FlatClientProperties.SELECT_ALL_ON_FOCUS_POLICY, "always");
        nomeLabel.setLabelFor(nomeBancoTextField);

        apelidoBancoTextField = new JTextField();
        JLabel apelidoLabel = new JLabel("Apelido do Banco:");
        apelidoBancoTextField.putClientProperty(FlatClientProperties.SELECT_ALL_ON_FOCUS_POLICY, "always");
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

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case FormModel.NOME_BANCO -> nomeBancoTextField.setText((String) evt.getNewValue());
            case FormModel.APELIDO -> apelidoBancoTextField.setText((String) evt.getNewValue());
        }
    }
}
