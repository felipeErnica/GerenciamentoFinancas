package com.santacarolina.areas.duplicatas.common;

import java.beans.PropertyChangeListener;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.santacarolina.enums.TipoPagamento;
import com.santacarolina.interfaces.ViewUpdater;
import com.santacarolina.model.ContaBancaria;
import com.santacarolina.model.Duplicata;
import com.santacarolina.util.PropertyFirer;
import com.santacarolina.util.StringConversor;

/**
 * FilterModel
 */
public class FilterModel implements ViewUpdater {

    public static final String DATA_FIM = "dataFim";
    public static final String DATA_INICIO = "dataInicio";

    private String emissor; 
    private TipoPagamento tipoPagamento;
    private ContaBancaria contaBancaria;
    private LocalDate dataInicio;
    private LocalDate dataFim;

    private PropertyFirer pf;
    private List<Duplicata> filteredList;
    private DupTableModel tableModel;
    private boolean isUpdating;

    public FilterModel(DupTableModel tableModel) {
        this.tableModel = tableModel;
        pf = new PropertyFirer(this);
        filteredList = new ArrayList<>(tableModel.getList());

        if (filteredList.getFirst().isPaga()) {
            dataInicio = filteredList.getLast().getDataVencimento();
            dataFim = filteredList.getFirst().getDataVencimento();
        } else {
            dataInicio = filteredList.getFirst().getDataVencimento();
            dataFim = filteredList.getLast().getDataVencimento();
        }
    }

    public void setEmissor(String emissor) {
        this.emissor = emissor;
        setFilters();
    }

    public void setTipoPagamento(TipoPagamento tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
        setFilters();
    }

    public void setContaBancaria(ContaBancaria contaBancaria) {
        this.contaBancaria = contaBancaria;
        setFilters();
    }

    public void setDataInicio(String dataInicio) {
        if (isUpdating) return;
        try {
            this.dataInicio = StringConversor.transformDate(dataInicio);
        } catch (DateTimeException e) {
            this.dataInicio = null;
        }
        isUpdating = true;
        setFilters();
        pf.firePropertyChange(DATA_INICIO, this.dataInicio);
        isUpdating = false;
    }

    public void setDataFim(String dataFim) {
        if (isUpdating) return;
        try {
            this.dataFim = StringConversor.transformDate(dataFim);
        } catch (DateTimeException e) {
            this.dataFim = null;
        }
        isUpdating = true;
        setFilters();
        pf.firePropertyChange(DATA_FIM, this.dataFim);
        isUpdating = false;
    }

    public void setFilters() {
        filteredList = new ArrayList<>(tableModel.getList()); 
        if (!StringUtils.isBlank(emissor)) filterEmissor();
        if (tipoPagamento != null) filterTipo();
        if (contaBancaria != null) filterConta();
        if (dataInicio != null) filterInicio();
        if (dataFim != null) filterFim();
        tableModel.getBaseModel().setList(filteredList);
    }

    private void filterEmissor() {
        System.out.println("filtro emissor: " + emissor);
        System.out.println("filterEmissor");
        filteredList.forEach(d -> System.out.println(d.toString()));
        filteredList = filteredList.stream()
            .filter(dup -> dup.getDocumento() != null)
            .filter(dup -> dup.getDocumento().getEmissor() != null)
            .filter(dup -> dup.getDocumento().getEmissor().getNome().contains(emissor.toUpperCase()))
            .collect(Collectors.toList());
    }

    private void filterTipo() {
        System.out.println("filterTipo");
        filteredList.forEach(d -> System.out.println(d.toString()));
        filteredList = filteredList.stream()
            .filter(dup -> dup.getTipoPagamento() != null)
            .filter(dup -> dup.getTipoPagamento() == tipoPagamento)
            .collect(Collectors.toList());
    }

    private void filterConta() {
        System.out.println("filterConta");
        filteredList.forEach(d -> System.out.println(d.toString()));
        filteredList = filteredList.stream()
            .filter(dup -> dup.getDocumento() != null)
            .filter(dup -> dup.getDocumento().getPasta() != null)
            .filter(dup -> dup.getDocumento().getPasta().getConta() != null)
            .filter(dup -> dup.getDocumento().getPasta().getConta().getId() == contaBancaria.getId())
            .collect(Collectors.toList());
    }

    private void filterInicio() {
        System.out.println("filterInicio");
        filteredList.forEach(d -> System.out.println(d.toString()));
        //filteredList = filteredList.stream()
            //.filter(dup -> dup.getDataVencimento() != null)
            //.filter(dup -> dup.getDataVencimento().isAfter(dataInicio.minusDays(1)))
            //.collect(Collectors.toList());
    }

    private void filterFim() {
        System.out.println("filterFim");
        filteredList.forEach(d -> System.out.println(d.toString()));
        //filteredList = filteredList.stream()
            //.filter(dup -> dup.getDataVencimento() != null)
            //.filter(dup -> dup.getDataVencimento().isBefore(dataFim.plusDays(1)))
            //.collect(Collectors.toList());
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) { pf.addPropertyChangeListener(listener); }

    @Override
    public void fireInitialChanges() {
        pf.firePropertyChange(DATA_FIM, dataFim);
        pf.firePropertyChange(DATA_INICIO, dataInicio);
    }

}
