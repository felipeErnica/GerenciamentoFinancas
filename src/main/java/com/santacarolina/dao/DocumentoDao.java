package com.santacarolina.dao;

import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.model.beans.DocumentoFiscal;
import com.santacarolina.services.DocService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class DocumentoDao {

    private final Logger logger = LogManager.getLogger();
    private static final String MAPPING = "/documentos";
    private final DocService service = new DocService();

    public List<DocumentoFiscal> findAll() throws FetchFailException {
        return service.getListRequest(MAPPING);
    }

    public Optional<DocumentoFiscal> findById(long id) throws FetchFailException {
        String query = MAPPING + "/" + id;
        return service.getRequest(query);
    }

}
