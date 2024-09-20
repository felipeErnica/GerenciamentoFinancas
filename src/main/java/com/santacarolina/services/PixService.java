package com.santacarolina.services;

import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.model.beans.ChavePix;
import com.santacarolina.dto.PixDto;
import com.santacarolina.util.ApiRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

public class PixService {

    private final Logger logger = LogManager.getLogger();
    private ApiRequest<PixDto> apiRequest = new ApiRequest<>();

    public Optional<ChavePix> getByChave(String query) throws FetchFailException {
        try {
            return apiRequest.getRequest(query,PixDto.class).map(this::fromDto);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            logger.error(e);
            throw new FetchFailException(e, logger);
        }
    }

    private ChavePix fromDto(PixDto dto) {
        return new ChavePix.Builder().setId(dto.id())
                .setContato(dto.contato())
                .setChave(dto.chave())
                .setTipoPix(dto.tipoPix())
                .setDadoId(dto.dadoId())
                .build();
    }

}
