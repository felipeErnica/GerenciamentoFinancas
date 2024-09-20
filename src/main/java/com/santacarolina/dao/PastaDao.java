package com.santacarolina.dao;

import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.exceptions.SaveFailException;
import com.santacarolina.model.beans.PastaContabil;
import com.santacarolina.util.ApiRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PastaDao {

    private final Logger logger = LogManager.getLogger();

    private ApiRequest<PastaContabil> apiRequest = new ApiRequest<>();
    private final String MAPPING = "/pastaContabil";

    public List<PastaContabil> findAll() throws FetchFailException {
        try {
            return apiRequest.getListRequest(MAPPING,PastaContabil.class).stream()
                    .sorted(Comparator.comparing(PastaContabil::getNome))
                    .collect(Collectors.toList());
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new FetchFailException(e, logger);
        }
    }

    public Optional<PastaContabil> findByNome(String nome) throws FetchFailException {
        try {
            String query = MAPPING + "/?nome=" + nome.replace(" ","+");
            return apiRequest.getRequest(query, PastaContabil.class);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new FetchFailException(e, logger);
        }
    }

    public void save(PastaContabil pastaContabil) throws SaveFailException {
        try {
            apiRequest.postRequest(MAPPING, pastaContabil);
        } catch (IOException | URISyntaxException | InterruptedException e) {
            throw new SaveFailException(e, logger);
        }
    }
}
