package com.santacarolina.dao;

import com.santacarolina.exceptions.DeleteFailException;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.exceptions.SaveFailException;
import com.santacarolina.model.beans.ChavePix;
import com.santacarolina.services.PixService;
import com.santacarolina.util.ApiRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

public class PixDao {

    private final Logger logger = LogManager.getLogger();
    private PixService service = new PixService();
    private ApiRequest<ChavePix> apiRequest = new ApiRequest<>();
    private final String MAPPING = "/chavesPix";

    public List<ChavePix> findAll() throws FetchFailException {
        try {
            return apiRequest.getListRequest(MAPPING, ChavePix.class);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new FetchFailException(e, logger);
        }
    }

    public Optional<ChavePix> findById(long id) throws FetchFailException {
        try {
            String query = MAPPING + "/" + id;
            return apiRequest.getRequest(query, ChavePix.class);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new FetchFailException(e, logger);
        }
    }

    public Optional<ChavePix> getByChave(String chave) throws FetchFailException {
        String query = MAPPING + "/?chave=" + chave;
        return service.getByChave(query);
    }

    public void save(ChavePix c) throws SaveFailException {
        try {
            apiRequest.postRequest(MAPPING, c);
        } catch (IOException | URISyntaxException | InterruptedException e) {
            throw new SaveFailException(e, logger);
        }
    }

    public void deleteById(long id) throws DeleteFailException {
        try {
            String query = MAPPING + "/" + id;
            apiRequest.deleteRequest(query);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new DeleteFailException(e, logger);
        }
    }
}