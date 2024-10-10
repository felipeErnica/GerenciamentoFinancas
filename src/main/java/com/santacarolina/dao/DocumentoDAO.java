package com.santacarolina.dao;

import com.santacarolina.dto.DocumentoDTO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.exceptions.SaveFailException;
import com.santacarolina.model.DocumentoFiscal;
import com.santacarolina.util.Service;

import java.util.List;
import java.util.Optional;

public class DocumentoDAO {

    private Service<DocumentoFiscal, DocumentoDTO> service = new Service<>(DocumentoFiscal.class, DocumentoDTO.class);
    private static final String MAPPING = "/documentos";

    public List<DocumentoDTO> findAll() throws FetchFailException { return service.getListRequestDTO(MAPPING); }

    public Optional<DocumentoFiscal> findById(long id) throws FetchFailException {
        String query = MAPPING + "/" + id;
        return service.getRequest(query);
    }

    public boolean exists(DocumentoFiscal documentoFiscal) throws FetchFailException {
        String query = MAPPING + "/doc" +
                "?contatoId=" + documentoFiscal.getEmissorId() +
                "&tipoDoc=" + documentoFiscal.getTipoDoc().name() +
                "&dataEmissao=" + documentoFiscal.getDataEmissao() +
                "&pastaId=" + documentoFiscal.getPastaId() +
                "&valor=" + documentoFiscal.getValor();
        return service.getRequest(query).isPresent();
    }

    public boolean notaExists(DocumentoFiscal documentoFiscal) throws FetchFailException {
        String query = MAPPING + "/nota?contatoId=" + documentoFiscal.getEmissorId() +
                "&numDoc=" + documentoFiscal.getNumDoc();
        return service.getRequest(query).isPresent();
    }

    public void save(DocumentoFiscal documentoFiscal) throws SaveFailException { service.postRequest(MAPPING, documentoFiscal); }
}
