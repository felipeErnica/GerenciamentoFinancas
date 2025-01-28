package com.santacarolina.dao;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.santacarolina.exceptions.DeleteFailException;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.exceptions.SaveFailException;
import com.santacarolina.model.Conciliacao;
import com.santacarolina.util.ApiRequest;

public class ConciliacaoDAO {

    private final Logger logger = LogManager.getLogger();
    private final ApiRequest<Conciliacao> apiRequest;
    private static final String MAPPING = "/conciliacoes";

    public ConciliacaoDAO() { this.apiRequest = new ApiRequest<>(Conciliacao.class); }

    public List<Conciliacao> findAll() throws FetchFailException { 
        try {
            return apiRequest.getListRequest(MAPPING);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new FetchFailException(e, logger);
        } 
    } 

    public List<Conciliacao> findByExtrato(long extratoId) throws FetchFailException {
        String query = MAPPING + "/extrato/" + extratoId;
        try {
            return apiRequest.getListRequest(query);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new FetchFailException(e, logger);
        }
    }

    public List<Conciliacao> findByDuplicata(long duplicataId) throws FetchFailException {
        String query = MAPPING + "/duplicata/" + duplicataId;
        try {
            return apiRequest.getListRequest(query);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new FetchFailException(e, logger);
        }
    }

    public void save(Conciliacao conciliacao) throws SaveFailException { 
        try {
            apiRequest.postRequest(MAPPING, conciliacao);
        } catch (IOException | URISyntaxException | InterruptedException e) {
            throw new SaveFailException(e, logger);
        } 
    }

    public void saveAll(List<Conciliacao> list) throws SaveFailException {
        String query = MAPPING + "/batch";
        try {
            apiRequest.postListRequest(query, list);
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

    public void deleteAll(List<Conciliacao> list) throws DeleteFailException {
        String query = MAPPING + "/delete-batch";
        try {
            apiRequest.postListRequest(query, list);
        } catch (IOException | URISyntaxException | InterruptedException e) {
            throw new DeleteFailException(e, logger);
        }
    }

}
