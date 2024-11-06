package com.santacarolina.dao;

import java.util.List;

import com.santacarolina.dto.ConciliacaoDTO;
import com.santacarolina.exceptions.DeleteFailException;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.exceptions.SaveFailException;
import com.santacarolina.model.Conciliacao;
import com.santacarolina.util.Service;

public class ConciliacaoDAO {

    private final Service<Conciliacao, ConciliacaoDTO> service;
    private static final String MAPPING = "/conciliacoes";

    public ConciliacaoDAO() { this.service = new Service<>(ConciliacaoDTO.class); }

    public List<ConciliacaoDTO> findAll() throws FetchFailException { return service.getListRequestDTO(MAPPING); } 

    public List<Conciliacao> findByExtrato(long extratoId) throws FetchFailException {
        String query = MAPPING + "/extrato=" + extratoId;
        return service.getListRequest(query);
    }

    public List<Conciliacao> findByDuplicata(long duplicataId) throws FetchFailException {
        String query = MAPPING + "/duplicata=" + duplicataId;
        return service.getListRequest(query);
    }

    public void save(Conciliacao conciliacao) throws SaveFailException { service.postRequestDTO(MAPPING, conciliacao); }

    public void saveAll(List<Conciliacao> list) throws SaveFailException {
        String query = MAPPING + "/batch";
        service.postListRequest(query, list);
    }

    public void deleteById(long id) throws DeleteFailException {
        String query = MAPPING + "/" + id;
        service.deleteRequest(query);
    }

}
