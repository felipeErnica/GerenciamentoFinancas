package com.santacarolina.areas.login;

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
    private JButton loginButton;

    private JTextField usernameField;
    private JPasswordField passwordField;

    public FormView() {
        addView = new AddView();
        dialog = addView.getDialog();
        loginButton = addView.getAddButton();
        centerPanel = addView.getCenterPanel();
        init();
    }

    private void init() {
        dialog.setTitle("Autenticação de usuário");
        loginButton.setText("Entrar");

        JLabel usernameLabel = new JLabel("Nome de usuário: ");
        usernameField = new JTextField();
        usernameLabel.setLabelFor(usernameField);

        JLabel passwordLabel = new JLabel("Senha: ");
        passwordField = new JPasswordField();
        passwordLabel.setLabelFor(passwordField);

        centerPanel.setLayout(new MigLayout("insets 15",
                "[][grow, fill]",
                "[][]"));

        centerPanel.add(usernameLabel);
        centerPanel.add(usernameField, "wrap");
        centerPanel.add(passwordLabel);
        centerPanel.add(passwordField);
    }

    public AddView getAddView() { return addView; }
    public JDialog getDialog() { return dialog; }
    public JPanel getCenterPanel() { return centerPanel; }
    public JButton getLoginButton() { return loginButton; }
    public JTextField getUsernameField() { return usernameField; }
    public JPasswordField getPasswordField() { return passwordField; }

}
