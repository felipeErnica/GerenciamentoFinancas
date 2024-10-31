package com.santacarolina.areas.contato.frmManageContato;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.santacarolina.interfaces.ViewUpdater;
import com.santacarolina.model.Contato;
import com.santacarolina.util.PropertyFirer;

/**
 * FilterModel
 */
public class FilterModel implements ViewUpdater {

    public static final String CPF = "cpf";
    public static final String CNPJ = "cnpj";
    public static final String IE = "ie";

    private String nome;
    private String cpf;
    private String cnpj;
    private String ie;

    private ContatoTableModel tableModel;
    private List<Contato> filteredList;
    private PropertyFirer pf;

    public FilterModel(ContatoTableModel tableModel) {
        this.tableModel = tableModel;
        filteredList = new ArrayList<>(tableModel.getList());
        pf = new PropertyFirer(this);
    }

    public void setNome(String nome) {
        this.nome = nome; 
        setFilters();
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
        setFilters();
        pf.firePropertyChange(CPF, cpf);
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj; 
        setFilters();
        pf.firePropertyChange(CNPJ, cnpj);
    }

    public void setIe(String ie) {
        this.ie = ie; 
        setFilters();
        pf.firePropertyChange(IE, ie);
    }

    private void setFilters() {
        filteredList = new ArrayList<>(tableModel.getList());
        if (!StringUtils.isBlank(nome)) filterNome();
        if (!StringUtils.isBlank(cpf)) filterCpf();
        if (!StringUtils.isBlank(cnpj)) filterCnpj();
        if (!StringUtils.isBlank(ie)) filterIe();
        tableModel.getBaseModel().setList(filteredList);
    }

    private void filterIe() {
        filteredList = filteredList.stream()
            .filter(contato -> !StringUtils.isBlank(contato.getIe()))
            .filter(contato -> contato.getIe().contains(ie))
            .collect(Collectors.toList());
    }

    private void filterCnpj() {
        filteredList = filteredList.stream()
            .filter(contato -> !StringUtils.isBlank(contato.getCnpj()))
            .filter(contato -> contato.getCnpj().contains(cnpj))
            .collect(Collectors.toList());
    }

    private void filterCpf() {
        filteredList = filteredList.stream()
            .filter(contato -> !StringUtils.isBlank(contato.getCpf()))
            .filter(contato -> contato.getCpf().contains(cpf))
            .collect(Collectors.toList());
    }

    private void filterNome() {
        filteredList = filteredList.stream()
            .filter(contato -> contato.getNome().contains(nome.toUpperCase()))
            .collect(Collectors.toList());
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) { pf.addPropertyChangeListener(listener); }

    @Override
    public void fireInitialChanges() {
    }

}
