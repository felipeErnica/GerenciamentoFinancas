package com.santacarolina.dao;

import com.santacarolina.dto.DuplicataDTO;
import com.santacarolina.exceptions.DeleteFailException;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.exceptions.SaveFailException;
import com.santacarolina.model.DocumentoFiscal;
import com.santacarolina.model.Duplicata;
import com.santacarolina.util.Service;

import java.util.List;
import java.util.Optional;

public class DuplicataDAO {

    private final Service<Duplicata, DuplicataDTO> service;
    private static final String MAPPING = "/duplicatas";

    public DuplicataDAO() {
        this.service = new Service<>(DuplicataDTO.class);
    }

    public List<DuplicataDTO> findAll() throws FetchFailException { return service.getListRequestDTO(MAPPING); }

    public List<Duplicata> findByDoc(DocumentoFiscal documentoFiscal) throws FetchFailException {
        String query = MAPPING  + "/documento=" + documentoFiscal.getId();
        return service.getListRequest(query);
    }

    public List<DuplicataDTO> findPagas() throws FetchFailException {
        String query = MAPPING + "/pagas";
        return service.getListRequestDTO(query);
    }

    public List<DuplicataDTO> findNaoPagas() throws FetchFailException {
        String query = MAPPING + "/naoPagas";
        return service.getListRequestDTO(query);
    }

    public Optional<Duplicata> findById(long id) throws FetchFailException {
        String query = MAPPING + "/" + id;
        return service.getRequest(query);
    }

    public void save(Duplicata d) throws SaveFailException { service.postRequestDTO(MAPPING, d); }

    public void saveAll(List<Duplicata> list) throws SaveFailException {
        String query = MAPPING + "/batch";
        service.postListRequest(query, list);
    }

    public void deleteAll(List<DuplicataDTO> list) throws DeleteFailException {
        String query = MAPPING + "/delete-batch";
        service.deleteListDTO(query, list);
    }

}
