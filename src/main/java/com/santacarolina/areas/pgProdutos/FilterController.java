package com.santacarolina.areas.pgProdutos;

import com.santacarolina.interfaces.AfterUpdateListener;
import com.santacarolina.interfaces.DocumentChangeListener;
import com.santacarolina.model.PastaContabil;

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
        view.getDataFim().addFocusListener((AfterUpdateListener) e -> dataFim_onLostFocus());
        view.getDataInicio().addFocusListener((AfterUpdateListener) e -> dataInicio_onLostFocus());
        view.getPastaField().addActionListener(e -> pastaField_onAction());
        view.getEmissorField().getDocument().addDocumentListener((DocumentChangeListener) e -> emissorField_onChange());
        view.getDescricaoField().getDocument().addDocumentListener((DocumentChangeListener) e -> descricao_onChange());
        view.getTipoMercadoriaField().getDocument().addDocumentListener((DocumentChangeListener) e -> tipoMercadoria_onChange());
    }

    private void dataFim_onLostFocus() { model.setDataFim(view.getDataFim().getText()); }
    private void dataInicio_onLostFocus() { model.setDataInicio(view.getDataInicio().getText()); }
    private void emissorField_onChange() { model.setEmissor(view.getEmissorField().getText()); }
    private void descricao_onChange() { model.setDescricao(view.getDescricaoField().getText()); }
    private void tipoMercadoria_onChange() { model.setTipoMercadoria(view.getTipoMercadoriaField().getText()); }

    private void pastaField_onAction() {
        PastaContabil pastaContabil = (PastaContabil) view.getPastaField().getSelectedItem();
        model.setPastaContabil(pastaContabil);
    }

}
