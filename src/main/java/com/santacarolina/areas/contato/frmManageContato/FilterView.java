package com.santacarolina.areas.contato.frmManageContato;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;
import javax.swing.JTextField;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.icons.FlatSearchIcon;
import com.santacarolina.util.DocConversor;

import net.miginfocom.swing.MigLayout;

/**
 * FilterView
 */
public class FilterView implements PropertyChangeListener {

    private JTextField nomeSearch;
    private JTextField cpfField;
    private JTextField cnpjField;
    private JTextField ieField;
    
    public FilterView(JPanel filterPanel) { init(filterPanel); }

    private void init(JPanel filterPanel) {

        nomeSearch = new JTextField();
        nomeSearch.putClientProperty(FlatClientProperties.SELECT_ALL_ON_FOCUS_POLICY, "always");
        nomeSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Pesquisar nome...");
        nomeSearch.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_ICON, new FlatSearchIcon());

        cpfField = new JTextField();
        cpfField.putClientProperty(FlatClientProperties.SELECT_ALL_ON_FOCUS_POLICY, "always");
        cpfField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Pesquisar CPF...");
        cpfField.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_ICON, new FlatSearchIcon());

        cnpjField = new JTextField();
        cnpjField.putClientProperty(FlatClientProperties.SELECT_ALL_ON_FOCUS_POLICY, "always");
        cnpjField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Pesquisar CNPJ...");
        cnpjField.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_ICON, new FlatSearchIcon());

        ieField = new JTextField();
        ieField.putClientProperty(FlatClientProperties.SELECT_ALL_ON_FOCUS_POLICY, "always");
        ieField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Pesquisar IE...");
        ieField.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_ICON, new FlatSearchIcon());

        filterPanel.setLayout(new MigLayout("",
            "[grow, fill]10[grow, fill]10[grow, fill]",
            "[]10[]"));

        filterPanel.add(nomeSearch, "span, wrap");
        filterPanel.add(cpfField);
        filterPanel.add(cnpjField);
        filterPanel.add(ieField);

    }

    public JTextField getNomeSearch() { return nomeSearch; }
    public JTextField getCpfField() { return cpfField; }
    public JTextField getCnpjField() { return cnpjField; }
    public JTextField getIeField() { return ieField; }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case FilterModel.CPF -> {
                String doc = (String) evt.getNewValue();
                if (DocConversor.isValidFormat(doc, DocConversor.CPF_FORMAT)) {
                    cpfField.setToolTipText(DocConversor.docFormat(doc, DocConversor.CPF_FORMAT));
                }
            }
            case FilterModel.CNPJ -> {
                String doc = (String) evt.getNewValue();
                if (DocConversor.isValidFormat(doc, DocConversor.CNPJ_FORMAT)) {
                    cnpjField.setToolTipText(DocConversor.docFormat(doc, DocConversor.CNPJ_FORMAT));
                }
            }
            case FilterModel.IE -> {
                String doc = (String) evt.getNewValue();
                if (DocConversor.isValidFormat(doc, DocConversor.CPF_FORMAT)) {
                    ieField.setToolTipText(DocConversor.docFormat(doc, DocConversor.IE_FORMAT));
                }
            }
        }
    }

}
