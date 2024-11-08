package com.santacarolina.areas.pgProdutos;

import java.beans.PropertyChangeListener;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.santacarolina.dto.ProdutoDTO;
import com.santacarolina.interfaces.ViewUpdater;
import com.santacarolina.model.PastaContabil;
import com.santacarolina.util.PropertyFirer;
import com.santacarolina.util.StringConversor;

/**
 * FilterModel
 */
public class FilterModel implements ViewUpdater {

    public static final String DATA_INICIO = "dataInicio";
    public static final String DATA_FIM = "dataFim";

    private String emissor;
    private String descricao;
    private String tipoMercadoria;
    private PastaContabil pastaContabil;
    private LocalDate dataInicio;
    private LocalDate dataFim;

    private PropertyFirer pf;
    private ProdTableModel tableModel;
    private List<ProdutoDTO> filteredList;

    public FilterModel(ProdTableModel tableModel) {
        this.tableModel = tableModel;
        pf = new PropertyFirer(this);
        filteredList = new ArrayList<>(tableModel.getList());
    }

    public void setEmissor(String emissor) {
        this.emissor = emissor;
        setFilters();
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
        setFilters();
    }

    public void setTipoMercadoria(String tipoMercadoria) {
        this.tipoMercadoria = tipoMercadoria;
        setFilters();
    }

    public void setPastaContabil(PastaContabil pastaContabil) {
        this.pastaContabil = pastaContabil;
        setFilters();
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
        if (!StringUtils.isBlank(emissor)) filterEmissor();
        if (!StringUtils.isBlank(tipoMercadoria)) filterTipo();
        if (!StringUtils.isBlank(descricao)) filterDescricao();
        if (dataInicio != null) filterInicio();
        if (dataFim != null) filterFim();
        if (pastaContabil != null) filterPasta();
        tableModel.getBaseModel().setList(filteredList);
    }

    private void filterEmissor() {
        filteredList = filteredList.stream()
            .filter(dto -> !StringUtils.isBlank(dto.getNomeContato()))
            .filter(dto -> dto.getNomeContato().contains(emissor.toUpperCase()))
            .collect(Collectors.toList());
    }

    private void filterTipo() {
        filteredList = filteredList.stream()
            .filter(dto -> !StringUtils.isBlank(dto.getNomeClassificacao()))
            .filter(dto -> dto.getNomeClassificacao().contains(tipoMercadoria.toUpperCase()))
            .collect(Collectors.toList());
    }

    private void filterDescricao() {
        filteredList = filteredList.stream()
            .filter(dto -> !StringUtils.isBlank(dto.getDescricao()))
            .filter(dto -> dto.getDescricao().contains(descricao.toUpperCase()))
            .collect(Collectors.toList());
    }

    private void filterInicio() {
        filteredList = filteredList.stream()
            .filter(dto -> dto.getDataEmissao() != null)
            .filter(dto -> dto.getDataEmissao().isAfter(dataInicio.minusDays(1)))
            .collect(Collectors.toList());
    }

    private void filterFim() {
        filteredList = filteredList.stream()
            .filter(dto -> dto.getDataEmissao() != null)
            .filter(dto -> dto.getDataEmissao().isBefore(dataFim.plusDays(1)))
            .collect(Collectors.toList());
    }

    private void filterPasta() {
        filteredList = filteredList.stream()
            .filter(dto -> dto.getPastaId() != 0)
            .filter(dto -> dto.getPastaId() == pastaContabil.getId())
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
