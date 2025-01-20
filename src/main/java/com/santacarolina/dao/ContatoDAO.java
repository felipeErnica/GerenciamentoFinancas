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
import com.santacarolina.util.ApiRequest;

public class ContatoDAO {

    private final Logger logger = LogManager.getLogger();
    private final ApiRequest<Contato> apiRequest = new ApiRequest<>(Contato.class);
    private static final String MAPPING = "/contatos";

    public List<Contato> findAll() throws FetchFailException {
        try {
            return apiRequest.getListRequest(MAPPING);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new FetchFailException(e, logger);
        } 
    }

    public Optional<Contato> findByCpf(String cpf) throws FetchFailException {
        String query = MAPPING + "/cpf=" + cpf;
        try {
            return apiRequest.getRequest(query);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new FetchFailException(e, logger);
        }
    }

    public Optional<Contato> findByCnpj(String cnpj) throws FetchFailException {
        String query = MAPPING + "/cnpj=" + cnpj;
        try {
            return apiRequest.getRequest(query);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new FetchFailException(e, logger);
        }
    }

    public Optional<Contato> findByIe(String ie) throws FetchFailException {
        String query = MAPPING + "/ie=" + ie;
        try {
            return apiRequest.getRequest(query);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new FetchFailException(e, logger);
        }
    }

    public Optional<Contato> findByNome(String nome) throws FetchFailException {
        String queryString = MAPPING + "/nome=" + nome.replace(" ","+");
        try {
            return apiRequest.getRequest(queryString);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new FetchFailException(e, logger);
        }
    }

    public Contato save(Contato c) throws SaveFailException {
        try {
            return apiRequest.postRequestWithResponse(MAPPING, c);
        } catch (InterruptedException | IOException | URISyntaxException e) {
            throw new SaveFailException(e, logger);
        } 
    }

    public Optional<Contato> findById(long id) throws FetchFailException {
        String query = MAPPING + "/" + id;
        try {
            return apiRequest.getRequest(query);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new FetchFailException(e, logger);
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

    public void deleteAll(List<Contato> list) throws DeleteFailException {
        String query = MAPPING + "/delete-batch";
        try {
            apiRequest.postListRequest(query, list);
        } catch (IOException | URISyntaxException | InterruptedException e) {
            throw new DeleteFailException(e, logger);
        }
    }

}
