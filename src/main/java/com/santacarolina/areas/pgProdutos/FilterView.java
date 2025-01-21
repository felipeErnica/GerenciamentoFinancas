package com.santacarolina.areas.pgProdutos;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.icons.FlatSearchIcon;
import com.santacarolina.dao.PastaDAO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.model.PastaContabil;

import net.miginfocom.swing.MigLayout;

/**
 * FilterView
 */
public class FilterView implements PropertyChangeListener {
    
    private JTextField dataInicio;
    private JTextField dataFim;
    private JComboBox<PastaContabil> pastaField;
    private JTextField emissorField;
    private JTextField tipoMercadoriaField;
    private JTextField descricaoField;

    public FilterView(JPanel filterPanel) throws FetchFailException { init(filterPanel); }

    private void init(JPanel filterPanel) throws FetchFailException {
        JLabel inicioLabel = new JLabel("Data Inicial:");
        dataInicio = new JTextField();
        dataInicio.putClientProperty(FlatClientProperties.SELECT_ALL_ON_FOCUS_POLICY, "always");
        inicioLabel.setLabelFor(dataInicio);

        JLabel fimLabel = new JLabel("Data Final:");
        dataFim = new JTextField();
        dataFim.putClientProperty(FlatClientProperties.SELECT_ALL_ON_FOCUS_POLICY, "always");
        fimLabel.setLabelFor(dataFim);

        emissorField = new JTextField();
        emissorField.putClientProperty(FlatClientProperties.SELECT_ALL_ON_FOCUS_POLICY, "always");
        emissorField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Pesquisar emitente...");
        emissorField.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_ICON, new FlatSearchIcon());

        tipoMercadoriaField = new JTextField();
        tipoMercadoriaField.putClientProperty(FlatClientProperties.SELECT_ALL_ON_FOCUS_POLICY, "always");
        tipoMercadoriaField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Pesquisar Tipo de mercadoria...");
        tipoMercadoriaField.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_ICON, new FlatSearchIcon());

        descricaoField = new JTextField();
        descricaoField.putClientProperty(FlatClientProperties.SELECT_ALL_ON_FOCUS_POLICY, "always");
        descricaoField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Pesquisar descrição...");
        descricaoField.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_ICON, new FlatSearchIcon());

        pastaField = new JComboBox<>();
        new PastaDAO().findAll().forEach(pasta -> pastaField.addItem(pasta));
        pastaField.setSelectedItem(null);
        pastaField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Selecionar pasta...");
        AutoCompleteDecorator.decorate(pastaField);
        
        filterPanel.setLayout(new MigLayout("insets 10",
            "[grow 50, fill][grow 20, fill][grow 20, fill][][grow 10, fill]",
            "[][]"));

        filterPanel.add(emissorField, "span 3");
        filterPanel.add(inicioLabel);
        filterPanel.add(dataInicio,"wrap");
        filterPanel.add(descricaoField);
        filterPanel.add(tipoMercadoriaField);
        filterPanel.add(pastaField);
        filterPanel.add(fimLabel);
        filterPanel.add(dataFim);
    }

    public JTextField getDataInicio() { return dataInicio; }
    public JTextField getDataFim() { return dataFim; }
    public JComboBox<PastaContabil> getPastaField() { return pastaField; }
    public JTextField getEmissorField() { return emissorField; }
    public JTextField getTipoMercadoriaField() { return tipoMercadoriaField; }
    public JTextField getDescricaoField() { return descricaoField; }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case FilterModel.DATA_FIM -> {
                LocalDate date = (LocalDate) evt.getNewValue();
                if (date == null) dataFim.setText(null);
                else dataFim.setText(date.format(DateTimeFormatter.ofPattern("dd/mm/yyyy")));
            }
            case FilterModel.DATA_INICIO -> {
                LocalDate date = (LocalDate) evt.getNewValue();
                if (date == null) dataInicio.setText(null);
                else dataInicio.setText(date.format(DateTimeFormatter.ofPattern("dd/mm/yyyy")));
            }
        }
    }

}
