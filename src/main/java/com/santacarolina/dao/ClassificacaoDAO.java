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
import com.santacarolina.model.ClassificacaoContabil;
import com.santacarolina.util.ApiRequest;

public class ClassificacaoDAO {

    private final Logger logger = LogManager.getLogger();
    private final ApiRequest<ClassificacaoContabil> apiRequest;
    private final String MAPPING = "/classificacao";

    public ClassificacaoDAO() { this.apiRequest = new ApiRequest<>(ClassificacaoContabil.class); }

    public List<ClassificacaoContabil> findAll() throws FetchFailException {
        try {
            return apiRequest.getListRequest(MAPPING);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new FetchFailException(e, logger);
        }
    }

    public Optional<ClassificacaoContabil> findByNumero(long numero) throws FetchFailException {
        try {
            return apiRequest.getRequest(MAPPING + "/numeroIdentificacao=" + numero);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new FetchFailException(e, logger);
        }
    }

    public Optional<ClassificacaoContabil> findById(long classificacaoId) throws FetchFailException {
        String query = MAPPING + "/" + classificacaoId;
        try {
            return apiRequest.getRequest(query);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new FetchFailException(e, logger);
        }
    }

    public Optional<ClassificacaoContabil> findByNome(String nome) throws FetchFailException {
        String query = MAPPING + "/nome=" + nome.replace(" ", "+");
        try {
            return apiRequest.getRequest(query);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new FetchFailException(e, logger);
        }
    }

    public void save(ClassificacaoContabil classificacao) throws SaveFailException { 
        try {
            apiRequest.postRequest(MAPPING, classificacao);
        } catch (IOException | URISyntaxException | InterruptedException e) {
            throw new SaveFailException(e, logger);
        } 
    }

    public void deleteById(long id) throws DeleteFailException {
        String query = MAPPING + "/" + id;
        try {
            apiRequest.deleteRequest(query);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new DeleteFailException(e, logger);
        }
    }

    public void deleteAll(List<ClassificacaoContabil> list) throws DeleteFailException {
        String query = MAPPING + "/delete-batch";
        try {
            apiRequest.postListRequest(query, list);
        } catch (IOException | URISyntaxException | InterruptedException e) {
            throw new DeleteFailException(e, logger);
        }
    }

}
