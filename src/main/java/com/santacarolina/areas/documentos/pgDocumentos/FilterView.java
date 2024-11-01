package com.santacarolina.areas.documentos.pgDocumentos;

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
import com.santacarolina.dao.PastaDAO;
import com.santacarolina.enums.TipoDoc;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.model.PastaContabil;

import net.miginfocom.swing.MigLayout;

/**
 * FilterView
 */
public class FilterView implements PropertyChangeListener {

    private JTextField emissorField;
    private JTextField numDocField;
    private JComboBox<TipoDoc> tipoDocField;
    private JComboBox<PastaContabil> pastaField;
    private JTextField dataInicioField;
    private JTextField dataFinalField;

    public FilterView(JPanel filterPane) throws FetchFailException { init(filterPane); }

    private void init(JPanel filterPane) throws FetchFailException {
        emissorField = new JTextField();
        emissorField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Pesquisar emissor...");
        emissorField.putClientProperty(FlatClientProperties.SELECT_ALL_ON_FOCUS_POLICY, "always");
        emissorField.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_ICON, new FlatSearchIcon());
        
        numDocField = new JTextField();
        numDocField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Pesquisar Número do Documento...");
        numDocField.putClientProperty(FlatClientProperties.SELECT_ALL_ON_FOCUS_POLICY, "always");
        numDocField.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_ICON, new FlatSearchIcon());
        
        tipoDocField = new JComboBox<>(TipoDoc.values());
        tipoDocField.setSelectedItem(null);
        AutoCompleteDecorator.decorate(tipoDocField);
        tipoDocField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Selecionar Tipo de Documento...");

        pastaField = new JComboBox<>();
        new PastaDAO().findAll().forEach(pasta -> pastaField.addItem(pasta));
        pastaField.setSelectedItem(null);
        AutoCompleteDecorator.decorate(pastaField);
        pastaField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Selecionar Pasta Contábil...");

        JLabel inicioLabel = new JLabel("Data Inicial:");
        dataInicioField = new JTextField();
        dataInicioField.putClientProperty(FlatClientProperties.SELECT_ALL_ON_FOCUS_POLICY, "always");
        inicioLabel.setLabelFor(dataInicioField);

        JLabel finalLabel = new JLabel("Data Final:");
        dataFinalField = new JTextField();
        dataFinalField.putClientProperty(FlatClientProperties.SELECT_ALL_ON_FOCUS_POLICY, "always");
        finalLabel.setLabelFor(dataFinalField);

        filterPane.setLayout(new MigLayout("insets 10",
            "[grow, fill]10[grow, fill]10[grow, fill]10[][grow, fill]",
            "[]10[]"));

        filterPane.add(emissorField, "span 3");
        filterPane.add(inicioLabel);
        filterPane.add(dataInicioField, "wrap");
        filterPane.add(numDocField);
        filterPane.add(tipoDocField);
        filterPane.add(pastaField);
        filterPane.add(finalLabel);
        filterPane.add(dataFinalField);

    }

    public JTextField getEmissorField() { return emissorField; }
    public JTextField getNumDocField() { return numDocField; }
    public JComboBox<TipoDoc> getTipoDocField() { return tipoDocField; }
    public JComboBox<PastaContabil> getPastaField() { return pastaField; }
    public JTextField getDataInicioField() { return dataInicioField; }
    public JTextField getDataFinalField() { return dataFinalField; }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case FilterModel.DATA_INICIO -> {
                LocalDate date = (LocalDate) evt.getNewValue();
                if (date != null) dataInicioField.setText(date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            }
            case FilterModel.DATA_FIM -> {
                LocalDate date = (LocalDate) evt.getNewValue();
                if(date != null) dataFinalField.setText(date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            }
        }
    }

}
