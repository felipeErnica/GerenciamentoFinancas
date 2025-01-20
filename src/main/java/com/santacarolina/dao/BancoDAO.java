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
import com.santacarolina.model.Banco;
import com.santacarolina.util.ApiRequest;

public class BancoDAO {

    private static final String MAPPING = "/bancos";
    private Logger logger = LogManager.getLogger();
    private ApiRequest<Banco> apiRequest = new ApiRequest<>(Banco.class);

    public List<Banco> findAll() throws FetchFailException { 
        try {
            return apiRequest.getListRequest(MAPPING);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new FetchFailException(e, logger);
        } 
    }
    
    public void save(Banco banco) throws SaveFailException { 
        try {
            apiRequest.postRequest(MAPPING, banco);
        } catch (IOException | URISyntaxException | InterruptedException e) {
            throw new SaveFailException(e, logger);
        } 
    }

    public Optional<Banco> findById(long id) throws FetchFailException {
        String query = MAPPING + "/" + id;
        try {
            return apiRequest.getRequest(query);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new FetchFailException(e, logger);
        }
    }

    public Optional<Banco> findByNome(String nomeBanco) throws FetchFailException {
        String query = MAPPING + "/nomeBanco=" + nomeBanco.replace(" ","+");
        try {
            return apiRequest.getRequest(query);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new FetchFailException(e, logger);
        }
    }

    public void deleteById(long id) throws DeleteFailException { 
        try {
            apiRequest.deleteRequest(MAPPING + "/" + id);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new DeleteFailException(e, logger);
        } 
    }

    public Optional<Banco> findByApelido(String apelidoBanco) throws FetchFailException {
        String query = MAPPING + "/apelido=" + apelidoBanco;
        try {
            return apiRequest.getRequest(query);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new FetchFailException(e, logger);
        }
    }
    public void deleteAll(List<Banco> list) throws DeleteFailException {
        String query = MAPPING + "/delete-batch";
        try {
            apiRequest.postListRequest(query, list);
        } catch (IOException | URISyntaxException | InterruptedException e) {
            throw new DeleteFailException(e, logger);
        }
    }

}
