package com.santacarolina.services;

import com.santacarolina.dto.ExtratoDTO;
import com.santacarolina.model.beans.Extrato;
import com.santacarolina.util.ApiRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

public class ExtratoService {

    private final Logger logger = LogManager.getLogger();
    private ApiRequest<ExtratoDTO> apiRequest = new ApiRequest<>();
    private ApiRequest<ExtratoDTO> apiConciliacao = new ApiRequest<>();

    public List<Extrato> getListRequest(String query) throws URISyntaxException, IOException, InterruptedException {
        return apiRequest.getListRequest(query, ExtratoDTO.class).stream()
                .map(d -> new Extrato().fromDTO(d))
                .collect(Collectors.toList());
    }

    public List<ExtratoDTO> getListConciliacaoRequest(String query) throws URISyntaxException, IOException, InterruptedException {
        return apiConciliacao.getListRequest(query, ExtratoDTO.class);
    }
}
