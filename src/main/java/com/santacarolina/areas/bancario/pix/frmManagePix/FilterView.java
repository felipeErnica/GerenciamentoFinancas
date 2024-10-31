package com.santacarolina.areas.bancario.pix.frmManagePix;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.icons.FlatSearchIcon;
import com.santacarolina.enums.TipoPix;
import com.santacarolina.interfaces.AbstractFilterView;

import net.miginfocom.swing.MigLayout;

/**
 * FiterView
 */
public class FilterView implements AbstractFilterView, PropertyChangeListener {

    private JTextField nome;
    private JTextField banco;
    private JTextField chave;
    private JTextField conta;
    private JTextField agencia;
    private JComboBox<TipoPix> tipoChave;

    public FilterView(JPanel filterPanel) { init(filterPanel); }

    private void init(JPanel filterPanel) {

        nome = new JTextField();
        nome.putClientProperty(FlatClientProperties.SELECT_ALL_ON_FOCUS_POLICY, "always");
        nome.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Pesquisar nome...");
        nome.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_ICON, new FlatSearchIcon());

        banco = new JTextField();
        banco.putClientProperty(FlatClientProperties.SELECT_ALL_ON_FOCUS_POLICY, "always");
        banco.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Pesquisar banco...");
        banco.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_ICON, new FlatSearchIcon());

        chave = new JTextField();
        chave.putClientProperty(FlatClientProperties.SELECT_ALL_ON_FOCUS_POLICY, "always");
        chave.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Pesquisar chave...");
        chave.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_ICON, new FlatSearchIcon());

        conta = new JTextField();
        conta.putClientProperty(FlatClientProperties.SELECT_ALL_ON_FOCUS_POLICY, "always");
        conta.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Pesquisar conta...");
        conta.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_ICON, new FlatSearchIcon());

        agencia = new JTextField();
        agencia.putClientProperty(FlatClientProperties.SELECT_ALL_ON_FOCUS_POLICY, "always");
        agencia.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Pesquisar agência...");
        agencia.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_ICON, new FlatSearchIcon());

        tipoChave = new JComboBox<>(TipoPix.values());
        AutoCompleteDecorator.decorate(tipoChave);
        tipoChave.putClientProperty(FlatClientProperties.SELECT_ALL_ON_FOCUS_POLICY, "always");
        tipoChave.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Selecionar Tipo de Chave...");
        tipoChave.setSelectedItem(null);

        filterPanel.setLayout(new MigLayout("",
            "[grow 70, fill][grow 15, fill][grow 15, fill]",
            "[][]"));

        filterPanel.add(nome);
        filterPanel.add(tipoChave);
        filterPanel.add(chave, "wrap");
        filterPanel.add(banco);
        filterPanel.add(agencia);
        filterPanel.add(conta);

    }

    public JTextField getNome() { return nome; }
    public JTextField getBanco() { return banco; }
    public JTextField getChave() { return chave; }
    public JTextField getConta() { return conta; }
    public JTextField getAgencia() { return agencia; }
    public JComboBox<TipoPix> getTipoChave() { return tipoChave; }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(FilterModel.CHAVE)) chave.setText((String) evt.getNewValue());
    }

}
