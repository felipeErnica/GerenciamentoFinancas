package com.santacarolina.areas.bancario.dadoBancario.frmManageDado;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.santacarolina.interfaces.AbstractFilterModel;
import com.santacarolina.model.DadoBancario;

/**
 * FilterModel
 */
public class FilterModel implements AbstractFilterModel {

    private String nome;
    private String agencia;
    private String numeroConta;
    private String banco;

    private TableModel tableModel;
    private List<DadoBancario> filteredList;

    public FilterModel(TableModel tableModel) {
        this.tableModel = tableModel;
        filteredList = new ArrayList<>(tableModel.getList());
    }

    public void setNome(String nome) {
        this.nome = nome;
        setFilters();
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
        setFilters();
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
        setFilters();
    }

    public void setBanco(String banco) {
        this.banco = banco;
        setFilters();
    }

    public void setFilters() {
        filteredList = new ArrayList<>(tableModel.getList());
        if (!StringUtils.isBlank(nome)) filterNome();
        if (!StringUtils.isBlank(agencia)) filterAgencia();
        if (!StringUtils.isBlank(numeroConta)) filterConta();
        if (!StringUtils.isBlank(banco)) filterBanco();
        tableModel.getBaseModel().setList(filteredList);
    }

    private void filterBanco() {
        filteredList = filteredList.stream()
            .filter(dado -> dado.getBanco() != null)
            .filter(dado -> !StringUtils.isBlank(dado.getBanco().getNomeBanco()))
            .filter(dado -> dado.getBanco().getNomeBanco().contains(banco.toUpperCase()))
            .collect(Collectors.toList());
    }

    private void filterConta() {
        filteredList = filteredList.stream()
            .filter(dado -> !StringUtils.isBlank(dado.getNumeroConta()))
            .filter(dado -> dado.getNumeroConta().contains(numeroConta))
            .collect(Collectors.toList());
    }

    private void filterAgencia() {
        filteredList = filteredList.stream()
            .filter(dado -> !StringUtils.isBlank(dado.getAgencia()))
            .filter(dado -> dado.getAgencia().contains(agencia))
            .collect(Collectors.toList());
    }

    private void filterNome() {
        filteredList = filteredList.stream()
            .filter(dado -> dado.getContato() != null)
            .filter(dado -> !StringUtils.isBlank(dado.getContato().getNome()))
            .filter(dado -> dado.getContato().getNome().contains(nome.toUpperCase()))
            .collect(Collectors.toList());
    }

}
