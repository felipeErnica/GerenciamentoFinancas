package com.santacarolina.services;

import com.santacarolina.dto.DocumentoDTO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.model.beans.DocumentoFiscal;
import com.santacarolina.util.ApiRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class DocService {

    private final Logger logger = LogManager.getLogger();
    private final ApiRequest<DocumentoDTO> apiRequest = new ApiRequest<>();

    public List<DocumentoFiscal> getListRequest(String query) throws FetchFailException {
        try {
            return apiRequest.getListRequest(query,DocumentoDTO.class).stream()
                    .map(dto -> new DocumentoFiscal().fromDTO(dto))
                    .collect(Collectors.toList());
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new FetchFailException(e, logger);
        }
    }

    public Optional<DocumentoFiscal> getRequest(String query) throws FetchFailException {
        try {
            return apiRequest.getRequest(query, DocumentoDTO.class).map(new DocumentoFiscal()::fromDTO);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new FetchFailException(e, logger);
        }
    }

}
