package com.santacarolina.areas.categoria.frmManageCategoria;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.santacarolina.enums.FluxoCaixa;
import com.santacarolina.model.CategoriaContabil;

/**
 * FilterModel
 */
public class FilterModel {

    private String nomeCategoria;
    private FluxoCaixa fluxoCaixa;
    
    private CategoriaTableModel tableModel;
    private List<CategoriaContabil> filteredList;

    public FilterModel(CategoriaTableModel tableModel) {
        this.tableModel = tableModel;
        filteredList = new ArrayList<>(tableModel.getList());
    }

    public void setNomeCategoria(String categoria) {
        this.nomeCategoria = categoria;
        setFilters();
    }

    public void setFluxoCaixa(FluxoCaixa fluxoCaixa) {
        this.fluxoCaixa = fluxoCaixa;
        setFilters();
    }

    public void setFilters() {
        filteredList = new ArrayList<>(tableModel.getList());
        if (!StringUtils.isBlank(nomeCategoria)) filterCategoria();
        if (fluxoCaixa != null) filterFluxo();
        tableModel.getBaseModel().setList(filteredList);
    }

    private void filterFluxo() {
        filteredList = filteredList.stream()
            .filter(categoria -> categoria.getFluxoCaixa() == fluxoCaixa)
            .collect(Collectors.toList());
    }

    private void filterCategoria() {
        filteredList = filteredList.stream()
            .filter(categoria -> !StringUtils.isBlank(categoria.getNome()))
            .filter(categoria -> categoria.getNome().contains(nomeCategoria.toUpperCase()))
            .collect(Collectors.toList());
    }
    
}
