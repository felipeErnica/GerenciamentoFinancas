package com.santacarolina.dao;

import java.util.List;
import java.util.Optional;

import com.santacarolina.dto.CategoriaDTO;
import com.santacarolina.exceptions.DeleteFailException;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.exceptions.SaveFailException;
import com.santacarolina.model.CategoriaContabil;
import com.santacarolina.util.Service;

/**
 * CategoriaDAO
 */
public class CategoriaDAO {

    private final Service<CategoriaContabil,CategoriaDTO> service = new Service<>(CategoriaDTO.class);
    private final String MAPPING = "/categoria";

    public List<CategoriaContabil> findAll() throws FetchFailException {
        return service.getListRequest(MAPPING);
    }

    public Optional<CategoriaContabil> findByNome(String nome) throws FetchFailException {
        String query = MAPPING + "/nome=" + nome.replace(" ", "+");
        return service.getRequest(query);
    }

    public Optional<CategoriaContabil> findByNumero(String numero) throws FetchFailException {
        String query = MAPPING + "/numero=" + numero;
        return service.getRequest(query);
    }

    public Optional<CategoriaContabil> findById(long id) throws FetchFailException {
        String query = MAPPING + "/" + id;
        return service.getRequest(query);
    }

    public void save(CategoriaContabil categoriaContabil) throws SaveFailException {
        service.postRequest(MAPPING, categoriaContabil);
    }

    public void deleteById(long id) throws DeleteFailException {
        String query = MAPPING + "/" + id;
        service.deleteRequest(query);
    }
    
}
