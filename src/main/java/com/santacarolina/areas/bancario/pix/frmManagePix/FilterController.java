package com.santacarolina.areas.bancario.pix.frmManagePix;

import com.santacarolina.enums.TipoPix;
import com.santacarolina.interfaces.DocumentChangeListener;

/**
 * FilterController
 */
public class FilterController {

    private FilterView view;
    private FilterModel model;

    public FilterController(FilterView view, FilterModel model) {
        this.view = view;
        this.model = model;
        model.addPropertyChangeListener(view);
        init();
    }

    private void init() {
        view.getAgencia().getDocument().addDocumentListener((DocumentChangeListener) e -> agencia_onChange());
        view.getNome().getDocument().addDocumentListener((DocumentChangeListener) e -> nome_onChange());
        view.getBanco().getDocument().addDocumentListener((DocumentChangeListener) e -> banco_onChange());
        view.getChave().getDocument().addDocumentListener((DocumentChangeListener) e -> chave_onChange());
        view.getTipoChave().addActionListener(e -> tipoChave_onUpdate()); 
    }

    private void agencia_onChange() { model.setAgencia(view.getAgencia().getText()); }
    private void nome_onChange() { model.setNome(view.getNome().getText()); }
    private void banco_onChange() { model.setBanco(view.getBanco().getText()); }
    private void chave_onChange() { model.setChave(view.getChave().getText()); }
    
    private void tipoChave_onUpdate() {
        TipoPix tipoPix = (TipoPix) view.getTipoChave().getSelectedItem();
        model.setTipoPix(tipoPix);
    }

}
