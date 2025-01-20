package com.santacarolina.dao;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.santacarolina.exceptions.DeleteFailException;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.exceptions.SaveFailException;
import com.santacarolina.model.DocumentoFiscal;
import com.santacarolina.model.Duplicata;
import com.santacarolina.util.ApiRequest;

public class DuplicataDAO {

    private final Logger logger = LogManager.getLogger();
    private final ApiRequest<Duplicata> apiRequest;
    private static final String MAPPING = "/duplicatas";

    public DuplicataDAO() {
        this.apiRequest = new ApiRequest<>(Duplicata.class);
    }

    public List<Duplicata> findAll() throws FetchFailException { 
        try {
            return apiRequest.getListRequest(MAPPING);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new FetchFailException(e, logger);
        } 
    }

    public List<Duplicata> findByDoc(DocumentoFiscal documentoFiscal) throws FetchFailException {
        String query = MAPPING  + "/documento=" + documentoFiscal.getId();
        try {
            return apiRequest.getListRequest(query);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new FetchFailException(e, logger);
        }
    }

    public List<Duplicata> findPagas() throws FetchFailException {
        String query = MAPPING + "/pagas";
        try {
            return apiRequest.getListRequest(query);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new FetchFailException(e, logger);
        }
    }

    public List<Duplicata> findNaoPagas() throws FetchFailException {
        String query = MAPPING + "/naoPagas";
        try {
            return apiRequest.getListRequest(query);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new FetchFailException(e, logger);
        }
    }

    public Optional<Duplicata> findById(long id) throws FetchFailException {
        String query = MAPPING + "/" + id;
        try {
            return apiRequest.getRequest(query);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new FetchFailException(e, logger);
        }
    }

    public void save(Duplicata d) throws SaveFailException {
        try {
            apiRequest.postRequest(MAPPING, d);
        } catch (IOException | URISyntaxException | InterruptedException e) {
            throw new SaveFailException(e, logger);
        } 
    }

    public void saveAll(List<Duplicata> list) throws SaveFailException {
        String query = MAPPING + "/batch";
        try {
            apiRequest.postListRequest(query, list);
        } catch (IOException | URISyntaxException | InterruptedException e) {
            throw new SaveFailException(e, logger);
        }
    }

    public void deleteAll(List<Duplicata> list) throws DeleteFailException {
        String query = MAPPING + "/delete-batch";
        try {
            apiRequest.postListRequest(query, list);
        } catch (IOException | URISyntaxException | InterruptedException e) {
            throw new DeleteFailException(e, logger);
        }
    }

    public List<Duplicata> findByDocId(long id) throws FetchFailException {
        String query = MAPPING + "/documento=" + id;
        try {
            return apiRequest.getListRequest(query);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new FetchFailException(e, logger);
        }
    }

}
