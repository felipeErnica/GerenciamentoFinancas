package com.santacarolina.areas.frmDoc;

import com.santacarolina.dao.ClassificacaoDao;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.AfterUpdateListener;
import com.santacarolina.interfaces.DoubleClickListener;
import com.santacarolina.model.beans.ClassificacaoContabil;
import com.santacarolina.model.beans.Produto;
import com.santacarolina.model.tablemodels.ClassificacaoModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.stream.Collectors;

public class ClassificacaoController {

    private final Logger logger = LogManager.getLogger();

    private Produto produto;
    private List<ClassificacaoContabil> classificacaoList;
    private ClassificacaoForm form;
    private ClassificacaoModel model;

    public ClassificacaoController(Produto produto, ClassificacaoForm form) {
        this.produto = produto;
        this.form = form;
        this.model = (ClassificacaoModel) form.getTable().getModel();
        insertValues();
        form.getTable().addMouseListener((DoubleClickListener) this::table_doubleClick);
        form.getTextField().addFocusListener((AfterUpdateListener) this::textField_afterUpdate);
        form.getDialog().setVisible(true);
    }

    private void insertValues() {
        try {
            classificacaoList = new ClassificacaoDao().getAll().stream()
                    .filter(c -> c.getFluxoCaixa() == produto.getDocumento().getFluxoCaixa())
                    .toList();
            model.setList(classificacaoList);
            model.fireTableDataChanged();
        } catch (FetchFailException e) {
            logger.error(e.getMessage());
            JOptionPane.showMessageDialog(null,
                    e.getMessage(),
                    e.getMessageTitle(),
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void table_doubleClick(MouseEvent e) {
        int row = form.getTable().rowAtPoint(e.getPoint());
        ClassificacaoContabil classificacao = model.getObject(row);
        produto.setClassificacao(classificacao);
        form.getDialog().dispose();
    }

    private void textField_afterUpdate(FocusEvent e) {
        List<ClassificacaoContabil> filterList;
        filterList = classificacaoList.stream()
                .filter(c -> c.getNomeClassificacao().toUpperCase().contains(form.getTextField().getText().toUpperCase()))
                .collect(Collectors.toList());
        model.setList(filterList);
    }

}
