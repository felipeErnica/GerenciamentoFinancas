package com.santacarolina.areas.classificacao.frmClassificacao;

import java.beans.PropertyChangeListener;

import com.santacarolina.enums.FluxoCaixa;
import com.santacarolina.interfaces.ViewUpdater;
import com.santacarolina.model.CategoriaContabil;
import com.santacarolina.model.ClassificacaoContabil;
import com.santacarolina.util.PropertyFirer;

/**
 * FormModel
 */
public class FormModel implements ViewUpdater {

    public static final String NOME = "nome";
    public static final String NUMERO = "numero";
    public static final String CATEGORIA = "categoriaContabil";

    public ClassificacaoContabil classificacao;

    private String nome;
    private long numero;
    private CategoriaContabil categoriaContabil;
    private FluxoCaixa fluxoCaixa;

    private PropertyFirer pf;

    public FormModel(ClassificacaoContabil classificacao) {
        this.classificacao = classificacao;
        pf = new PropertyFirer(this);
        updateAllData();
    }

    public void setNome(String nome) { 
        this.nome = nome.toUpperCase();
        pf.firePropertyChange(NOME, this.nome);
    }

    public void setNumero(long numero) { this.numero = numero; }

    public void setCategoriaContabil(CategoriaContabil categoriaContabil) {
        this.categoriaContabil = categoriaContabil; 
        fluxoCaixa = categoriaContabil != null ? categoriaContabil.getFluxoCaixa() : null;
    }

    private void updateAllData() {
        fluxoCaixa = categoriaContabil != null ? categoriaContabil.getFluxoCaixa() : null;
        categoriaContabil = classificacao.getCategoriaContabil();
        nome = classificacao.getNomeClassificacao();
        numero = classificacao.getNumeroIdentificacao();
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) { pf.addPropertyChangeListener(listener); }

    @Override
    public void fireInitialChanges() {
        pf.firePropertyChange(NOME, nome);
        pf.firePropertyChange(NUMERO, numero);
        pf.firePropertyChange(CATEGORIA, categoriaContabil);
    }

    public ClassificacaoContabil getClassificacao() { 
        classificacao.setCategoriaContabil(categoriaContabil);
        classificacao.setNumeroIdentificacao(numero);
        classificacao.setNomeClassificacao(nome);
        classificacao.setFluxoCaixa(fluxoCaixa);
        return classificacao; 
    }

    public CategoriaContabil getCategoriaContabil() { return categoriaContabil; }
    public String getNome() { return nome; }
    public long getNumero() { return numero; }

}
