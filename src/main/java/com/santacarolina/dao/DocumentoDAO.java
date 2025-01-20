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
import com.santacarolina.util.ApiRequest;

public class DocumentoDAO {

    private final Logger logger = LogManager.getLogger();
    private ApiRequest<DocumentoFiscal> apiRequest = new ApiRequest<>(DocumentoFiscal.class);
    private static final String MAPPING = "/documentos";

    public List<DocumentoFiscal> findAll() throws FetchFailException {
        try {
            return apiRequest.getListRequest(MAPPING);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new FetchFailException(e, logger);
        } 
    }

    public Optional<DocumentoFiscal> findById(long id) throws FetchFailException {
        String query = MAPPING + "/" + id;
        try {
            return apiRequest.getRequest(query);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new FetchFailException(e, logger);
        }
    }

    public Optional<DocumentoFiscal> findEqualDoc(DocumentoFiscal documentoFiscal) throws FetchFailException {
        String query = MAPPING + "/doc" +
                "?contatoId=" + documentoFiscal.getContatoId() +
                "&tipoDoc=" + documentoFiscal.getTipoDoc().name() +
                "&dataEmissao=" + documentoFiscal.getDataEmissao() +
                "&pastaId=" + documentoFiscal.getPastaId() +
                "&valor=" + documentoFiscal.getValor();
        try {
            return apiRequest.getRequest(query);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new FetchFailException(e, logger);
        }
    }

    public Optional<DocumentoFiscal> findEqualNota(DocumentoFiscal documentoFiscal) throws FetchFailException {
        String query = MAPPING + "/nota?contatoId=" + documentoFiscal.getContatoId() +
                "&numDoc=" + documentoFiscal.getNumDoc();
        try {
            return apiRequest.getRequest(query);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new FetchFailException(e, logger);
        }
    }

    public void save(DocumentoFiscal documentoFiscal) throws SaveFailException {
        try {
            apiRequest.postRequest(MAPPING, documentoFiscal);
        } catch (IOException | URISyntaxException | InterruptedException e) {
            throw new SaveFailException(e, logger);
        } 
    }

    public void deleteAll(List<DocumentoFiscal> list) throws DeleteFailException {
        String query = MAPPING + "/delete-batch";
        try {
            apiRequest.postListRequest(query, list);
        } catch (IOException | URISyntaxException | InterruptedException e) {
            throw new DeleteFailException(e, logger);
        }
    }

}
