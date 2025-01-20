package com.santacarolina.areas.bancario.pix.frmManagePix;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.santacarolina.enums.TipoPix;
import com.santacarolina.interfaces.AbstractFilterModel;
import com.santacarolina.interfaces.ViewUpdater;
import com.santacarolina.model.ChavePix;
import com.santacarolina.util.DocConversor;
import com.santacarolina.util.PropertyFirer;

/**
 * FilterModel
 */
public class FilterModel implements AbstractFilterModel, ViewUpdater {

    public final static String CHAVE = "chave";

    private String conta;
    private String chave;
    private String nome;
    private String agencia;
    private String banco;
    private TipoPix tipoPix;
    
    private PixTableModel tableModel;
    private List<ChavePix> filteredList;
    private boolean isUpdating;

    private PropertyFirer pf;

    public FilterModel(PixTableModel tableModel) {
        this.tableModel = tableModel;
        pf = new PropertyFirer(this);
        filteredList = new ArrayList<>(tableModel.getList());
    }

    public void setConta(String conta) {
        this.conta = conta;
        setFilters();
    }

    public void setChave(String chave) {
        if (isUpdating) return;
        this.chave = chave;
        setFilters();
        if (tipoPix != null) {
            String chaveFormat = chave;
            if (!isValidFormat(chaveFormat)) return;
            switch (tipoPix) {
                case CPF -> chaveFormat = DocConversor.docFormat(chaveFormat, DocConversor.CPF_FORMAT);
                case CNPJ -> chaveFormat = DocConversor.docFormat(chaveFormat, DocConversor.CNPJ_FORMAT);
                case TELEFONE -> chaveFormat = DocConversor.docFormat(chaveFormat, DocConversor.PHONE_FORMAT);
                default -> chaveFormat = "";
            };
            isUpdating = true;
            pf.firePropertyChange(CHAVE, chaveFormat);
            isUpdating = false;
        }
    }

    private boolean isValidFormat(String chaveFormat) {
        return switch (tipoPix) {
            case CPF -> DocConversor.isValidFormat(chaveFormat, DocConversor.CPF_FORMAT);
            case CNPJ -> DocConversor.isValidFormat(chaveFormat, DocConversor.CNPJ_FORMAT);
            case TELEFONE -> DocConversor.isValidFormat(chaveFormat, DocConversor.PHONE_FORMAT);
            default -> false;
        };
    }

    public void setNome(String nome) {
        this.nome = nome;
        setFilters();
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
        setFilters();
    }

    public void setBanco(String banco) {
        this.banco = banco;
        setFilters();
    }

    public void setTipoPix(TipoPix tipoPix) {
        this.tipoPix = tipoPix;
        setFilters();
    }

    public void setFilters() {
        filteredList = new ArrayList<>(tableModel.getList());
        if (!StringUtils.isBlank(nome)) filterNome();
        if (!StringUtils.isBlank(banco)) filterBanco();
        if (!StringUtils.isBlank(chave)) filterChave();
        if (!StringUtils.isBlank(agencia)) filterAgencia();
        if (!StringUtils.isBlank(conta)) filterConta();
        if (tipoPix != null) filterTipoPix();
        tableModel.getBaseModel().setList(filteredList);
    }

    private void filterTipoPix() {
        filteredList = filteredList.stream()
            .filter(pix -> pix.getTipoPix() == tipoPix)
            .collect(Collectors.toList());
    }

    private void filterConta() {
        filteredList = filteredList.stream()
            .filter(pix -> pix.getDado() != null)
            .filter(pix -> pix.getDado().getNumeroConta().contains(conta))
            .collect(Collectors.toList());
    }

    private void filterAgencia() {
        filteredList = filteredList.stream()
            .filter(pix -> pix.getDado() != null)
            .filter(pix -> pix.getDado().getAgencia().contains(agencia))
            .collect(Collectors.toList());
    }

    private void filterChave() {
        filteredList = filteredList.stream()
            .filter(pix -> pix.getChave().contains(chave))
            .collect(Collectors.toList());
    }

    private void filterBanco() {
        filteredList = filteredList.stream()
            .filter(pix -> pix.getDado() != null)
            .filter(pix -> pix.getDado().getBanco() != null)
            .filter(pix -> pix.getDado().getBanco().getNomeBanco().contains(banco))
            .collect(Collectors.toList());
    }

    private void filterNome() {
        filteredList = filteredList.stream()
            .filter(pix -> pix.getContato() != null)
            .filter(pix -> pix.getContato().getNome().contains(nome.toUpperCase()))
            .collect(Collectors.toList());
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) { pf.addPropertyChangeListener(listener); }

    @Override
    public void fireInitialChanges() { }
    
}
