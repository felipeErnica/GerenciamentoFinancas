package com.santacarolina.areas.relatorio;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.icons.FlatFileChooserNewFolderIcon;
import com.santacarolina.model.PastaContabil;
import com.santacarolina.ui.AddView;

import net.miginfocom.swing.MigLayout;

public class FormView implements PropertyChangeListener {

    private AddView baseView;

    private JDialog dialog;
    private JButton relatorioButton;
    private JPanel centerPanel;

    private JButton caminhoButton;
    private JTextField caminho;
    private JTextField dataFim;
    private JTextField dataInicio;
    private JList<PastaContabil> listaPasta;

    public FormView() {
        baseView = new AddView();
        dialog = baseView.getDialog();
        dialog.setTitle("Gerar Relatório");
        relatorioButton = baseView.getAddButton();
        relatorioButton.setText("Gerar Relatório");
        centerPanel = baseView.getCenterPanel();
        init();
    }

    private void init() {

        JLabel dataInicioLabel = new JLabel("Data Inicial:");
        dataInicio = new JTextField();
        dataInicioLabel.setLabelFor(dataInicio);

        JLabel dataFimLabel = new JLabel("Data Final:");
        dataFim = new JTextField();
        dataFimLabel.setLabelFor(dataFim);

        JLabel caminhoLabel = new JLabel("Selecionar caminho:");
        caminhoButton = new JButton(new FlatFileChooserNewFolderIcon());
        caminho = new JTextField();
        caminho.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_COMPONENT, caminhoButton);
        caminhoLabel.setLabelFor(caminho);

        JLabel listaPastaLabel = new JLabel("Selecionar Pasta");
        listaPasta = new JList<>();
        listaPasta.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        listaPastaLabel.setLabelFor(listaPasta);

        centerPanel.setLayout(new MigLayout("insets 10",
                "[][grow, fill]40[][grow, fill]",
                "[][]20[]"));

        centerPanel.add(dataInicioLabel);
        centerPanel.add(dataInicio);
        centerPanel.add(dataFimLabel);
        centerPanel.add(dataFim, "wrap");
        centerPanel.add(caminhoLabel);
        centerPanel.add(caminho, "span, wrap");
        centerPanel.add(listaPastaLabel);
        centerPanel.add(listaPasta, "span");
    }

    public AddView getBaseView() { return baseView; }
    public JDialog getDialog() { return dialog; }
    public JButton getRelatorioButton() { return relatorioButton; }
    public JPanel getCenterPanel() { return centerPanel; }
    public JTextField getDataFim() { return dataFim; }
    public JTextField getDataInicio() { return dataInicio; }
    public JTextField getCaminho() { return caminho; }
    public JList<PastaContabil> getListaPasta() { return listaPasta; } 
    public JButton getCaminhoButton() { return caminhoButton; }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case FormModel.DATA_INICIO -> {
                LocalDate data = (LocalDate) evt.getNewValue();
                if (data == null)
                    return;
                dataInicio.setText(data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            }
            case FormModel.DATA_FIM -> {
                LocalDate data = (LocalDate) evt.getNewValue();
                if (data == null)
                    return;
                dataFim.setText(data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            }
            case FormModel.DATA_INICIO_INVALIDO -> {
                boolean invalido = (boolean) evt.getNewValue();
                if (invalido)
                    dataInicio.putClientProperty(FlatClientProperties.OUTLINE, "error");
                else
                    dataInicio.putClientProperty(FlatClientProperties.OUTLINE, null);
            }
            case FormModel.DATA_FIM_INVALIDA -> {
                boolean invalido = (boolean) evt.getNewValue();
                if (invalido)
                    dataFim.putClientProperty(FlatClientProperties.OUTLINE, "error");
                else
                    dataFim.putClientProperty(FlatClientProperties.OUTLINE, null);
            }
        }
    }

}
