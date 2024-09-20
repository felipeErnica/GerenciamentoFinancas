package com.santacarolina.ui;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import net.coderazzi.filters.gui.AutoChoices;
import net.coderazzi.filters.gui.TableFilterHeader;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class ManageViewImpl {

    private JDialog dialog;
    private JTable table;
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

        table = new JTable();
        table.setShowHorizontalLines(true);
        JScrollPane scrollPane = new JScrollPane(table);

        TableFilterHeader filterHeader = new TableFilterHeader(table, AutoChoices.ENABLED);
        filterHeader.setPosition(TableFilterHeader.Position.TOP);

        dialog.setLayout(new MigLayout("insets 25",
                "[grow][][]",
                "[]30[grow, fill]"
        ));

        dialog.add(addButton,"skip");
        dialog.add(deleteButton, "wrap");
        dialog.add(scrollPane, "span, grow");
    }

    public JDialog getDialog() { return dialog; }
    public JTable getTable() { return table; }
    public ActionSVGButton getDeleteButton() { return deleteButton; }
    public ActionSVGButton getAddButton() { return addButton; }

}
