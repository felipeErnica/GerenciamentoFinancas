package com.santacarolina.areas.bancario.extrato.pgExtrato;

import java.beans.PropertyChangeListener;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.santacarolina.dto.ExtratoDTO;
import com.santacarolina.interfaces.ViewUpdater;
import com.santacarolina.util.PropertyFirer;
import com.santacarolina.util.StringConversor;

/**
 * FilterModel
 */
public class FilterModel implements ViewUpdater {

    public static final String DATA_FIM = "dataFim";
    public static final String DATA_INICIO = "dataInicio";

    private LocalDate dataInicio;
    private LocalDate dataFim;

    private PropertyFirer pf;
    private List<ExtratoDTO> filteredList;
    private ExtratoTableModel tableModel;

    public FilterModel(ExtratoTableModel tableModel) {
        this.tableModel = tableModel;
        filteredList = new ArrayList<>(tableModel.getList());
        pf = new PropertyFirer(this);
        setData();
    }

    public void setData() {
        filteredList = new ArrayList<>(tableModel.getList());
        if (!filteredList.isEmpty()) {
            dataInicio = filteredList.getLast().getDataTransacao();
            dataFim = filteredList.getFirst().getDataTransacao();
        } else {
            dataFim = null;
            dataInicio = null;
        }
        fireInitialChanges();
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

    public void setFilters() {
        filteredList = new ArrayList<>(tableModel.getList());
        if (dataInicio != null) filterInicio();
        if (dataFim != null) filterFim();
        tableModel.getBaseModel().setList(filteredList);
    }

    private void filterInicio() {
        filteredList = filteredList.stream()
            .filter(dto -> dto.getDataTransacao() != null)
            .filter(dto -> dto.getDataTransacao().isAfter(dataInicio.minusDays(1)))
            .collect(Collectors.toList());
    }

    private void filterFim() {
        filteredList = filteredList.stream()
            .filter(dto -> dto.getDataTransacao() != null)
            .filter(dto -> dto.getDataTransacao().isBefore(dataFim.plusDays(1)))
            .collect(Collectors.toList());
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) { pf.addPropertyChangeListener(listener); }

    @Override
    public void fireInitialChanges() {
        pf.firePropertyChange(DATA_FIM, dataFim);
        pf.firePropertyChange(DATA_INICIO, dataInicio);
    }

}
