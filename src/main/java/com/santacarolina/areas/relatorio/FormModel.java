package com.santacarolina.areas.relatorio;

import java.beans.PropertyChangeListener;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.santacarolina.dao.PastaDAO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.ViewUpdater;
import com.santacarolina.model.PastaContabil;
import com.santacarolina.model.ProdutoDuplicata;
import com.santacarolina.util.PropertyFirer;
import com.santacarolina.util.StringConversor;

public class FormModel implements ViewUpdater {

    public final static String DATA_FIM = "dataFim";
    public final static String DATA_FIM_INVALIDA = "dataFimInvalida";
    public final static String DATA_INICIO = "dataInicio";
    public final static String DATA_INICIO_INVALIDO = "dataInicioInvalido";

    private LocalDate dataFim;
    private LocalDate dataInicio;
    private Map<Long, PastaContabil> mapaPasta;
    private String caminho;

    private List<ProdutoDuplicata> listaSemFiltro;
    private PropertyFirer pf;

    public FormModel(List<ProdutoDuplicata> listaSemFiltro) {
        LocalDate dataHoje = LocalDate.now();
        dataInicio = LocalDate.of(dataHoje.getYear(), dataHoje.getMonth(), 1);
        dataFim = LocalDate.of(dataHoje.getYear(), dataHoje.getMonth().plus(1), 1).minusDays(1);

        this.listaSemFiltro = listaSemFiltro;
        try {
            mapaPasta = new PastaDAO().findAll().stream()
                .collect(Collectors.toMap(pasta -> pasta.getId(), pasta -> pasta));
        } catch (FetchFailException e) {}
        pf = new PropertyFirer(this);
    }

    public LocalDate getDataFim() { return dataFim; }
    public LocalDate getDataInicio() { return dataInicio; }
    public String getCaminho() { return caminho; }
    public Map<Long, PastaContabil> getMapaPasta() { return mapaPasta; }
    public List<ProdutoDuplicata> getListaSemFiltro() { return listaSemFiltro; }

    public void setDataFim(String dataFim) {
        try {
            this.dataFim = StringConversor.transformDate(dataFim);
            pf.firePropertyChange(DATA_FIM, this.dataFim);
            pf.firePropertyChange(DATA_FIM_INVALIDA, false);
        } catch (DateTimeException e) {
            this.dataFim = null;
            pf.firePropertyChange(DATA_FIM, null);
            pf.firePropertyChange(DATA_FIM_INVALIDA, true);
        }
    }
    
    public void setDataInicio(String dataInicio) {
        try {
            this.dataInicio = StringConversor.transformDate(dataInicio);
            pf.firePropertyChange(DATA_INICIO, this.dataInicio);
            pf.firePropertyChange(DATA_INICIO_INVALIDO, false);
        } catch (DateTimeException e) {
            this.dataInicio = null;
            pf.firePropertyChange(DATA_INICIO, null);
            pf.firePropertyChange(DATA_INICIO_INVALIDO, true);
        }
    }

    public void setMapaPasta(Map<Long, PastaContabil> listaPasta) { this.mapaPasta = listaPasta; }
    public void setCaminho(String caminho) { this.caminho = caminho; }

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
    public void fireInitialChanges() {}

}
