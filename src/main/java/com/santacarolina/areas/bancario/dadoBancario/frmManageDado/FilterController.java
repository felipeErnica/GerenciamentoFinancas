package com.santacarolina.areas.bancario.dadoBancario.frmManageDado;

import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.DocumentChangeListener;

/**
 * FilterController
 */
public class FilterController {

    private FilterView view;
    private FilterModel model;

    public FilterController(FilterView view, FilterModel model) throws FetchFailException {
        this.view = view;
        this.model = model;
        init();
    }

    private void init() throws FetchFailException {
        view.getNomeField().getDocument().addDocumentListener((DocumentChangeListener) e -> nomeField_onChange());
        view.getAgenciaField().getDocument().addDocumentListener((DocumentChangeListener) e -> agenciaField_onChange());
        view.getNumeroContaField().getDocument().addDocumentListener((DocumentChangeListener) e -> contaField_onChange());
        view.getBancoField().getDocument().addDocumentListener((DocumentChangeListener) e -> bancoField_afterUpdate());
    }

    private void bancoField_afterUpdate() { model.setBanco(view.getBancoField().getText()); }
    private void contaField_onChange() { model.setNumeroConta(view.getNumeroContaField().getText()); }
    private void agenciaField_onChange() { model.setAgencia(view.getAgenciaField().getText()); }
    private void nomeField_onChange() { model.setNome(view.getNomeField().getText()); }

}
