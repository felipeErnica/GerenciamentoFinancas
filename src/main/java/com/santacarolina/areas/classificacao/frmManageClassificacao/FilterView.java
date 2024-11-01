package com.santacarolina.areas.classificacao.frmManageClassificacao;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.icons.FlatSearchIcon;
import com.santacarolina.enums.FluxoCaixa;

import net.miginfocom.swing.MigLayout;

/**
 * FilterView
 */
public class FilterView {

    private JTextField categoriaField;
    private JComboBox<FluxoCaixa> fluxoCaixaField;

    public FilterView(JPanel filterPanel) { init(filterPanel); }

    private void init(JPanel filterPanel) {
        categoriaField = new JTextField();
        categoriaField.putClientProperty(FlatClientProperties.SELECT_ALL_ON_FOCUS_POLICY_ALWAYS, "always");
        categoriaField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Pesquisar categoria...");
        categoriaField.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_ICON, new FlatSearchIcon());

        fluxoCaixaField = new JComboBox<>(FluxoCaixa.values());
        AutoCompleteDecorator.decorate(fluxoCaixaField);
        fluxoCaixaField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Selecionar fluxo de caixa...");

        filterPanel.setLayout(new MigLayout("",
            "[grow 70, fill][grow 30, fill]"));

        filterPanel.add(categoriaField);
        filterPanel.add(fluxoCaixaField);
    }

}
