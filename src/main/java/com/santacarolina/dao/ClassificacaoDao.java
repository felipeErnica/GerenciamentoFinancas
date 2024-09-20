package com.santacarolina.dao;

import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.model.beans.ClassificacaoContabil;
import com.santacarolina.util.ApiRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

public class ClassificacaoDao {

    private final Logger logger = LogManager.getLogger();

    private final ApiRequest<ClassificacaoContabil> apiRequest = new ApiRequest<>();
    private final String MAPPING = "/classificacao";

    public List<ClassificacaoContabil> getAll() throws FetchFailException {
        try {
            return apiRequest.getListRequest(MAPPING,ClassificacaoContabil.class);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new FetchFailException(e, logger);
        }
    }

    public Optional<ClassificacaoContabil> getByNumero(String numero) throws FetchFailException {
        try {
            return apiRequest.getRequest(MAPPING + "?numeroIdentificacao=" + numero, ClassificacaoContabil.class);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new FetchFailException(e, logger);
        }
    }


}
