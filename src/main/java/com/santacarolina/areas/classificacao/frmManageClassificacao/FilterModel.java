package com.santacarolina.areas.classificacao.frmManageClassificacao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.santacarolina.dto.ClassificacaoDTO;
import com.santacarolina.enums.FluxoCaixa;

/**
 * FilterModel
 */
public class FilterModel {

    private String classificacao;
    private String categoria;
    private FluxoCaixa fluxoCaixa;

    private ClassificacaoTableModel tableModel;
    private List<ClassificacaoDTO> filteredList;

    public FilterModel(ClassificacaoTableModel tableModel) {
        this.tableModel = tableModel;
        filteredList = new ArrayList<>(tableModel.getList());
    }

    public void setClassificacao(String classificacao) {
        this.classificacao = classificacao;
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
        if (!StringUtils.isBlank(classificacao)) fiterClassificacao();
        if (!StringUtils.isBlank(categoria)) fiterCategoria();
        if (fluxoCaixa != null) filterFluxo();
        tableModel.getBaseModel().setList(filteredList);
    }

    private void fiterClassificacao() {
        filteredList = filteredList.stream()
            .filter(dto -> dto.getNomeClassificacao().contains(classificacao.toUpperCase()))
            .collect(Collectors.toList());
    }

    private void fiterCategoria() {
        filteredList = filteredList.stream()
            .filter(dto -> dto.getNomeCategoria().contains(categoria.toUpperCase()))
            .collect(Collectors.toList());
    }

    private void filterFluxo() {
        filteredList = filteredList.stream()
            .filter(dto -> dto.getFluxoCaixa() == fluxoCaixa)
            .collect(Collectors.toList());
    }

}
