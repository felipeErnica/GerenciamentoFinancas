package com.santacarolina.areas.classificacao.frmClassificacao;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import com.formdev.flatlaf.FlatClientProperties;
import com.santacarolina.model.CategoriaContabil;
import com.santacarolina.ui.AddView;

import net.miginfocom.swing.MigLayout;

/**
 * FormView
 */
public class FormView implements PropertyChangeListener {

    private AddView baseView;

    private JComboBox<CategoriaContabil> categoriaContabilComboBox;
    private JButton newCategoriaButton;
    private JTextField nomeTextField;
    private JTextField numeroTextField;

    public FormView(String dialogTitle, String buttonText) {
        baseView = new AddView();
        baseView.getDialog().setTitle(dialogTitle);
        baseView.getAddButton().setText(buttonText);
        init();
    }

    private void init() {
        newCategoriaButton = new JButton("Adicionar Nova Categoria");

        JLabel categoriaLabel = new JLabel("Categoria Contábil:");
        categoriaContabilComboBox = new JComboBox<>();
        AutoCompleteDecorator.decorate(categoriaContabilComboBox);
        categoriaLabel.setLabelFor(categoriaContabilComboBox);

        JLabel numeroLabel = new JLabel("Número da Classificação:");
        numeroTextField = new JTextField();
        numeroTextField.putClientProperty(FlatClientProperties.SELECT_ALL_ON_FOCUS_POLICY, "always");
        numeroLabel.setLabelFor(numeroTextField);

        JLabel nomeLabel = new JLabel("Nome da Classificação:");
        nomeTextField = new JTextField();
        nomeTextField.putClientProperty(FlatClientProperties.SELECT_ALL_ON_FOCUS_POLICY, "always");
        nomeLabel.setLabelFor(nomeTextField);

        JPanel panel = baseView.getCenterPanel();
        panel.setLayout(new MigLayout("insets 20", 
            "[][grow, fill][]", 
            "[]20[][]"));

        panel.add(categoriaLabel);
        panel.add(categoriaContabilComboBox);
        panel.add(newCategoriaButton, "wrap");
        panel.add(nomeLabel);
        panel.add(nomeTextField, "wrap");
        panel.add(numeroLabel);
        panel.add(numeroTextField);

    }

    public JDialog getDialog() { return baseView.getDialog(); }
    public JButton getAddButton() { return baseView.getAddButton(); }
    public JPanel getCenterPanel() { return baseView.getCenterPanel(); }
    public JComboBox<CategoriaContabil> getCategoriaContabilComboBox() { return categoriaContabilComboBox; }
    public JButton getNewCategoriaButton() { return newCategoriaButton; }
    public JTextField getNomeTextField() { return nomeTextField; }
    public JTextField getNumeroTextField() { return numeroTextField; }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case FormModel.NOME -> nomeTextField.setText((String) evt.getNewValue());
            case FormModel.NUMERO-> numeroTextField.setText(Long.toString((Long) evt.getNewValue()));
            case FormModel.CATEGORIA -> categoriaContabilComboBox.setSelectedItem(evt.getNewValue());
            case FormModel.NUMERO_INVALIDO -> {
                boolean numeroInvalido = (Boolean) evt.getNewValue();
                if (numeroInvalido) {
                    numeroTextField.setText(null);
                    numeroTextField.putClientProperty(FlatClientProperties.OUTLINE, "error");
                } else {
                    numeroTextField.putClientProperty(FlatClientProperties.OUTLINE, null);
                }
            }
        }
    }

}
