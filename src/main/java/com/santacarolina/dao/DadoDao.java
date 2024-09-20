package com.santacarolina.dao;

import com.santacarolina.exceptions.DeleteFailException;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.exceptions.SaveFailException;
import com.santacarolina.model.beans.DadoBancario;
import com.santacarolina.services.DadoService;
import com.santacarolina.util.ApiRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

public class DadoDao {

    private final Logger logger = LogManager.getLogger();
    private ApiRequest<DadoBancario> apiRequest = new ApiRequest<>();
    private DadoService service = new DadoService();
    private static final String MAPPING = "/contas";

    public List<DadoBancario> findAll() throws FetchFailException {
        return service.findAll(MAPPING);
    }

    public Optional<DadoBancario> findById(long id) throws FetchFailException {
        String query = MAPPING + "/" + id;
        return service.findById(query);
    }

    public Optional<DadoBancario> getEqual(DadoBancario d) throws FetchFailException {
        try {
            String query = MAPPING + "/info?banco=" + d.getBanco().getId() +
                    "&agencia=" + d.getAgencia() +
                    "&numeroConta=" + d.getNumeroConta();
            return apiRequest.getRequest(query, DadoBancario.class);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new FetchFailException(e, logger);
        }
    }

    public void save(DadoBancario d) throws SaveFailException {
        try {
            apiRequest.postRequest(MAPPING, d);
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
