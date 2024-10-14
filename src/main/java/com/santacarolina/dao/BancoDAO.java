package com.santacarolina.dao;

import com.santacarolina.dto.BancoDTO;
import com.santacarolina.exceptions.DeleteFailException;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.exceptions.SaveFailException;
import com.santacarolina.model.Banco;
import com.santacarolina.util.Service;

import java.util.List;
import java.util.Optional;

public class BancoDAO {

    private final Service<Banco, BancoDTO> service = new Service<>(BancoDTO.class);
    private static final String MAPPING = "/bancos";

    public List<Banco> findAll() throws FetchFailException { return service.getListRequest(MAPPING); }
    public void save(Banco banco) throws SaveFailException { service.postRequest(MAPPING, banco); }

    public Optional<Banco> findById(long id) throws FetchFailException {
        String query = MAPPING + "/" + id;
        return service.getRequest(query);
    }

    public Optional<Banco> findByNome(String nomeBanco) throws FetchFailException {
        String query = MAPPING + "/nomeBanco=" + nomeBanco.replace(" ","+");
        return service.getRequest(query);
    }

    public void deleteById(long id) throws DeleteFailException { service.deleteRequest(MAPPING); }

}
