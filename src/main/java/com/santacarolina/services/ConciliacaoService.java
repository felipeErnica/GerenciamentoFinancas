package com.santacarolina.services;

import com.santacarolina.dto.ConciliacaoDTO;
import com.santacarolina.exceptions.SaveFailException;
import com.santacarolina.model.beans.Conciliacao;
import com.santacarolina.util.ApiRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class ConciliacaoService {

    private final Logger logger = LogManager.getLogger();
    private final ApiRequest<ConciliacaoDTO> apiRequest = new ApiRequest<>();

    public void postRequest(String query, Conciliacao conciliacao) throws SaveFailException {
        try {
            apiRequest.postRequest(query, conciliacao.toDTO());
        } catch (IOException | URISyntaxException | InterruptedException e) {
            throw new SaveFailException(e, logger);
        }
    }

    public void postListRequest(String query, List<Conciliacao> list) throws SaveFailException {
        try {
            List<ConciliacaoDTO> dtoList = list.stream()
                    .map(Conciliacao::toDTO)
                    .toList();
            apiRequest.postListRequest(query, dtoList);
        } catch (IOException | URISyntaxException | InterruptedException e) {
            throw new SaveFailException(e, logger);
        }
    }

}
