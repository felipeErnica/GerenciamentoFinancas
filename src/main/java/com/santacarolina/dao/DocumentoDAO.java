package com.santacarolina.dao;

import java.util.List;
import java.util.Optional;

import com.santacarolina.dto.DocumentoDTO;
import com.santacarolina.exceptions.DeleteFailException;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.exceptions.SaveFailException;
import com.santacarolina.model.DocumentoFiscal;
import com.santacarolina.util.ServiceTest;

public class DocumentoDAO {

    private ServiceTest<DocumentoFiscal, DocumentoDTO> service = new ServiceTest<>(DocumentoDTO.class, DocumentoFiscal.class);
    private static final String MAPPING = "/documentos";

    public List<DocumentoDTO> findAll() throws FetchFailException { return service.getListRequestDTO(MAPPING); }

    public Optional<DocumentoFiscal> findById(long id) throws FetchFailException {
        String query = MAPPING + "/" + id;
        return service.getRequest(query);
    }

    public Optional<DocumentoFiscal> findEqualDoc(DocumentoFiscal documentoFiscal) throws FetchFailException {
        String query = MAPPING + "/doc" +
                "?contatoId=" + documentoFiscal.getEmissorId() +
                "&tipoDoc=" + documentoFiscal.getTipoDoc().name() +
                "&dataEmissao=" + documentoFiscal.getDataEmissao() +
                "&pastaId=" + documentoFiscal.getPastaId() +
                "&valor=" + documentoFiscal.getValor();
        return service.getRequest(query);
    }

    public Optional<DocumentoFiscal> findEqualNota(DocumentoFiscal documentoFiscal) throws FetchFailException {
        String query = MAPPING + "/nota?contatoId=" + documentoFiscal.getEmissorId() +
                "&numDoc=" + documentoFiscal.getNumDoc();
        return service.getRequest(query);
    }

    public void save(DocumentoFiscal documentoFiscal) throws SaveFailException { service.postRequestDTO(MAPPING, documentoFiscal); }

    public void deleteAll(List<DocumentoDTO> list) throws DeleteFailException {
        String query = MAPPING + "/delete-batch";
        service.deleteAll(query, list);
    }

}
