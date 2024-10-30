package com.santacarolina.areas.bancario.conciliacao.frmManageConciliados;

import com.santacarolina.enums.TipoMovimento;
import com.santacarolina.interfaces.AfterUpdateListener;
import com.santacarolina.model.Contato;
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
        init();
    }

    private void init() {
        view.getTipoMovimento().addActionListener(e -> tipoMovimento_afterUpdate());
        view.getDataFim().addFocusListener((AfterUpdateListener) e -> dataFim_afterUpdate());
        view.getDataInicio().addFocusListener((AfterUpdateListener) e -> dataInicio_afterUpdate());
        view.getEmissor().addActionListener(e -> emissor_afterUpdate());
        view.getPastaContabil().addActionListener(e -> pastaContabil_afterUpdate());
    }

    private void tipoMovimento_afterUpdate() {
        TipoMovimento tipoMovimento = (TipoMovimento) view.getTipoMovimento().getSelectedItem();
        model.setTipoMovimento(tipoMovimento);
    }

    private void dataInicio_afterUpdate() { model.setDataInicio(view.getDataInicio().getText()); }
    private void dataFim_afterUpdate() { model.setDataFim(view.getDataFim().getText()); }

    private void emissor_afterUpdate() {
        Contato emissor = (Contato) view.getEmissor().getSelectedItem();
        model.setEmissor(emissor);
    }

    private void pastaContabil_afterUpdate() {
        PastaContabil pastaContabil = (PastaContabil) view.getPastaContabil().getSelectedItem();
        model.setPastaContabil(pastaContabil);
    }

}
