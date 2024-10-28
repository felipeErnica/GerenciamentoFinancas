package com.santacarolina.areas.bancario.dadoBancario.frmManageDado;

import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.santacarolina.interfaces.ManageView;
import com.santacarolina.ui.ActionSVGButton;
import com.santacarolina.ui.ManageViewImpl;
import com.santacarolina.util.AppIcon;

public class FormView implements ManageView {

    private ManageViewImpl baseView;
    private JDialog dialog;
    private ActionSVGButton deleteButton;
    private ActionSVGButton addButton;
    private JTable table;

    public FormView() {
        baseView= new ManageViewImpl();
        this.dialog = baseView.getDialog();
        this.deleteButton = baseView.getDeleteButton();
        this.addButton = baseView.getAddButton();
        this.table = baseView.getTable();
        initComponents();
    }

    private void initComponents() {
        dialog.setTitle("Gerenciar Contas Bancárias");
        dialog.setIconImage(AppIcon.paintIcon(new FlatSVGIcon("icon/dado_bancario_icon.svg")).getImage());
        deleteButton.setText("Excluir Conta");
        addButton.setText("Adicionar Conta");
    }

    public void formatColumns () {
        int width = table.getWidth()/100;
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(width*35);
        columnModel.getColumn(1).setPreferredWidth(width*25);
        columnModel.getColumn(2).setPreferredWidth(width*5);
        columnModel.getColumn(3).setPreferredWidth(width*10);
        columnModel.getColumn(4).setPreferredWidth(width*15);
        columnModel.getColumn(5).setPreferredWidth(width*10);
    }

    public JDialog getDialog() { return dialog; }
    public ActionSVGButton getDeleteButton() { return deleteButton; }
    public JTable getTable() { return table; }
    public ActionSVGButton getAddButton() { return addButton; }

}
