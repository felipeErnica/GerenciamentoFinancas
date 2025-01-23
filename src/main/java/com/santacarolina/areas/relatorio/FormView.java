package com.santacarolina.areas.relatorio;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.DropMode;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.santacarolina.model.PastaContabil;
import com.santacarolina.ui.AddView;

import net.miginfocom.swing.MigLayout;

public class FormView implements PropertyChangeListener {

    private AddView baseView;

    private JDialog dialog;
    private JButton relatorioButton;
    private JPanel centerPanel;

    private JTextField dataFim;
    private JTextField dataInicio;
    private JList<PastaContabil> listaPasta;

    public FormView() {
        baseView = new AddView();
        dialog = baseView.getDialog();
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

        JLabel listaPastaLabel = new JLabel("Selecionar Pasta");
        listaPasta = new JList<>();
        listaPasta.setDropMode(DropMode.ON);
        listaPastaLabel.setLabelFor(listaPasta);
        
        centerPanel.setLayout(new MigLayout("insets 10",
            "[][grow, fill]20[][grow, fill]",
            "[][]"));

        centerPanel.add(dataInicioLabel);
        centerPanel.add(dataInicio);
        centerPanel.add(dataFimLabel);
        centerPanel.add(dataFim, "wrap");
        centerPanel.add(listaPastaLabel);
        centerPanel.add(listaPasta, "span");
    }

    public AddView getBaseView() { return baseView; }
    public JDialog getDialog() { return dialog; }
    public JButton getRelatorioButton() { return relatorioButton; }
    public JPanel getCenterPanel() { return centerPanel; }
    public JTextField getDataFim() { return dataFim; }
    public JTextField getDataInicio() { return dataInicio; }
    public JList<PastaContabil> getListaPasta() { return listaPasta; }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // TODO Auto-generated method stub
    }

}
