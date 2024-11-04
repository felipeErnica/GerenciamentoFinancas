package com.santacarolina.areas.duplicatas.common;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.icons.FlatSearchIcon;
import com.santacarolina.dao.ContaDAO;
import com.santacarolina.enums.TipoPagamento;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.model.ContaBancaria;

import net.miginfocom.swing.MigLayout;

/**
 * FilterView
 */
public class FilterView implements PropertyChangeListener {

    private JTextField dataInicio;
    private JTextField dataFim;
    private JComboBox<TipoPagamento> tipoPagamentoField;
    private JComboBox<ContaBancaria> contaField;
    private JTextField emissorField;

    public FilterView(JPanel filterPanel) throws FetchFailException {
        init(filterPanel);
    }

    private void init(JPanel filterPanel) throws FetchFailException {
        emissorField = new JTextField();
        emissorField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Pesquisar emissor...");
        emissorField.putClientProperty(FlatClientProperties.SELECT_ALL_ON_FOCUS_POLICY, "always");
        emissorField.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_ICON, new FlatSearchIcon());

        tipoPagamentoField = new JComboBox<>(TipoPagamento.values());
        tipoPagamentoField.setSelectedItem(null);
        AutoCompleteDecorator.decorate(tipoPagamentoField);
        tipoPagamentoField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Selecionar Tipo de pagamento...");

        contaField = new JComboBox<>();
        new ContaDAO().findAll().forEach(conta -> contaField.addItem(conta));
        contaField.setSelectedItem(null);
        AutoCompleteDecorator.decorate(contaField);
        contaField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Selecionar conta...");

        JLabel inicioLabel = new JLabel("Data Inicial:");
        dataInicio = new JTextField();
        dataInicio.putClientProperty(FlatClientProperties.SELECT_ALL_ON_FOCUS_POLICY, "always");
        inicioLabel.setLabelFor(dataInicio);

        JLabel fimLabel = new JLabel("Data Final:");
        dataFim = new JTextField();
        dataFim.putClientProperty(FlatClientProperties.SELECT_ALL_ON_FOCUS_POLICY, "always");
        fimLabel.setLabelFor(dataFim);

        filterPanel.setLayout(new MigLayout("insets 10",
                "[grow 40, fill][grow 40, fill]10[][grow 20, fill]",
                "[][]"));

        filterPanel.add(emissorField, "span 2");
        filterPanel.add(inicioLabel);
        filterPanel.add(dataInicio, "wrap");
        filterPanel.add(tipoPagamentoField);
        filterPanel.add(contaField);
        filterPanel.add(fimLabel);
        filterPanel.add(dataFim);
    }

    public JTextField getDataInicio() {
        return dataInicio;
    }

    public JTextField getDataFim() {
        return dataFim;
    }

    public JComboBox<TipoPagamento> getTipoPagamentoField() {
        return tipoPagamentoField;
    }

    public JComboBox<ContaBancaria> getContaField() {
        return contaField;
    }

    public JTextField getEmissorField() {
        return emissorField;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case FilterModel.DATA_INICIO -> {
                LocalDate date = (LocalDate) evt.getNewValue();
                dataInicio.setText(date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            }
            case FilterModel.DATA_FIM -> {
                LocalDate date = (LocalDate) evt.getNewValue();
                dataFim.setText(date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            }
        }
    }

}
