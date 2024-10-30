package com.santacarolina.ui;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import net.miginfocom.swing.MigLayout;

public class ManageViewImpl {

    private JDialog dialog;
    private JTable table;
    private JPanel filterPane;
    private ActionSVGButton deleteButton;
    private ActionSVGButton addButton;

    public ManageViewImpl() {
        initComponents();
    }

    private void initComponents() {
        dialog = new JDialog();
        dialog.setLocationRelativeTo(null);

        deleteButton = new ActionSVGButton(new FlatSVGIcon("icon/delete_icon.svg"));
        addButton = new ActionSVGButton(new FlatSVGIcon("icon/add_icon.svg"));
        filterPane = new JPanel();

        table = new JTable();
        table.setShowHorizontalLines(true);
        JScrollPane scrollPane = new JScrollPane(table);

        dialog.setLayout(new MigLayout("insets 25",
                "[grow, fill][fill]",
                "[][]30[grow, fill]"
        ));

        dialog.add(filterPane, "spany 2");
        dialog.add(addButton, "wrap");
        dialog.add(deleteButton, "skip, wrap");
        dialog.add(scrollPane, "span, grow");
    }

    public JDialog getDialog() { return dialog; }
    public JPanel getFilterPane() { return filterPane; }
    public JTable getTable() { return table; }
    public ActionSVGButton getDeleteButton() { return deleteButton; }
    public ActionSVGButton getAddButton() { return addButton; }

}
