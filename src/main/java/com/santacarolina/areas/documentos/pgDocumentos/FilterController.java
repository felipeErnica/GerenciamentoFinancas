package com.santacarolina.areas.documentos.pgDocumentos;

import com.santacarolina.enums.TipoDoc;
import com.santacarolina.interfaces.AfterUpdateListener;
import com.santacarolina.interfaces.DocumentChangeListener;
import com.santacarolina.model.PastaContabil;

/**
 * FilterController
 */
public class FilterController {

    private FilterView view;
    private FilterModel model;

    public FilterController(FilterView view, FilterModel filterModel) {
        this.view = view;
        this.model = filterModel;
        model.addPropertyChangeListener(view);
        init();
    }

    private void init() {
        view.getEmissorField().getDocument().addDocumentListener((DocumentChangeListener)e -> emissorField_onChange());
        view.getNumDocField().getDocument().addDocumentListener((DocumentChangeListener)e -> numDocField_onChange());
        view.getPastaField().addActionListener(e -> pastaField_onAction());
        view.getTipoDocField().addActionListener(e -> tipoDoc_onAction());
        view.getDataFinalField().addFocusListener((AfterUpdateListener) e -> dataFinal_afterUpdate());
        view.getDataInicioField().addFocusListener((AfterUpdateListener) e -> dataInicio_afterUpdate());
    }

    private void emissorField_onChange() { model.setEmissor(view.getEmissorField().getText()); }
    private void numDocField_onChange() { model.setNumDoc(view.getNumDocField().getText()); }
    private void dataFinal_afterUpdate() { model.setDataFim(view.getDataFinalField().getText()); }
    private void dataInicio_afterUpdate() { model.setDataInicio(view.getDataInicioField().getText()); }

    private void pastaField_onAction() {
        PastaContabil pastaContabil = (PastaContabil) view.getPastaField().getSelectedItem();
        model.setPastaContabil(pastaContabil);
    }

    private void tipoDoc_onAction() {
        TipoDoc tipoDoc = (TipoDoc) view.getTipoDocField().getSelectedItem();
        model.setTipoDoc(tipoDoc);
    }

}
