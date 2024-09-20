package com.santacarolina.areas.bancario.extrato.frmAddExtrato;

import com.santacarolina.ui.EditTablePanel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class AddExtratoView {

    private JDialog dialog;
    private EditTablePanel tablePanel;
    private JTable table;
    private JButton addExtratos;

    public AddExtratoView() {
        initComponents();
    }

    private void initComponents() {
        dialog = new JDialog();
        dialog.setTitle("Adicionar Extrato");

        tablePanel = new EditTablePanel();
        table = tablePanel.getTable();

        addExtratos = new JButton("Adicionar Extratos");

        dialog.setLayout(new MigLayout("wrap, insets 20",
                "[grow, fill]",
                "[grow, fill][]"
        ));

        dialog.add(tablePanel.getTableScrollPane());
        dialog.add(addExtratos);
    }

    public JDialog getDialog() { return dialog; }
    public EditTablePanel getTablePanel() { return tablePanel; }
    public JTable getTable() { return table; }
    public JButton getAddExtratos() { return addExtratos; }

}
