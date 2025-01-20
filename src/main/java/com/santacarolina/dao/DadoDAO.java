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
import com.santacarolina.model.Contato;
import com.santacarolina.model.DadoBancario;
import com.santacarolina.util.ApiRequest;

public class DadoDAO {

    private final Logger logger = LogManager.getLogger();
    private ApiRequest<DadoBancario> apiRequest = new ApiRequest<>(DadoBancario.class);
    private static final String MAPPING = "/contas";

    public List<DadoBancario> findAll() throws FetchFailException {
        try {
            return apiRequest.getListRequest(MAPPING);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new FetchFailException(e, logger);
        } 
    }

    public Optional<DadoBancario> findById(long id) throws FetchFailException {
        String query = MAPPING + "/" + id;
        try {
            return apiRequest.getRequest(query);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new FetchFailException(e, logger);
        }
    }

    public Optional<DadoBancario> getEqual(DadoBancario d) throws FetchFailException {
        String query = MAPPING + "/info?agencia=" + d.getAgencia() +
                "&numeroConta=" + d.getNumeroConta() +
                "&bancoId=" + d.getBancoId();
        try {
            return apiRequest.getRequest(query);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new FetchFailException(e, logger);
        }
    }

    public List<DadoBancario> findByContato(Contato c) throws FetchFailException {
        if (c == null) return null;
        String queryString = MAPPING + "/contato=" + c.getId();
        try {
            return apiRequest.getListRequest(queryString);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new FetchFailException(e, logger);
        }
    }

    public void save(DadoBancario d) throws SaveFailException {
        try {
            apiRequest.postRequest(MAPPING, d);
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

    public void deleteAll(List<DadoBancario> list) throws DeleteFailException {
        String query = MAPPING + "/delete-batch";
        try {
            apiRequest.postListRequest(query, list);
        } catch (IOException | URISyntaxException | InterruptedException e) {
            throw new DeleteFailException(e, logger);
        }
    }

}
