package com.santacarolina.areas.documentos.frmDoc.frmClassificacao;

import java.awt.event.MouseEvent;

import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

import org.jdesktop.swingx.combobox.ListComboBoxModel;

import com.santacarolina.dao.CategoriaDAO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.Controller;
import com.santacarolina.interfaces.DocumentChangeListener;
import com.santacarolina.interfaces.DoubleClickListener;
import com.santacarolina.interfaces.OnResize;
import com.santacarolina.model.CategoriaContabil;
import com.santacarolina.model.ClassificacaoContabil;
import com.santacarolina.util.ViewInvoker;

public class FormController implements Controller {

    private FormView view;
    private FormModel model;

    public FormController(FormView view, FormModel model) throws FetchFailException {
        this.view = view;
        this.model = model;
        init();
    }

    @SuppressWarnings("unchecked")
    private void init() throws FetchFailException {
        view.getCategoriaComboBox().setModel(new ListComboBoxModel<>(new CategoriaDAO().findAll()));
        view.getCategoriaComboBox().setSelectedItem(null);
        view.getTable().setModel(model.getBaseModel());
        view.getDialog().addComponentListener((OnResize) e -> view.formatColumns());

        TableColumnModel columnModel = view.getTable().getColumnModel();

        DefaultTableCellRenderer defaultRenderer = new DefaultTableCellRenderer();
        defaultRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        columnModel.getColumn(0).setCellRenderer(defaultRenderer);

        view.getTable().addMouseListener((DoubleClickListener) this::table_doubleClick);
        view.getTextField().getDocument().addDocumentListener((DocumentChangeListener) e -> textField_afterUpdate());
        view.getCategoriaComboBox().addActionListener(e -> categoriaComboBox_afterUpdate());
    }

    private void categoriaComboBox_afterUpdate() {
        CategoriaContabil categoriaContabil = (CategoriaContabil) view.getCategoriaComboBox().getSelectedItem();
        model.setCategoriaContabil(categoriaContabil);
    }

    private void textField_afterUpdate() { model.setFilter(view.getTextField().getText()); }

    private void table_doubleClick(MouseEvent e) {
        int row = view.getTable().rowAtPoint(e.getPoint());
        ClassificacaoContabil classificacao = model.getObject(row);
        model.setClassificacao(classificacao);
        view.getDialog().dispose();
    }

    @Override
    public void showView() { ViewInvoker.showView(view.getDialog()); }

}
