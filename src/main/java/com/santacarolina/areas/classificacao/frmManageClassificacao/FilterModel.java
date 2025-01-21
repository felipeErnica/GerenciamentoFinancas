package com.santacarolina.areas.classificacao.frmManageClassificacao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.santacarolina.enums.FluxoCaixa;
import com.santacarolina.model.ClassificacaoContabil;

/**
 * FilterModel
 */
public class FilterModel {

    private String nome;
    private String categoria;
    private FluxoCaixa fluxoCaixa;

    private ClassificacaoTableModel tableModel;
    private List<ClassificacaoContabil> filteredList;

    public FilterModel(ClassificacaoTableModel tableModel) {
        this.tableModel = tableModel;
        filteredList = new ArrayList<>(tableModel.getList());
    }

    public void setNome(String classificacao) {
        this.nome = classificacao;
        setFilters();
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
        setFilters();
    }

    public void setFluxoCaixa(FluxoCaixa fluxoCaixa) {
        this.fluxoCaixa = fluxoCaixa;
        setFilters();
    }

    public void setFilters() {
        filteredList = new ArrayList<>(tableModel.getList());
        if (!StringUtils.isBlank(nome)) fiterClassificacao();
        if (!StringUtils.isBlank(categoria)) fiterCategoria();
        if (fluxoCaixa != null) filterFluxo();
        tableModel.getBaseModel().setList(filteredList);
    }

    private void fiterClassificacao() {
        filteredList = filteredList.stream()
            .filter(classificacao -> !StringUtils.isBlank(classificacao.getNomeClassificacao()))
            .filter(classificacao -> classificacao.getNomeClassificacao().contains(nome.toUpperCase()))
            .collect(Collectors.toList());
    }

    private void fiterCategoria() {
        filteredList = filteredList.stream()
            .filter(classificacao -> classificacao.getCategoria() != null)
            .filter(classificacao -> !StringUtils.isBlank(classificacao.getCategoria().getNome()))
            .filter(classificacao -> classificacao.getCategoria().getNome().contains(categoria.toUpperCase()))
            .collect(Collectors.toList());
    }

    private void filterFluxo() {
        filteredList = filteredList.stream()
            .filter(classificacao -> classificacao.getCategoria() != null)
            .filter(classificacao -> classificacao.getCategoria().getFluxoCaixa() == fluxoCaixa)
            .collect(Collectors.toList());
    }

}
