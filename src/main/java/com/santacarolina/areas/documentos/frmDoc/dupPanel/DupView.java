package com.santacarolina.areas.documentos.frmDoc.dupPanel;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.santacarolina.enums.TipoPagamento;
import com.santacarolina.ui.*;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.swingx.combobox.EnumComboBoxModel;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class DupView implements PropertyChangeListener {

    private EditTablePanel editTablePanel;
    private JPanel mainPanel;
    private JTable table;
    private JComboBox<TipoPagamento> pagtoComboBox;
    private JTextField valorTotalText;
    private JButton addDadoBancario;
    private JButton addButton;

    public DupView() {
        editTablePanel = new EditTablePanel();
        table = editTablePanel.getTable();
        buildUI();
    }

    @SuppressWarnings("unchecked")
    private void buildUI() {
        addButton = new ActionSVGButton("Adicionar Duplicata", new FlatSVGIcon("icon/add_icon.svg"));
        addDadoBancario = new ActionSVGButton("Adicionar Dado Bancário", new FlatSVGIcon("icon/add_info_pagto_icon.svg"));
        addDadoBancario.setEnabled(false);

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        pagtoComboBox = new JComboBox<>(new EnumComboBoxModel<>(TipoPagamento.class));
        JLabel pagtoLabel = new JLabel("Método de Pagamento:");
        pagtoLabel.setLabelFor(pagtoComboBox);
        
        JLabel valorLabel = new JLabel("Valor Total:");
        valorTotalText = new JTextField();
        valorTotalText.setEditable(false);
        valorLabel.setLabelFor(valorTotalText);

        mainPanel = new JPanel(new MigLayout("insets 20",
                "[grow][]30[][][]",
                "[]30[fill][]"));

        mainPanel.add(addButton, "skip");
        mainPanel.add(pagtoLabel);
        mainPanel.add(pagtoComboBox);
        mainPanel.add(addDadoBancario, "wrap");
        mainPanel.add(editTablePanel.getTableScrollPane(), "span, growx, wrap");
        mainPanel.add(valorLabel, "skip 3, right");
        mainPanel.add(valorTotalText, "grow");
    }

    public void formatColumns(){
        int width = editTablePanel.getTableScrollPane().getWidth()/100;
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(width*10);
        columnModel.getColumn(1).setPreferredWidth(width*20);
        columnModel.getColumn(2).setPreferredWidth(width*20);
        columnModel.getColumn(3).setPreferredWidth(width*20);
        columnModel.getColumn(4).setPreferredWidth(width*15);
        columnModel.getColumn(5).setPreferredWidth(width*40);
    }

    public EditTablePanel getEditTablePanel() { return editTablePanel; }
    public JPanel getMainPanel() { return mainPanel; }
    public JTable getTable() { return table; }
    public JComboBox<TipoPagamento> getPagtoComboBox() { return pagtoComboBox; }
    public JButton getAddDadoBancario() { return addDadoBancario; }
    public JButton getAddButton() { return addButton; }
    public void setCutAction(ActionListener cutAction) { editTablePanel.setCutAction(cutAction); }
    public void setPasteAction(ActionListener pasteAction) { editTablePanel.setPasteAction(pasteAction); }
    public void setDelAction(ActionListener delAction) { editTablePanel.setDelAction(delAction); }
    public void setBackspaceAction(ActionListener backspaceAction) { editTablePanel.setBackspaceAction(backspaceAction); }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case DupModel.DADO_BUTTON -> addDadoBancario.setEnabled((Boolean) evt.getNewValue());
            case DupModel.TIPO_PAGTO -> pagtoComboBox.setSelectedItem(evt.getNewValue());
            case DupModel.TOTAL -> valorTotalText.setText((String) evt.getNewValue());
        }
    }

}

