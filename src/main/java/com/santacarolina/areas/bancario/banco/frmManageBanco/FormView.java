package com.santacarolina.areas.bancario.banco.frmManageBanco;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableColumnModel;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.icons.FlatSearchIcon;
import com.santacarolina.interfaces.ManageView;
import com.santacarolina.ui.ActionSVGButton;
import com.santacarolina.ui.ManageViewImpl;

import net.miginfocom.swing.MigLayout;

public class FormView implements ManageView {

    private ManageViewImpl baseView;
    private JTextField contatoSearchField;

    public FormView() {
        this.baseView = new ManageViewImpl();
        init();
    }

    private void init() {
        baseView.getDialog().setTitle("Gerenciar Bancos");
        baseView.getAddButton().setText("Adicionar Banco");
        baseView.getDeleteButton().setText("Excluir Banco");

        contatoSearchField = new JTextField();
        contatoSearchField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Pesquisar banco...");
        contatoSearchField.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_ICON, new FlatSearchIcon());

        JPanel fiterPanel = baseView.getFilterPane();
        fiterPanel.setLayout(new MigLayout("insets 20", "[grow, fill]"));
        fiterPanel.add(contatoSearchField);
    }

    @Override
    public JTable getTable() { return baseView.getTable(); }

    @Override
    public JDialog getDialog() { return baseView.getDialog(); }

    @Override
    public ActionSVGButton getAddButton() { return baseView.getAddButton(); }

    @Override
    public ActionSVGButton getDeleteButton() { return baseView.getDeleteButton(); }

    public JTextField getContatoSearchField() { return contatoSearchField; }

    @Override
    public void formatColumns() {
        int width = baseView.getTable().getWidth()/100;
        TableColumnModel columnModel = baseView.getTable().getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(width*80);
        columnModel.getColumn(1).setPreferredWidth(width*20);
    }

}
