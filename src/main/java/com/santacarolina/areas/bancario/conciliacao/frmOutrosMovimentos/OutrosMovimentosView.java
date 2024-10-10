package com.santacarolina.areas.bancario.conciliacao.frmOutrosMovimentos;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.santacarolina.areas.bancario.conciliacao.frmConciliacao.ConciliacaoModel;
import com.santacarolina.enums.TipoMovimento;
import com.santacarolina.util.AppIcon;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class OutrosMovimentosView implements PropertyChangeListener {

    private JDialog dialog;
    private Container mainPane;
    private JTable extratoTable;
    private JButton conciliarButton;
    private JComboBox<TipoMovimento> movimentosComboBox;

    public OutrosMovimentosView() {
        initComponents();
    }

    private void initComponents () {
        this.dialog = new JDialog();
        dialog.setTitle("Conciliação de Extratos");
        dialog.setIconImage(AppIcon.paintIcon(new FlatSVGIcon("icon/conciliacao_icon.svg")).getImage());

        mainPane = dialog.getContentPane();

        extratoTable = new JTable();
        extratoTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane extratoPane = new JScrollPane(extratoTable);

        conciliarButton = new JButton("Conciliar");

        JLabel movimentosLabel = new JLabel("Tipo de Movimentação:");
        movimentosComboBox = new JComboBox<>();
        AutoCompleteDecorator.decorate(movimentosComboBox);

        mainPane.setLayout(new MigLayout("insets 20",
                "[][grow, fill]15[grow, fill]",
                "[]15[grow, fill]"));

        mainPane.add(movimentosLabel);
        mainPane.add(movimentosComboBox);
        mainPane.add(conciliarButton, "wrap");
        mainPane.add(extratoPane, "span, grow");
    }

    public JDialog getDialog() { return dialog; }
    public Container getMainPane() { return mainPane; }
    public JTable getExtratoTable() { return extratoTable; }
    public JButton getConciliarButton() { return conciliarButton; }
    public JComboBox<TipoMovimento> getMovimentosComboBox() { return movimentosComboBox; }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(ConciliacaoModel.REQUERY)) extratoTable.repaint();
    }

    public void formatColumns() {
        int extratoWidth = extratoTable.getWidth()/100;
        TableColumnModel extratoModel = extratoTable.getColumnModel();
        extratoModel.getColumn(0).setPreferredWidth(extratoWidth*10);
        extratoModel.getColumn(1).setPreferredWidth(extratoWidth*15);
        extratoModel.getColumn(2).setPreferredWidth(extratoWidth*20);
        extratoModel.getColumn(3).setPreferredWidth(extratoWidth*45);
        extratoModel.getColumn(4).setPreferredWidth(extratoWidth*10);
    }

}
