package com.santacarolina.areas.bancario.conciliacao.frmConciliacao;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.santacarolina.util.AppIcon;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ConciliacaoView implements PropertyChangeListener {

    private JDialog dialog;
    private Container mainPane;
    private JTable extratoTable;
    private JButton conciliarButton;
    private JButton outrasButton;
    private JRadioButton oneForOneSelection;
    private JRadioButton oneExtratoForManyDup;
    private JRadioButton oneDupForManyExtrato;
    private JTable duplicatasTable;

    public ConciliacaoView() {
        initComponents();
    }

    private void initComponents () {
        this.dialog = new JDialog();
        dialog.setTitle("Conciliação de Extratos");
        dialog.setIconImage(AppIcon.paintIcon(new FlatSVGIcon("icon/conciliacao_icon.svg")).getImage());

        mainPane = dialog.getContentPane();

        extratoTable = new JTable();
        extratoTable.setRowSelectionAllowed(true);
        JScrollPane extratoPane = new JScrollPane(extratoTable);

        conciliarButton = new JButton("Conciliar");
        outrasButton = new JButton("Outras Movimentações");

        oneForOneSelection = new JRadioButton("Um para um");
        oneForOneSelection.setSelected(true);
        oneExtratoForManyDup = new JRadioButton("Um Extrato para Várias Duplicatas");
        oneDupForManyExtrato = new JRadioButton("Uma Duplicata para Vários Extratos");

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(oneForOneSelection);
        buttonGroup.add(oneDupForManyExtrato);
        buttonGroup.add(oneExtratoForManyDup);

        JPanel radioButtonsPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        radioButtonsPanel.setBorder(BorderFactory.createTitledBorder("Tipo de Conciliação:"));

        radioButtonsPanel.add(oneForOneSelection);
        radioButtonsPanel.add(oneExtratoForManyDup);
        radioButtonsPanel.add(oneDupForManyExtrato);

        this.duplicatasTable = new JTable();
        JScrollPane dupPane = new JScrollPane(duplicatasTable);

        mainPane.setLayout(new MigLayout("insets 15",
                "[grow, fill]15[grow, fill]",
                "[]15[][]15[]"));

        mainPane.add(extratoPane, "span, wrap");
        mainPane.add(conciliarButton);
        mainPane.add(radioButtonsPanel, "wrap, spany 2");
        mainPane.add(outrasButton, "wrap");
        mainPane.add(dupPane, "span");
    }

    public JDialog getDialog() { return dialog; }
    public Container getMainPane() { return mainPane; }
    public JTable getExtratoTable() { return extratoTable; }
    public JButton getConciliarButton() { return conciliarButton; }
    public JButton getOutrasButton() { return outrasButton; }
    public JRadioButton getOneForOneSelection() { return oneForOneSelection; }
    public JRadioButton getOneExtratoForManyDup() { return oneExtratoForManyDup; }
    public JRadioButton getOneDupForManyExtrato() { return oneDupForManyExtrato; }
    public JTable getDuplicatasTable() { return duplicatasTable; }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case ConciliacaoModel.TABLES -> changeSelection(String.valueOf(evt.getNewValue()));
            case ConciliacaoModel.REQUERY -> {
                extratoTable.repaint();
                duplicatasTable.repaint();
            }
        }
    }

    public void formatColumns() {
        int extratoWidth = extratoTable.getWidth()/100;
        TableColumnModel extratoModel = extratoTable.getColumnModel();
        extratoModel.getColumn(0).setPreferredWidth(extratoWidth*10);
        extratoModel.getColumn(1).setPreferredWidth(extratoWidth*15);
        extratoModel.getColumn(2).setPreferredWidth(extratoWidth*20);
        extratoModel.getColumn(3).setPreferredWidth(extratoWidth*45);
        extratoModel.getColumn(4).setPreferredWidth(extratoWidth*10);

        int dupWidth = duplicatasTable.getWidth()/100;
        TableColumnModel dupModel = duplicatasTable.getColumnModel();
        dupModel.getColumn(0).setPreferredWidth(dupWidth*8);
        dupModel.getColumn(1).setPreferredWidth(dupWidth*10);
        dupModel.getColumn(2).setPreferredWidth(dupWidth*10);
        dupModel.getColumn(3).setPreferredWidth(dupWidth*20);
        dupModel.getColumn(4).setPreferredWidth(dupWidth*40);
        dupModel.getColumn(5).setPreferredWidth(dupWidth*10);

    }

    private void changeSelection(String selectionMode) {
        switch (selectionMode) {
            case ConciliacaoModel.ONE_TO_ONE -> {
                extratoTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                duplicatasTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            }
            case ConciliacaoModel.ONE_EXTRATO_MANY_DUP -> {
                extratoTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                duplicatasTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            }
            case ConciliacaoModel.ONE_DUP_MANY_EXTRATO -> {
                extratoTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
                duplicatasTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            }
        }
    }

}
