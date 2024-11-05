package com.santacarolina.dao;

import com.santacarolina.dto.ContatoDTO;
import com.santacarolina.exceptions.DeleteFailException;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.exceptions.SaveFailException;
import com.santacarolina.model.Contato;
import com.santacarolina.util.Service;

import java.util.List;
import java.util.Optional;

public class ContatoDAO {

    private final Service<Contato, ContatoDTO> service = new Service<>(ContatoDTO.class);
    private static final String MAPPING = "/contatos";

    public List<Contato> findAll() throws FetchFailException { return service.getListRequest(MAPPING); }

    public Optional<Contato> findByCpf(String cpf) throws FetchFailException {
        String query = MAPPING + "/cpf=" + cpf;
        return service.getRequest(query);
    }

    public Optional<Contato> findByCnpj(String cnpj) throws FetchFailException {
        String query = MAPPING + "/cnpj=" + cnpj;
        return service.getRequest(query);
    }

    public Optional<Contato> findByIe(String ie) throws FetchFailException {
        String query = MAPPING + "/ie=" + ie;
        return service.getRequest(query);
    }

    public Optional<Contato> findByNome(String nome) throws FetchFailException {
        String queryString = MAPPING + "/nome=" + nome.replace(" ","+");
        return service.getRequest(queryString);
    }

    public Contato save(Contato c) throws SaveFailException { return service.postRequestWithResponse(MAPPING, c); }

    public Optional<Contato> findById(long id) throws FetchFailException {
        String query = MAPPING + "/" + id;
        return service.getRequest(query);
    }

    public void deleteById(long id) throws DeleteFailException {
        String query = MAPPING + "/" + id;
        service.deleteRequest(query);
    }
}
