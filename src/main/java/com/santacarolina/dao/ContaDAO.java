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
import com.santacarolina.model.ContaBancaria;
import com.santacarolina.util.ApiRequest;

public class ContaDAO {

    private final Logger logger = LogManager.getLogger();
    private final ApiRequest<ContaBancaria> apiRequest = new ApiRequest<>(ContaBancaria.class);
    private final String MAPPING = "/contasAdm";

    public List<ContaBancaria> findAll() throws FetchFailException { 
        try {
            return apiRequest.getListRequest(MAPPING);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new FetchFailException(e, logger);
        } 
    }

    public Optional<ContaBancaria> findEqual(ContaBancaria contaBancaria) throws FetchFailException {
        String query = MAPPING + "/?agencia=" + contaBancaria.getAgencia() +
                "&numeroConta=" + contaBancaria.getNumeroConta() +
                "&bancoId=" + contaBancaria.getBanco().getId();
        try {
            return apiRequest.getRequest(query);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new FetchFailException(e, logger);
        }
    }

    public void save(ContaBancaria contaBancaria) throws SaveFailException {
        try {
            apiRequest.postRequest(MAPPING, contaBancaria);
        } catch (IOException | URISyntaxException | InterruptedException e) {
            throw new SaveFailException(e, logger);
        } 
    }

    public Optional<ContaBancaria> findById(long id) throws FetchFailException {
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

    public void deleteAll(List<ContaBancaria> list) throws DeleteFailException {
        String query = MAPPING + "/delete-batch";
        try {
            apiRequest.postListRequest(query, list);
        } catch (IOException | URISyntaxException | InterruptedException e) {
            throw new DeleteFailException(e, logger);
        }
    }

}
