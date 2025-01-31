package com.santacarolina.areas.user.register;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.santacarolina.ui.AddView;

import net.miginfocom.swing.MigLayout;

public class FormView {

    private AddView addView;
    private JDialog dialog;
    private JPanel centerPanel;
    private JButton regiserButton;

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;

    public FormView() {
        addView = new AddView();
        dialog = addView.getDialog();
        regiserButton = addView.getAddButton();
        centerPanel = addView.getCenterPanel();
        init();
    }

    private void init() {
        dialog.setTitle("Registro de usuário");
        regiserButton.setText("Criar Usuário");

        JLabel usernameLabel = new JLabel("Nome de usuário: ");
        usernameField = new JTextField();
        usernameLabel.setLabelFor(usernameField);

        JLabel passwordLabel = new JLabel("Senha: ");
        passwordField = new JPasswordField();
        passwordLabel.setLabelFor(passwordField);

        JLabel confirmPasswordLabel = new JLabel("Confirmar senha: ");
        confirmPasswordField = new JPasswordField();
        confirmPasswordLabel.setLabelFor(confirmPasswordField);

        centerPanel.setLayout(new MigLayout("insets 15",
                "[][grow, fill]",
                "[][][]"));

        centerPanel.add(usernameLabel);
        centerPanel.add(usernameField, "wrap");
        centerPanel.add(passwordLabel);
        centerPanel.add(passwordField, "wrap");
        centerPanel.add(confirmPasswordLabel);
        centerPanel.add(confirmPasswordField);
    }

    public AddView getAddView() { return addView; }
    public JDialog getDialog() { return dialog; }
    public JPanel getCenterPanel() { return centerPanel; }
    public JButton getRegiserButton() { return regiserButton; }
    public JTextField getUsernameField() { return usernameField; }
    public JPasswordField getPasswordField() { return passwordField; }
    public JPasswordField getConfirmPasswordField() { return confirmPasswordField; }
    
}
