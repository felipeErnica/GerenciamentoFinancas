package com.santacarolina.areas.duplicatas.pgDuplicatasNaoPagas;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.santacarolina.enums.TipoPagamento;
import com.santacarolina.model.ContaBancaria;

/**
 * FilterView
 */
public class FilterView {

    private JTextField dataInicio;
    private JTextField dataFim;
    private JComboBox<TipoPagamento> tipoPagamentoField;
    private JComboBox<ContaBancaria> contaField;
    private JTextField emissorField;

    public FilterView(JPanel filterPanel) { init(filterPanel); }

    private void init(JPanel filterPanel) {
        emissorField = new JTextField();

    }

    public JTextField getDataInicio() { return dataInicio; }
    public JTextField getDataFim() { return dataFim; }
    public JComboBox<TipoPagamento> getTipoPagamentoField() { return tipoPagamentoField; }
    public JComboBox<ContaBancaria> getContaField() { return contaField; }
    public JTextField getEmissorField() { return emissorField; }

}
