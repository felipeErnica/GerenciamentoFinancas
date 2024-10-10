package com.santacarolina.dao;

import com.santacarolina.dto.ContaDTO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.exceptions.SaveFailException;
import com.santacarolina.model.ContaBancaria;
import com.santacarolina.util.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.List;
import java.util.Optional;

public class ContaDAO {

    private final Service<ContaBancaria, ContaDTO> service = new Service<>(ContaBancaria.class, ContaDTO.class);
    private final String MAPPING = "/contasAdm";

    public List<ContaBancaria> findAll() throws FetchFailException { return service.getListRequest(MAPPING); }

    public Optional<ContaBancaria> findEqual(ContaBancaria contaBancaria) throws FetchFailException {
        String query = MAPPING + "/?agencia=" + contaBancaria.getAgencia() +
                "&numeroConta=" + contaBancaria.getNumeroConta() +
                "&bancoId=" + contaBancaria.getBanco().getId();
        return service.getRequest(query);
    }

    public void save(ContaBancaria contaBancaria) throws SaveFailException { service.postRequest(MAPPING, contaBancaria); }

    public Optional<ContaBancaria> findById(long id) throws FetchFailException {
        String query = MAPPING + "/" + id;
        return service.getRequest(query);
    }

}
