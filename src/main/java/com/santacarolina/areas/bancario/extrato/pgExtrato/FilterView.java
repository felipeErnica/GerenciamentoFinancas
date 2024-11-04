package com.santacarolina.areas.bancario.extrato.pgExtrato;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.formdev.flatlaf.FlatClientProperties;

import net.miginfocom.swing.MigLayout;

/**
 * FilterView
 */
public class FilterView implements PropertyChangeListener {

    private JTextField dataInicio;
    private JTextField dataFim;

    public FilterView(JPanel filterPanel) { init(filterPanel); }

    private void init(JPanel filterPanel) {
        JLabel inicioLabel = new JLabel("Data Inicial:");
        dataInicio = new JTextField();
        dataInicio.putClientProperty(FlatClientProperties.SELECT_ALL_ON_FOCUS_POLICY, "always");
        inicioLabel.setLabelFor(dataInicio);

        JLabel fimLabel = new JLabel("Data Final:");
        dataFim = new JTextField();
        dataFim.putClientProperty(FlatClientProperties.SELECT_ALL_ON_FOCUS_POLICY, "always");
        fimLabel.setLabelFor(dataFim);

        filterPanel.setLayout(new MigLayout("insets 10", 
            "[][grow 10, fill]10[][grow 10, fill][grow 80]",
            "[]"));

        filterPanel.add(inicioLabel);
        filterPanel.add(dataInicio);
        filterPanel.add(fimLabel);
        filterPanel.add(dataFim);
    }

    public JTextField getDataInicio() { return dataInicio; }
    public JTextField getDataFim() { return dataFim; }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case FilterModel.DATA_INICIO -> {
                LocalDate date = (LocalDate) evt.getNewValue();
                if (date != null) dataInicio.setText(date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                else dataInicio.setText(null);
            }
            case FilterModel.DATA_FIM-> {
                LocalDate date = (LocalDate) evt.getNewValue();
                if (date != null) dataFim.setText(date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                else dataFim.setText(null);
            }
        }
    }

}
