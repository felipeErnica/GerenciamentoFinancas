package com.santacarolina.services;

import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.model.beans.DadoBancario;
import com.santacarolina.dto.DadoDto;
import com.santacarolina.util.ApiRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DadoService {

    private final Logger logger = LogManager.getLogger();
    private ApiRequest<DadoDto> apiRequest = new ApiRequest<>();

    public Optional<DadoBancario> getEqual(String query) throws FetchFailException {
        try {
            return apiRequest.getRequest(query, DadoDto.class).map(this::fromDto);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            logger.error(e);
            throw new FetchFailException(e, logger);
        }
    }

    public List<DadoBancario> findAll(String mapping) throws FetchFailException {
        try {
            return apiRequest.getListRequest(mapping, DadoDto.class).stream()
                    .map(this::fromDto)
                    .collect(Collectors.toList());
        } catch (URISyntaxException | IOException | InterruptedException e) {
            logger.error(e);
            throw new FetchFailException(e, logger);
        }
    }

    public Optional<DadoBancario> findById(String query) throws FetchFailException {
        try {
            return apiRequest.getRequest(query, DadoDto.class).map(this::fromDto);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            logger.error(e);
            throw new FetchFailException(e, logger);
        }
    }

    private  DadoBancario fromDto(DadoDto dto) {
        return new DadoBancario.Builder()
                .setId(dto.id())
                .setAgencia(dto.agencia())
                .setBanco(dto.banco())
                .setContato(dto.contato())
                .setNumeroConta(dto.numeroConta())
                .setPixId(dto.pixId())
                .build();
    }

}
