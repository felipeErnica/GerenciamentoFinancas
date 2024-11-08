package com.santacarolina.dao;

import com.santacarolina.dto.ClassificacaoDTO;
import com.santacarolina.exceptions.DeleteFailException;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.exceptions.SaveFailException;
import com.santacarolina.model.ClassificacaoContabil;
import com.santacarolina.util.Service;

import java.util.List;
import java.util.Optional;

public class ClassificacaoDAO {

    private final Service<ClassificacaoContabil, ClassificacaoDTO> service;
    private final String MAPPING = "/classificacao";

    public ClassificacaoDAO() { this.service = new Service<>(ClassificacaoDTO.class); }

    public List<ClassificacaoContabil> findAll() throws FetchFailException {
        return service.getListRequest(MAPPING);
    }

    public List<ClassificacaoDTO> findAllDTO () throws FetchFailException {
        return service.getListRequestDTO(MAPPING);
    }

    public Optional<ClassificacaoDTO> findByIdDTO(long classificacaoId) throws FetchFailException {
        String query = MAPPING + "/" + classificacaoId;
        return service.getRequestDTO(query);
    }

    public Optional<ClassificacaoContabil> findByNumero(String numero) throws FetchFailException {
        return service.getRequest(MAPPING + "/numeroIdentificacao=" + numero);
    }

    public Optional<ClassificacaoContabil> findById(long classificacaoId) throws FetchFailException {
        String query = MAPPING + "/" + classificacaoId;
        return service.getRequest(query);
    }

    public Optional<ClassificacaoContabil> findByNome(String nome) throws FetchFailException {
        String query = MAPPING + "/nome=" + nome.replace(" ", "+");
        return service.getRequest(query);
    }

    public void save(ClassificacaoContabil classificacao) throws SaveFailException { 
        service.postRequestDTO(MAPPING, classificacao); 
    }

    public void deleteById(long id) throws DeleteFailException {
        String query = MAPPING + "/" + id;
        service.deleteRequest(query);
    }

}
