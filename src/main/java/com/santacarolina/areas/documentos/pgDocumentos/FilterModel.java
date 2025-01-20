package com.santacarolina.areas.documentos.pgDocumentos;

import java.beans.PropertyChangeListener;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.santacarolina.enums.TipoDoc;
import com.santacarolina.interfaces.ViewUpdater;
import com.santacarolina.model.DocumentoFiscal;
import com.santacarolina.model.PastaContabil;
import com.santacarolina.util.PropertyFirer;
import com.santacarolina.util.StringConversor;

/**
 * FilterModel
 */
public class FilterModel implements ViewUpdater {

    public static final String DATA_FIM = "dataFim";
    public static final String DATA_INICIO = "dataInicio";

    private String emissor;
    private String numDoc;
    private PastaContabil pastaContabil;
    private TipoDoc tipoDoc;
    private LocalDate dataFim;
    private LocalDate dataInicio;

    private List<DocumentoFiscal> filteredList;
    private DocTableModel tableModel;
    private PropertyFirer pf;

    public FilterModel(DocTableModel tableModel) {
        this.tableModel = tableModel;
        filteredList = new ArrayList<>(tableModel.getList());
        dataInicio = filteredList.getLast().getDataEmissao();
        dataFim = filteredList.getFirst().getDataEmissao();
        pf = new PropertyFirer(this);
    }

    public void setEmissor(String emissor) {
        this.emissor = emissor;
        setFilters();
    }

    public void setNumDoc(String numDoc) {
        this.numDoc = numDoc;
        setFilters();
    }

    public void setPastaContabil(PastaContabil pastaContabil) {
        this.pastaContabil = pastaContabil;
        setFilters();
    }

    public void setTipoDoc(TipoDoc tipoDoc) {
        this.tipoDoc = tipoDoc;
        setFilters();
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

    public void setDataInicio(String dataInicio) {
        try {
            this.dataInicio = StringConversor.transformDate(dataInicio);
        } catch (DateTimeException e) {
            this.dataInicio = null;
        }
        setFilters();
        pf.firePropertyChange(DATA_INICIO, this.dataInicio)
        ;
    }

    private void setFilters() {
        filteredList = new ArrayList<>(tableModel.getList());
        if (pastaContabil != null) filterPasta();
        if (tipoDoc != null) filterTipo();
        if (!StringUtils.isBlank(emissor)) filterEmissor();
        if (numDoc != null) filterNumDoc();
        if (dataInicio != null) filterInicio();
        if (dataFim != null) filterFim();
        tableModel.getBaseModel().setList(filteredList);
    }

    private void filterPasta() {
        filteredList = filteredList.stream()
            .filter(doc -> doc.getPastaId() == pastaContabil.getId())
            .collect(Collectors.toList());
    }

    private void filterTipo() {
        filteredList = filteredList.stream()
            .filter(doc -> doc.getTipoDoc() == tipoDoc)
            .collect(Collectors.toList());
    }

    private void filterEmissor() {
        filteredList = filteredList.stream()
            .filter(doc -> doc.getEmissor() != null)
            .filter(doc -> !StringUtils.isBlank(doc.getEmissor().getNome()))
            .filter(doc -> doc.getEmissor().getNome().contains(emissor.toUpperCase()))
            .collect(Collectors.toList());
    }

    private void filterNumDoc() {
        filteredList = filteredList.stream()
            .filter(doc -> doc.getNumDoc() != null)
            .filter(doc -> doc.getNumDoc().toString().contains(numDoc))
            .collect(Collectors.toList());
    }

    private void filterInicio() {
        filteredList = filteredList.stream()
            .filter(doc -> doc.getDataEmissao() != null)
            .filter(doc -> doc.getDataEmissao().isAfter(dataInicio.minusDays(1)))
            .collect(Collectors.toList());
    }

    private void filterFim() {
        filteredList = filteredList.stream()
            .filter(doc -> doc.getDataEmissao() != null)
            .filter(doc -> doc.getDataEmissao().isBefore(dataFim.plusDays(1)))
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
