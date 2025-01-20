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
import com.santacarolina.model.Extrato;
import com.santacarolina.util.ApiRequest;

public class ExtratoDAO {

    private final Logger logger = LogManager.getLogger();
    private final ApiRequest<Extrato> apiRequest = new ApiRequest<>(Extrato.class);
    private final String MAPPING = "/extratos";

    public List<Extrato> findByConta(long contaId) throws FetchFailException {
        String query = MAPPING + "/contaId=" + contaId;
        try {
            return apiRequest.getListRequest(query);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new FetchFailException(e, logger);
        }
    }

    public List<Extrato> findByConciliacao(boolean isPayed) throws FetchFailException {
        String query = MAPPING + "/isConciliado=" + isPayed;
        try {
            return apiRequest.getListRequest(query);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new FetchFailException(e, logger);
        }
    }

    public void save(Extrato extrato) throws SaveFailException {
        try {
            apiRequest.postRequest(MAPPING, extrato);
        } catch (IOException | URISyntaxException | InterruptedException e) {
            throw new SaveFailException(e, logger);
        } 
    }

    public void saveAll(List<Extrato> list) throws SaveFailException {
        String query = MAPPING + "/batch";
        try {
            apiRequest.postListRequest(query, list);
        } catch (IOException | URISyntaxException | InterruptedException e) {
            throw new SaveFailException(e, logger);
        }
    }

    public Optional<Extrato> findById(long id) throws FetchFailException {
        String query = MAPPING + "/" + id;
        try {
            return apiRequest.getRequest(query);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new FetchFailException(e, logger);
        }
    }

    public void deleteAll(List<Extrato> list) throws DeleteFailException {
        String query = MAPPING + "/delete-batch";
        try {
            apiRequest.postListRequest(query, list);
        } catch (IOException | URISyntaxException | InterruptedException e) {
            throw new DeleteFailException(e, logger);
        }
    }

}
