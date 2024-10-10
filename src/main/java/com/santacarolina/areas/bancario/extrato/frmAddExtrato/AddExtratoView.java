package com.santacarolina.areas.bancario.extrato.frmAddExtrato;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.santacarolina.ui.EditTablePanel;
import com.santacarolina.util.AppIcon;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.TableColumnModel;

public class AddExtratoView {

    private JDialog dialog;
    private JButton addButton;
    private EditTablePanel tablePanel;
    private JTable table;
    private JButton addExtratos;

    public AddExtratoView() { initComponents(); }

    private void initComponents() {
        dialog = new JDialog();
        dialog.setTitle("Adicionar Extratos");
        dialog.setIconImage(AppIcon.paintIcon(new FlatSVGIcon("icon/extrato_icon.svg")).getImage());

        addButton = new JButton("Adicionar Novo", AppIcon.paintIcon(new FlatSVGIcon("icon/add_icon.svg")));

        tablePanel = new EditTablePanel();
        table = tablePanel.getTable();
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(true);

        addExtratos = new JButton("Salvar Extratos", AppIcon.paintIcon(new FlatSVGIcon("icon/save_icon.svg")));

        dialog.setLayout(new MigLayout("insets 20",
                "[grow, fill][][]",
                "[fill][grow, fill]"
        ));

        dialog.add(addButton, "skip");
        dialog.add(addExtratos, "span, wrap");
        dialog.add(tablePanel.getTableScrollPane(), "span");
    }

    public JDialog getDialog() { return dialog; }
    public JButton getAddButton() { return addButton; }
    public EditTablePanel getTablePanel() { return tablePanel; }
    public JTable getTable() { return table; }
    public JButton getAddExtratos() { return addExtratos; }

    public void formatColumns() {
        int width = tablePanel.getTableScrollPane().getWidth()/100;
        TableColumnModel model = getTable().getColumnModel();
        model.getColumn(0).setPreferredWidth(width*2);
        model.getColumn(1).setPreferredWidth(width*10);
        model.getColumn(2).setPreferredWidth(width*20);
        model.getColumn(3).setPreferredWidth(width*20);
        model.getColumn(4).setPreferredWidth(width*40);
        model.getColumn(5).setPreferredWidth(width*10);
    }

}
