package com.santacarolina.dao;

import com.santacarolina.dto.ConciliacaoDTO;
import com.santacarolina.exceptions.SaveFailException;
import com.santacarolina.model.Conciliacao;
import com.santacarolina.util.Service;

import java.util.List;

public class ConciliacaoDAO {

    private final Service<Conciliacao, ConciliacaoDTO> service;
    private static final String MAPPING = "/conciliacoes";

    public ConciliacaoDAO() { this.service = new Service<>(ConciliacaoDTO.class); }

    public void save(Conciliacao conciliacao) throws SaveFailException {
        service.postRequest(MAPPING, conciliacao);
    }

    public void saveAll(List<Conciliacao> list) throws SaveFailException {
        String query = MAPPING + "/batch";
        service.postListRequest(query, list);
    }

}
