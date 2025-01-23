package com.santacarolina.areas.relatorio;

import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.santacarolina.interfaces.ViewUpdater;
import com.santacarolina.model.PastaContabil;
import com.santacarolina.model.ProdutoDuplicata;
import com.santacarolina.util.PropertyFirer;

public class FormModel implements ViewUpdater {

    private LocalDate dataFim;
    private LocalDate dataInicio;
    private Map<Long, PastaContabil> mapaPasta;

    private List<ProdutoDuplicata> listaSemFiltro;
    private PropertyFirer pf;

    public FormModel(List<ProdutoDuplicata> listaSemFiltro) {
        this.listaSemFiltro = listaSemFiltro;
        pf = new PropertyFirer(this);
    }

    public LocalDate getDataFim() { return dataFim; }
    public LocalDate getDataInicio() { return dataInicio; }
    public Map<Long, PastaContabil> getMapaPasta() { return mapaPasta; }
    public List<ProdutoDuplicata> getListaSemFiltro() { return listaSemFiltro; }

    public void setDataFim(LocalDate dataFim) { this.dataFim = dataFim; }
    public void setDataInicio(LocalDate dataInicio) { this.dataInicio = dataInicio; }
    public void setMapaPasta(Map<Long, PastaContabil> listaPasta) { this.mapaPasta = listaPasta; }

    public List<ProdutoDuplicata> getListaFiltrada() {
        List<ProdutoDuplicata> listaDataFiltrada = new ArrayList<>();
        
        listaDataFiltrada = listaSemFiltro.stream()
            .filter(prodDup -> prodDup.getDuplicata().getDocumento() != null)
            .filter(prodDup -> prodDup.getDuplicata().getDocumento().getPasta() != null)
            .filter(prodDup -> prodDup.getDuplicata().getDataVencimento().isAfter(dataInicio.minusDays(1)))
            .filter(prodDup -> prodDup.getDuplicata().getDataVencimento().isBefore(dataFim.plusDays(1)))
            .collect(Collectors.toList());

        Map<Long,List<ProdutoDuplicata>> mapaListaPorPasta = listaDataFiltrada.stream()
            .collect(Collectors.groupingBy(prodDup -> prodDup.getDuplicata().getDocumento().getPasta().getId()));

        List<ProdutoDuplicata> listaFiltradaFinal = new ArrayList<>();

        for (Long pastaId : mapaPasta.keySet()) {
            List<ProdutoDuplicata> listaPorPasta = mapaListaPorPasta.getOrDefault(pastaId, Collections.emptyList());
            listaFiltradaFinal.addAll(listaPorPasta);
        }

        return listaFiltradaFinal;
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) { pf.addPropertyChangeListener(listener); }

    @Override
    public void fireInitialChanges() {
        // TODO Auto-generated method stub
    }

}
