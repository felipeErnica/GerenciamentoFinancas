package com.santacarolina.dao;

import com.santacarolina.dto.ExtratoDTO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.exceptions.SaveFailException;
import com.santacarolina.model.Extrato;
import com.santacarolina.util.Service;

import java.util.List;
import java.util.Optional;

public class ExtratoDAO {

    private final Service<Extrato, ExtratoDTO> service = new Service<>(ExtratoDTO.class);
    private final String MAPPING = "/extratos";

    public List<ExtratoDTO> findByConta(long contaId) throws FetchFailException {
        String query = MAPPING + "/contaId=" + contaId;
        return service.getListRequestDTO(query);
    }

    public List<ExtratoDTO> findByConciliacao(boolean isPayed) throws FetchFailException {
        String query = MAPPING + "/isConciliado=" + isPayed;
        return service.getListRequestDTO(query);
    }

    public void save(Extrato e) throws SaveFailException { service.postRequest(MAPPING, e); }

    public void saveAll(List<Extrato> list) throws SaveFailException {
        String query = MAPPING + "/batch";
        service.postListRequest(query, list);
    }

    public Optional<Extrato> findById(long id) throws FetchFailException {
        String query = MAPPING + "/" + id;
        return service.getRequest(query);
    }

}
