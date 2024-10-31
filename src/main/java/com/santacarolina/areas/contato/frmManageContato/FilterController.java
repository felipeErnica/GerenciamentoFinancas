package com.santacarolina.areas.contato.frmManageContato;

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
        init();
    }

    private void init() {
        view.getIeField().getDocument().addDocumentListener((DocumentChangeListener) e -> ieField_onChange());
        view.getCpfField().getDocument().addDocumentListener((DocumentChangeListener) e -> cpfField_onChange());
        view.getCnpjField().getDocument().addDocumentListener((DocumentChangeListener) e -> cnpjField_onChange());
        view.getNomeSearch().getDocument().addDocumentListener((DocumentChangeListener) e -> nomeField_onChange());
    }

    private void nomeField_onChange() { model.setNome(view.getNomeSearch().getText()); }
    private void cnpjField_onChange() { model.setCnpj(view.getCnpjField().getText()); }
    private void cpfField_onChange() { model.setCpf(view.getCpfField().getText()); }
    private void ieField_onChange() { model.setIe(view.getIeField().getText()); }

}
