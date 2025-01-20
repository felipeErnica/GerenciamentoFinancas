package com.santacarolina.dao;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.model.ProdutoDuplicata;
import com.santacarolina.util.ApiRequest;

/**
 * ProdutoDuplicataDAO
 */
public class ProdutoDuplicataDAO {

    private final Logger logger = LogManager.getLogger();

    private ApiRequest<ProdutoDuplicata> apiRequest;

    public ProdutoDuplicataDAO() {
        this.apiRequest = new ApiRequest<>(ProdutoDuplicata.class);
    }

    public List<ProdutoDuplicata> findAll() throws FetchFailException {
        try {
            return apiRequest.getListRequest("/homePage");
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new FetchFailException(e, logger);
        }
    }

}
