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

    private JTextField classificacaoField;
    private JTextField categoriaField;
    private JComboBox<FluxoCaixa> fluxoField;

    public FilterView(JPanel filterPanel) { init(filterPanel); }

    private void init(JPanel filterPanel) {
        classificacaoField = new JTextField();
        classificacaoField.putClientProperty(FlatClientProperties.SELECT_ALL_ON_FOCUS_POLICY, "always");
        classificacaoField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Pesquisar classificação...");
        classificacaoField.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_ICON, new FlatSearchIcon());

        categoriaField = new JTextField();
        categoriaField.putClientProperty(FlatClientProperties.SELECT_ALL_ON_FOCUS_POLICY, "always");
        categoriaField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Pesquisar categoria...");
        categoriaField.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_ICON, new FlatSearchIcon());

        fluxoField = new JComboBox<>(FluxoCaixa.values());
        fluxoField.setSelectedItem(null);
        AutoCompleteDecorator.decorate(fluxoField);
        fluxoField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Selecionar fluxo...");

        filterPanel.setLayout(new MigLayout("",
            "[grow 45, fill]10[grow 45, fill]10[grow 10, fill]"));

        filterPanel.add(classificacaoField);
        filterPanel.add(categoriaField);
        filterPanel.add(fluxoField);
    }

    public JTextField getClassificacaoField() { return classificacaoField; }
    public JTextField getCategoriaField() { return categoriaField; }
    public JComboBox<FluxoCaixa> getFluxoField() { return fluxoField; }

}
