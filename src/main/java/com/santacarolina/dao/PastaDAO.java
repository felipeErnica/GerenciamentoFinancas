package com.santacarolina.dao;

import com.santacarolina.dto.PastaDTO;
import com.santacarolina.exceptions.DeleteFailException;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.exceptions.SaveFailException;
import com.santacarolina.model.PastaContabil;
import com.santacarolina.util.Service;

import java.util.List;
import java.util.Optional;

public class PastaDAO {

    private final Service<PastaContabil, PastaDTO> service = new Service<>(PastaDTO.class);
    private final String MAPPING = "/pastaContabil";

    public List<PastaContabil> findAll() throws FetchFailException { return service.getListRequest(MAPPING); }
    public void save(PastaContabil pastaContabil) throws SaveFailException { service.postRequestDTO(MAPPING, pastaContabil); }

    public Optional<PastaContabil> findByNome(String nome) throws FetchFailException {
        String query = MAPPING + "/nome=" + nome.replace(" ","+");
        return service.getRequest(query);
    }

    public Optional<PastaContabil> findById(long id) throws FetchFailException {
        String query = MAPPING + "/" + id;
        return service.getRequest(query);
    }

    public void deleteById(long id) throws DeleteFailException {
        String query = MAPPING + "/" + id;
        service.deleteRequest(query);
    }

    public void deleteAll(List<PastaDTO> list) throws DeleteFailException {
        String query = MAPPING + "/delete-batch";
        service.deleteList(query, list);
    }

}
