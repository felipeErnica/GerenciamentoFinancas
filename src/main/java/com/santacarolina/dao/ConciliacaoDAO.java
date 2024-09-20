package com.santacarolina.dao;

import com.santacarolina.dto.ConciliacaoDTO;
import com.santacarolina.exceptions.SaveFailException;
import com.santacarolina.model.beans.Conciliacao;
import com.santacarolina.util.ApiRequest;
import com.santacarolina.util.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class ConciliacaoDAO {

    private final Logger logger = LogManager.getLogger();
    private final Service<Conciliacao, ConciliacaoDTO> service;
    private static final String MAPPING = "/conciliacoes";

    public ConciliacaoDAO() { this.service = new Service<>(Conciliacao.class, ConciliacaoDTO.class); }

    public void save(Conciliacao conciliacao) throws SaveFailException {
        service.postRequest(MAPPING, conciliacao);
    }

    public void saveAll(List<Conciliacao> list) throws SaveFailException {
        String query = MAPPING + "/batch";
        service.postListRequest(query, list);
    }

}
