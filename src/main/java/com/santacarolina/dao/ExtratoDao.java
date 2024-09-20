package com.santacarolina.dao;

import com.santacarolina.dto.ExtratoDTO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.exceptions.SaveFailException;
import com.santacarolina.model.beans.Extrato;
import com.santacarolina.util.ApiRequest;
import com.santacarolina.util.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class ExtratoDao {


    private final Logger logger = LogManager.getLogger();

    private final Service<Extrato, ExtratoDTO> service;
    private final String MAPPING = "/extratos";

    public ExtratoDao() {
        this.service = new Service<Extrato, ExtratoDTO>(Extrato.class, ExtratoDTO.class);
    }

    public List<ExtratoDTO> findByConta(long contaId) throws FetchFailException {
        String query = MAPPING + "/contaId=" + contaId;
        return service.getListRequestDTO(query);
    }

    public List<ExtratoDTO> findByConciliacao(boolean isPayed) throws FetchFailException {
        String query = MAPPING + "/isConciliado=" + isPayed;
        return service.getListRequestDTO(query);
    }

    public void save(Extrato e) throws SaveFailException {
        service.postRequest(MAPPING, e);
    }

    public void saveAll(List<Extrato> list) throws SaveFailException {
        String query = MAPPING + "/batch";
        service.postListRequest(query, list);
    }
}
