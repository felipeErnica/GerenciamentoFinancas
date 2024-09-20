package com.santacarolina.areas.frmDoc;

import com.formdev.flatlaf.FlatClientProperties;
import com.santacarolina.ui.ManageViewImpl;
import com.santacarolina.util.ImageIconConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;

public class ClassificacaoForm {

    private static Logger logger = LogManager.getLogger();

    private JDialog dialog;
    private JTextField searchField;
    private JTable table;

    public ClassificacaoForm() {
        initComponents();
    }

    private void initComponents() {

        ImageIconConfig iconConfig = new ImageIconConfig("src/main/resources/icon/add_contact_icon.png");

        ManageViewImpl form = new ManageViewImpl();
        dialog = form.getDialog();
//        searchField = form.getSearchField();
//        table = form.getTable();

        dialog.setTitle("Tabela de Classificação");
        dialog.setIconImage(iconConfig.getBufferedImage());
        searchField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Pesquisar Classificação...");
        for (int i = 0; i < table.getModel().getColumnCount(); i++) formatColumns(i);
        dialog.setModal(true);
        dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
    }

    private void formatColumns(int columnIndex) {
        TableColumn column = table.getColumnModel().getColumn(columnIndex);
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        switch (columnIndex) {
            case 0 -> {
                column.setPreferredWidth(120);
                renderer.setHorizontalAlignment(SwingConstants.CENTER);
                column.setCellRenderer(renderer);
            }
            case 1 -> column.setPreferredWidth(300);
        }
    }

    public JDialog getDialog() { return dialog; }
    public JTextField getSearchField() { return searchField; }
    public JTable getTable() { return table; }
    public JTextField getTextField() { return searchField; }

}