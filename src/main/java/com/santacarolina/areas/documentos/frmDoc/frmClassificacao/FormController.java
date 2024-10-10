package com.santacarolina.areas.documentos.frmDoc.frmClassificacao;

import com.santacarolina.interfaces.AfterUpdateListener;
import com.santacarolina.interfaces.Controller;
import com.santacarolina.interfaces.DoubleClickListener;
import com.santacarolina.interfaces.OnResize;
import com.santacarolina.model.ClassificacaoContabil;
import com.santacarolina.model.Produto;
import com.santacarolina.util.ViewInvoker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.stream.Collectors;

public class FormController implements Controller {

    private Produto produto;
    private List<ClassificacaoContabil> classificacaoList;
    private FormView view;
    private FormModel model;

    public FormController(FormView view, FormModel model) {
        this.view = view;
        this.model = model;
        init();
    }

    private void init() {
        view.getTable().setModel(model.getBaseModel());
        view.getDialog().addComponentListener((OnResize) e -> view.formatColumns());

        TableColumnModel columnModel = view.getTable().getColumnModel();

        DefaultTableCellRenderer defaultRenderer = new DefaultTableCellRenderer();
        defaultRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        columnModel.getColumn(0).setCellRenderer(defaultRenderer);

        view.getTable().addMouseListener((DoubleClickListener) this::table_doubleClick);
        view.getTextField().addFocusListener((AfterUpdateListener) e -> textField_afterUpdate());
    }

    private void table_doubleClick(MouseEvent e) {
        int row = view.getTable().rowAtPoint(e.getPoint());
        ClassificacaoContabil classificacao = model.getObject(row);
        model.setClassificacao(classificacao);
        view.getDialog().dispose();
    }

    private void textField_afterUpdate() { EventQueue.invokeLater(() -> model.setFilter(view.getTextField().getText())); }

    @Override
    public void showView() { ViewInvoker.showView(view.getDialog()); }

}
