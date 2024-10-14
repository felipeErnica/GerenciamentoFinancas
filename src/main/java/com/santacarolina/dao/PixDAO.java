package com.santacarolina.dao;

import com.santacarolina.dto.PixDTO;
import com.santacarolina.exceptions.DeleteFailException;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.exceptions.SaveFailException;
import com.santacarolina.model.ChavePix;
import com.santacarolina.model.Contato;
import com.santacarolina.util.Service;

import java.util.List;
import java.util.Optional;

public class PixDAO {

    private final Service<ChavePix, PixDTO> service = new Service<>(PixDTO.class);
    private final String MAPPING = "/chavesPix";

    public List<ChavePix> findAll() throws FetchFailException {
        return service.getListRequest(MAPPING);
    }

    public Optional<ChavePix> findById(long id) throws FetchFailException {
        String query = MAPPING + "/" + id;
        return service.getRequest(query);
    }

    public Optional<ChavePix> getByChave(String chave) throws FetchFailException {
        String query = MAPPING + "/chave=" + chave;
        return service.getRequest(query);
    }

    public List<ChavePix> findByContato(Contato c) throws FetchFailException {
        String queryString = MAPPING + "/contato=" + c.getId();
        return service.getListRequest(queryString);
    }

    public void save(ChavePix c) throws SaveFailException { service.postRequest(MAPPING, c); }

    public void deleteById(long id) throws DeleteFailException {
        String query = MAPPING + "/" + id;
        service.deleteRequest(query);
    }

}