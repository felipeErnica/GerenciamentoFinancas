package com.santacarolina.areas.user.login;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.santacarolina.ui.AddView;
import com.santacarolina.util.AppIcon;

import net.miginfocom.swing.MigLayout;

public class FormView {

    private AddView addView;
    private JDialog dialog;
    private JPanel centerPanel;
    private JButton loginButton;

    private JButton createUserButton;
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

        createUserButton = new JButton("Criar Novo Usuário", AppIcon.paintIcon(new FlatSVGIcon("icon/add_icon.svg")));

        centerPanel.setLayout(new MigLayout("insets 15",
                "[][grow, fill][fill]",
                "[][]"));

        centerPanel.add(usernameLabel);
        centerPanel.add(usernameField);
        centerPanel.add(createUserButton, "spany, wrap");
        centerPanel.add(passwordLabel);
        centerPanel.add(passwordField);
    }

    public AddView getAddView() { return addView; }
    public JDialog getDialog() { return dialog; }
    public JPanel getCenterPanel() { return centerPanel; }
    public JButton getLoginButton() { return loginButton; }
    public JTextField getUsernameField() { return usernameField; }
    public JPasswordField getPasswordField() { return passwordField; }
    public JButton getCreateUserButton() { return createUserButton; }

}
