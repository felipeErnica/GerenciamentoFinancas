package com.santacarolina.areas.bancario.conciliacao.frmManageConciliados;

import java.beans.PropertyChangeListener;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.santacarolina.dto.ConciliacaoDTO;
import com.santacarolina.enums.TipoMovimento;
import com.santacarolina.interfaces.ViewUpdater;
import com.santacarolina.model.Contato;
import com.santacarolina.model.PastaContabil;
import com.santacarolina.util.PropertyFirer;
import com.santacarolina.util.StringConversor;

/**
 * FilterModel
 */
public class FilterModel implements ViewUpdater {

    public static final String DATA_FIM = "dataFim";
    public static final String DATA_INICIO = "dataInicio";

    private PropertyFirer pf;

    private LocalDate dataInicio;
    private LocalDate dataFim;
    private PastaContabil pastaContabil;
    private Contato emissor;
    private TipoMovimento tipoMovimento;
    private List<ConciliacaoDTO> filteredList;
    private ConciliadosTableModel tableModel;

    public FilterModel(ConciliadosTableModel model) {
        this.tableModel = model;
        filteredList = new ArrayList<>(model.getList());
        pf = new PropertyFirer(this);
        updateData();
    }

    private void updateData() {
        dataInicio = filteredList.stream()
            .map(dto -> dto.getDataExtrato())
            .sorted()
            .findFirst().orElse(null);
        dataFim = filteredList.stream()
            .map(dto -> dto.getDataExtrato())
            .sorted(Comparator.reverseOrder())
            .findFirst().orElse(null);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) { pf.addPropertyChangeListener(listener); }

    @Override
    public void fireInitialChanges() {
        pf.firePropertyChange(DATA_FIM, dataFim);
        pf.firePropertyChange(DATA_INICIO, dataInicio);
    }

    public void setDataInicio(String dataInicio) { 
        try {
            this.dataInicio = StringConversor.transformDate(dataInicio); 
        } catch (DateTimeException e) {
            this.dataInicio = null;
        }
        setFilters();
        pf.firePropertyChange(DATA_INICIO, this.dataInicio);
    }

    public void setDataFim(String dataFim) {
        try {
            this.dataFim = StringConversor.transformDate(dataFim); 
        } catch (DateTimeException e) {
            this.dataFim = null;
        }
        setFilters();
        pf.firePropertyChange(DATA_FIM, this.dataFim);
    }

    public void setPastaContabil(PastaContabil pastaContabil) {
        this.pastaContabil = pastaContabil;
        setFilters();
    }

    public void setEmissor(Contato emissor) {
        this.emissor = emissor;
        setFilters();
    }

    public void setTipoMovimento(TipoMovimento tipoMovimento) {
        this.tipoMovimento = tipoMovimento;
        setFilters();
    }

    public List<ConciliacaoDTO> getList() { return filteredList; }

    private void setFilters() {
        filteredList = new ArrayList<>(tableModel.getList());
        if (dataInicio != null) filterInicio();
        if (dataFim != null) filterFim();
        if (pastaContabil != null) filterPasta();
        if (tipoMovimento != null) filterTipo();
        if (emissor != null) filterEmissor();
        tableModel.getBaseModel().setList(filteredList);
    }

    private void filterFim() {
        filteredList = filteredList.stream()
            .filter(dto -> dto.getDataExtrato().isBefore(dataFim.plusDays(1)))
            .collect(Collectors.toList());
    }

    private void filterInicio() {
        filteredList = filteredList.stream()
            .filter(dto -> dto.getDataExtrato().isAfter(dataInicio.minusDays(1)))
            .collect(Collectors.toList());
    }

    private void filterPasta() {
        filteredList = filteredList.stream()
            .filter(dto -> dto.getPastaId() == pastaContabil.getId())
            .collect(Collectors.toList());
    }

    private void filterTipo() {
        filteredList = filteredList.stream()
            .filter(dto -> dto.getTipoMovimento() == tipoMovimento)
            .collect(Collectors.toList());
    }

    private void filterEmissor() {
        filteredList = filteredList.stream()
            .filter(dto -> dto.getEmissorId() == emissor.getId())
            .collect(Collectors.toList());
    }

}
