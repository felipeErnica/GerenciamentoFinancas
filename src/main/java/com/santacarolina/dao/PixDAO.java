package com.santacarolina.dao;

import java.util.List;
import java.util.Optional;

import com.santacarolina.dto.PixDTO;
import com.santacarolina.exceptions.DeleteFailException;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.exceptions.SaveFailException;
import com.santacarolina.model.ChavePix;
import com.santacarolina.model.Contato;
import com.santacarolina.util.Service;

public class PixDAO {

    private final Service<ChavePix, PixDTO> service = new Service<>(PixDTO.class);
    private final String MAPPING = "/chavesPix";

    public List<PixDTO> findAll() throws FetchFailException {
        return service.getListRequestDTO(MAPPING);
    }

    public Optional<ChavePix> findById(long id) throws FetchFailException {
        String query = MAPPING + "/" + id;
        return service.getRequest(query);
    }

    public Optional<ChavePix> findByChave(String chave) throws FetchFailException {
        String query = MAPPING + "/chave=" + chave;
        return service.getRequest(query);
    }

    public List<ChavePix> findByContato(Contato c) throws FetchFailException {
        String queryString = MAPPING + "/contato=" + c.getId();
        return service.getListRequest(queryString);
    }

    public void save(ChavePix c) throws SaveFailException { service.postRequestDTO(MAPPING, c); }

    public void deleteById(long id) throws DeleteFailException {
        String query = MAPPING + "/" + id;
        service.deleteRequest(query);
    }

    public Optional<ChavePix> findByDadoId(long dadoId) throws FetchFailException {
        String query = MAPPING + "/dadoId=" + dadoId;
        return service.getRequest(query);
    }

    public void deleteAll(List<PixDTO> list) throws DeleteFailException {
        String query = MAPPING + "/delete-batch";
        service.deleteList(query, list);
    }

}
