package com.santacarolina.areas.bancario.conciliacao.frmManageConciliados;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.jdesktop.swingx.combobox.EnumComboBoxModel;

import com.santacarolina.enums.TipoMovimento;
import com.santacarolina.model.Contato;
import com.santacarolina.model.PastaContabil;

import net.miginfocom.swing.MigLayout;

/**
 * FilterView
 */
public class FilterView implements PropertyChangeListener {

    private JTextField dataInicio;
    private JTextField dataFim;
    private JComboBox<PastaContabil> pastaContabil;
    private JComboBox<Contato> emissor;
    private JComboBox<TipoMovimento> tipoMovimento;

    public FilterView(JPanel filterPanel) { init(filterPanel); }

    @SuppressWarnings("unchecked")
    private void init(JPanel filterPanel) {
        
        JLabel dataInicioLabel = new JLabel("Data Inicial:");
        dataInicio = new JTextField();
        dataInicioLabel.setLabelFor(dataInicio);
        
        JLabel dataFimLabel = new JLabel("Data Final:");
        dataFim = new JTextField();
        dataFimLabel.setLabelFor(dataFim);

        JLabel pastaLabel = new JLabel("Pasta Contábil:");
        pastaContabil = new JComboBox<>();
        AutoCompleteDecorator.decorate(pastaContabil);
        pastaLabel.setLabelFor(pastaContabil);

        JLabel emissorLabel = new JLabel("Emissor:");
        emissor = new JComboBox<>();
        AutoCompleteDecorator.decorate(emissor);
        emissorLabel.setLabelFor(emissor);

        JLabel tipoLabel = new JLabel("Tipo de Movimento:");
        tipoMovimento = new JComboBox<>(new EnumComboBoxModel<>(TipoMovimento.class));
        AutoCompleteDecorator.decorate(tipoMovimento);
        tipoLabel.setLabelFor(tipoMovimento);

        filterPanel.setLayout(new MigLayout("insets 20",
            "[][grow, fill]10[][grow, fill]10[][grow, fill]",
            "[]15[]"));

        filterPanel.add(tipoLabel);
        filterPanel.add(tipoMovimento);
        filterPanel.add(pastaLabel);
        filterPanel.add(pastaContabil);
        filterPanel.add(dataInicioLabel);
        filterPanel.add(dataInicio, "wrap");
        filterPanel.add(emissorLabel);
        filterPanel.add(emissor, "span 3, grow");
        filterPanel.add(dataFimLabel);
        filterPanel.add(dataFim);
    }

    public JTextField getDataInicio() { return dataInicio; }
    public JTextField getDataFim() { return dataFim; }
    public JComboBox<PastaContabil> getPastaContabil() { return pastaContabil; }
    public JComboBox<Contato> getEmissor() { return emissor; }
    public JComboBox<TipoMovimento> getTipoMovimento() { return tipoMovimento; }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case FilterModel.DATA_INICIO-> {
                LocalDate localDate = (LocalDate) evt.getNewValue();
                if (localDate == null) return;
                dataInicio.setText(localDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            }
            case FilterModel.DATA_FIM -> {
                LocalDate localDate = (LocalDate) evt.getNewValue();
                if (localDate == null) return;
                dataFim.setText(localDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            }
        }
    }

}   
