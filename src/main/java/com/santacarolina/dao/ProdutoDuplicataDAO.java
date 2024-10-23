package com.santacarolina.dao;


import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.santacarolina.dto.ProdutoDuplicataDTO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.util.ApiRequest;

/**
 * ProdutoDuplicataDAO
 */
public class ProdutoDuplicataDAO {

    private Logger logger = LogManager.getLogger();

    private ApiRequest<ProdutoDuplicataDTO> apiRequest = new ApiRequest<>(ProdutoDuplicataDTO.class);
    private final String MAPPING = "/homePage";

    public List<ProdutoDuplicataDTO> findAll() throws FetchFailException {
        try {
            return apiRequest.getListRequest(MAPPING);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            logger.error(e);
            throw new FetchFailException(e, logger);
        }
    }

}
