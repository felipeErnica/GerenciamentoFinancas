package com.santacarolina.areas.documentos.frmDoc.prodPanel;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.santacarolina.ui.*;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.TableColumnModel;

public class ProdView {

    private JPanel mainPanel;
    private EditTablePanel editTablePanel;
    private JTable table;
    private JButton addButton;

    public ProdView() {
        editTablePanel = new EditTablePanel();
        table = editTablePanel.getTable();
        buildUI();
    }

    private void buildUI() {
        addButton = new ActionSVGButton("Adicionar Produto", new FlatSVGIcon("icon/add_icon.svg"));

        mainPanel = new JPanel(new MigLayout("insets 20",
                "[grow][fill]",
                "[]30[fill]"));

        mainPanel.add(addButton, "skip, wrap");
        mainPanel.add(editTablePanel.getTableScrollPane(), "span, growx");

    }

    public EditTablePanel getEditTablePanel() { return editTablePanel; }
    public JPanel getMainPanel() { return mainPanel; }
    public JTable getTable() { return table; }
    public JButton getAddButton() { return addButton; }

    public void formatColumns() {
        int width = editTablePanel.getTableScrollPane().getWidth()/100;
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(width*10);
        columnModel.getColumn(1).setPreferredWidth(width*50);
        columnModel.getColumn(2).setPreferredWidth(width*10);
        columnModel.getColumn(3).setPreferredWidth(width*10);
        columnModel.getColumn(4).setPreferredWidth(width*15);
        columnModel.getColumn(5).setPreferredWidth(width*15);
    }

}

