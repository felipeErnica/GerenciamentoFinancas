package com.santacarolina.areas.categoria.frmCategoria;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.jdesktop.swingx.combobox.EnumComboBoxModel;

import com.santacarolina.enums.FluxoCaixa;
import com.santacarolina.ui.AddView;

import net.miginfocom.swing.MigLayout;

public class FormView implements PropertyChangeListener {

    private AddView baseView;

    private JComboBox<FluxoCaixa> fluxoCaixaComboBox;
    private JTextField numeroTextField;
    private JTextField nomeTextField;

    public FormView(String dialogText, String buttonText) {
        baseView = new AddView();
        baseView.getDialog().setTitle(dialogText);
        baseView.getAddButton().setText(buttonText);
        init();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void init() {
        JPanel centerPanel = baseView.getCenterPanel();

        JLabel fluxoLabel = new JLabel("Fluxo de Caixa:");
        fluxoCaixaComboBox = new JComboBox<>(new EnumComboBoxModel(FluxoCaixa.class));
        AutoCompleteDecorator.decorate(fluxoCaixaComboBox);
        fluxoLabel.setLabelFor(fluxoCaixaComboBox);
        
        JLabel numeroLabel = new JLabel("Número da Etiqueta");
        numeroTextField = new JTextField();
        numeroLabel.setLabelFor(numeroTextField);

        JLabel nomeLabel = new JLabel("Nome da Categoria");
        nomeTextField = new JTextField();
        nomeLabel.setLabelFor(nomeTextField);

        centerPanel.setLayout(new MigLayout("insets 20",
            "[][grow, fill]",
            "[][][]"));

        centerPanel.add(fluxoLabel);
        centerPanel.add(fluxoCaixaComboBox, "wrap");
        centerPanel.add(numeroLabel);
        centerPanel.add(numeroTextField, "wrap");
        centerPanel.add(nomeLabel);
        centerPanel.add(nomeTextField);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case FormModel.NUMERO -> numeroTextField.setText((String) evt.getNewValue());
            case FormModel.FLUXO -> fluxoCaixaComboBox.setSelectedItem((FluxoCaixa) evt.getNewValue());
            case FormModel.NOME -> nomeTextField.setText((String) evt.getNewValue());
        }
    }

    public JComboBox<FluxoCaixa> getFluxoCaixaComboBox() { return fluxoCaixaComboBox; }
    public JTextField getNumeroTextField() { return numeroTextField; }
    public JTextField getNomeTextField() { return nomeTextField; }
    public JDialog getDialog() { return baseView.getDialog(); }
    public JButton getAddButton() { return baseView.getAddButton(); }

}

