package com.santacarolina.areas.bancario.dadoBancario.frmManageDado;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.icons.FlatSearchIcon;
import com.santacarolina.model.Banco;

import net.miginfocom.swing.MigLayout;

/**
 * FilterView
 */
public class FilterView {

    private JTextField nomeField;
    private JComboBox<Banco> bancoField;
    private JTextField numeroContaField;
    private JTextField agenciaField;

    public FilterView(JPanel filterPanel) { init(filterPanel); }

    private void init(JPanel filterPanel) {
        nomeField = new JTextField();
        nomeField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Pesquisar nome...");
        nomeField.putClientProperty(FlatClientProperties.SELECT_ALL_ON_FOCUS_POLICY, "always");
        nomeField.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_ICON, new FlatSearchIcon());

        numeroContaField = new JTextField();
        numeroContaField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Pesquisar número da conta...");
        numeroContaField.putClientProperty(FlatClientProperties.SELECT_ALL_ON_FOCUS_POLICY, "always");
        numeroContaField.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_ICON, new FlatSearchIcon());

        agenciaField = new JTextField();
        agenciaField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Pesquisar agência...");
        agenciaField.putClientProperty(FlatClientProperties.SELECT_ALL_ON_FOCUS_POLICY, "always");
        agenciaField.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_ICON, new FlatSearchIcon());

        JLabel bancoLabel = new JLabel("Banco:");
        bancoField = new JComboBox<>();
        AutoCompleteDecorator.decorate(bancoField);
        bancoLabel.setLabelFor(bancoField);

        filterPanel.setLayout(new MigLayout("",
            "[grow 80, fill]10[grow 20, fill]",
            "[]10[]"));

        filterPanel.add(nomeField);
        filterPanel.add(agenciaField, "wrap");
        filterPanel.add(bancoLabel, "split 2, shrink");
        filterPanel.add(bancoField, "grow");
        filterPanel.add(numeroContaField);
        
    }

    public JTextField getNomeField() { return nomeField; }
    public JComboBox<Banco> getBancoField() { return bancoField; }
    public JTextField getNumeroContaField() { return numeroContaField; }
    public JTextField getAgenciaField() { return agenciaField; }

}
