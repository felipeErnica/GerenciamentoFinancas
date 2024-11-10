package com.santacarolina.dao;

import java.util.List;
import java.util.Optional;

import com.santacarolina.dto.ContaDTO;
import com.santacarolina.exceptions.DeleteFailException;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.exceptions.SaveFailException;
import com.santacarolina.model.ContaBancaria;
import com.santacarolina.util.ServiceTest;

public class ContaDAO {

    private final ServiceTest<ContaBancaria, ContaDTO> service = new ServiceTest<>(ContaDTO.class, ContaBancaria.class);
    private final String MAPPING = "/contasAdm";

    public List<ContaBancaria> findAll() throws FetchFailException { return service.getListRequest(MAPPING); }

    public Optional<ContaBancaria> findEqual(ContaBancaria contaBancaria) throws FetchFailException {
        String query = MAPPING + "/?agencia=" + contaBancaria.getAgencia() +
                "&numeroConta=" + contaBancaria.getNumeroConta() +
                "&bancoId=" + contaBancaria.getBanco().getId();
        return service.getRequest(query);
    }

    public void save(ContaBancaria contaBancaria) throws SaveFailException { service.postRequestDTO(MAPPING, contaBancaria); }

    public Optional<ContaBancaria> findById(long id) throws FetchFailException {
        String query = MAPPING + "/" + id;
        return service.getRequest(query);
    }

    public void deleteById(long id) throws DeleteFailException {
        String query = MAPPING + "/" + id;
        service.deleteRequest(query);
    }

    public void deleteAll(List<ContaDTO> list) throws DeleteFailException {
        String query = MAPPING + "/delete-batch";
        service.deleteAll(query, list);
    }

}
