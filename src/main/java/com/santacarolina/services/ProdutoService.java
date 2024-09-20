package com.santacarolina.services;

import com.santacarolina.dto.ProdutoDTO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.model.beans.Produto;
import com.santacarolina.util.ApiRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

public class ProdutoService {

    private final Logger logger = LogManager.getLogger();
    private ApiRequest<ProdutoDTO> apiRequest = new ApiRequest<>();

    public List<Produto> getListRequest(String query) throws FetchFailException {
        try {
            return apiRequest.getListRequest(query, ProdutoDTO.class).stream()
                    .map(p -> new Produto().fromDTO(p))
                    .collect(Collectors.toList());
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new FetchFailException(e, logger);
        }
    }

}
