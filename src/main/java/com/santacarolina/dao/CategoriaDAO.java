package com.santacarolina.dao;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.santacarolina.exceptions.DeleteFailException;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.exceptions.SaveFailException;
import com.santacarolina.model.CategoriaContabil;
import com.santacarolina.util.ApiRequest;

/**
 * CategoriaDAO
 */
public class CategoriaDAO {

    private final ApiRequest<CategoriaContabil> apiRequest = new ApiRequest<>(CategoriaContabil.class);
    private final String MAPPING = "/categoria";
    private final Logger logger = LogManager.getLogger();

    public List<CategoriaContabil> findAll() throws FetchFailException {
        try {
            return apiRequest.getListRequest(MAPPING);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new FetchFailException(e, logger);
        }
    }

    public Optional<CategoriaContabil> findByNome(String nome) throws FetchFailException {
        String query = MAPPING + "/nome=" + nome.replace(" ", "+");
        try {
            return apiRequest.getRequest(query);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new FetchFailException(e, logger);
        }
    }

    public Optional<CategoriaContabil> findByNumero(Long numero) throws FetchFailException {
        String query = MAPPING + "/numero=" + numero;
        try {
            return apiRequest.getRequest(query);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new FetchFailException(e, logger);
        }
    }

    public Optional<CategoriaContabil> findById(long id) throws FetchFailException {
        String query = MAPPING + "/" + id;
        try {
            return apiRequest.getRequest(query);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new FetchFailException(e, logger);
        }
    }

    public void save(CategoriaContabil categoriaContabil) throws SaveFailException {
        try {
            apiRequest.postRequest(MAPPING, categoriaContabil);
        } catch (IOException | URISyntaxException | InterruptedException e) {
            throw new SaveFailException(e, logger);
        }
    }

    public void deleteById(long id) throws DeleteFailException {
        String query = MAPPING + "/" + id;
        try {
            apiRequest.deleteRequest(query);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new DeleteFailException(e, logger);
        }
    }

    public void deleteAll(List<CategoriaContabil> list) throws DeleteFailException {
        String query = MAPPING + "/delete-batch";
        try {
            apiRequest.postListRequest(query, list);
        } catch (IOException | URISyntaxException | InterruptedException e) {
            throw new DeleteFailException(e, logger);
        }
    }
    
}
