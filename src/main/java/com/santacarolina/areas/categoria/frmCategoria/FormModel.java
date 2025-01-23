package com.santacarolina.areas.categoria.frmCategoria;

import java.beans.PropertyChangeListener;

import com.santacarolina.enums.FluxoCaixa;
import com.santacarolina.interfaces.ViewUpdater;
import com.santacarolina.model.CategoriaContabil;
import com.santacarolina.util.PropertyFirer;

public class FormModel implements ViewUpdater {

    public static final String FLUXO = "fluxo";
    public static final String NUMERO = "numero";
    public static final String NUMERO_INVALIDO = "numeroInvalido";
    public static final String NOME = "nome";

    private CategoriaContabil categoriaContabil;
    private FluxoCaixa fluxoCaixa;
    private Long numeroEtiqueta;
    private String nomeCategoria;
    
    private boolean isUpdating;
    private PropertyFirer pf;

    public FormModel(CategoriaContabil categoriaContabil) {
        this.categoriaContabil = categoriaContabil;
        this.pf = new PropertyFirer(this);
        updateAllData();
    }

    private void updateAllData() {
        fluxoCaixa = categoriaContabil.getFluxoCaixa();
        numeroEtiqueta = categoriaContabil.getNumeroCategoria();
        nomeCategoria = categoriaContabil.getNome();
    }

    public void setFluxoCaixa(FluxoCaixa fluxoCaixa) { this.fluxoCaixa = fluxoCaixa; }

    public void setNumeroEtiqueta(String numeroString) {
        try {
            Long numeroEtiqueta = Long.parseLong(numeroString);
            this.numeroEtiqueta = numeroEtiqueta; 
            pf.firePropertyChange(NUMERO_INVALIDO, false);   
        } catch (NumberFormatException e) {
            this.numeroEtiqueta = null;
            pf.firePropertyChange(NUMERO_INVALIDO, true);
        }
    }

    public void setNomeCategoria(String nomeCategoria) {
        if (isUpdating) return;
        isUpdating = true;
        this.nomeCategoria = nomeCategoria.toUpperCase();
        pf.firePropertyChange(NOME, this.nomeCategoria);
        isUpdating = false;
    }

    public CategoriaContabil getCategoriaContabil() { 
        if (categoriaContabil == null) categoriaContabil = new CategoriaContabil();
        categoriaContabil.setFluxoCaixa(fluxoCaixa);
        categoriaContabil.setNome(nomeCategoria);
        categoriaContabil.setNumeroCategoria(numeroEtiqueta);
        return categoriaContabil; 
    }
    public FluxoCaixa getFluxoCaixa() { return fluxoCaixa; }
    public Long getNumeroEtiqueta() { return numeroEtiqueta; }
    public String getNomeCategoria() { return nomeCategoria; }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) { pf.addPropertyChangeListener(listener); }

    @Override
    public void fireInitialChanges() {
        isUpdating = true;
        pf.firePropertyChange(FLUXO, fluxoCaixa);
        pf.firePropertyChange(NUMERO, numeroEtiqueta);
        pf.firePropertyChange(NOME, nomeCategoria);
        isUpdating = false;
    }

}
