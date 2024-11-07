package com.santacarolina.dao;

import java.util.List;
import java.util.Optional;

import com.santacarolina.dto.DadoDTO;
import com.santacarolina.exceptions.DeleteFailException;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.exceptions.SaveFailException;
import com.santacarolina.model.Contato;
import com.santacarolina.model.DadoBancario;
import com.santacarolina.util.ServiceTest;

public class DadoDAO {

    private ServiceTest<DadoBancario, DadoDTO> service = new ServiceTest<>(DadoDTO.class, DadoBancario.class);
    private static final String MAPPING = "/contas";

    public List<DadoDTO> findAll() throws FetchFailException { return service.getListRequestDTO(MAPPING); }

    public Optional<DadoBancario> findById(long id) throws FetchFailException {
        String query = MAPPING + "/" + id;
        return service.getRequest(query);
    }

    public Optional<DadoBancario> getEqual(DadoBancario d) throws FetchFailException {
        String query = MAPPING + "/info?agencia=" + d.getAgencia() +
                "&numeroConta=" + d.getNumeroConta() +
                "&bancoId=" + d.getBancoId();
        return service.getRequest(query);
    }

    public List<DadoBancario> findByContato(Contato c) throws FetchFailException {
        if (c == null) return null;
        String queryString = MAPPING + "/contato=" + c.getId();
        return service.getListRequest(queryString);
    }

    public void save(DadoBancario d) throws SaveFailException { service.postRequest(MAPPING, d); }

    public void deleteById(long id) throws DeleteFailException {
        String query = MAPPING + "/" + id;
        service.deleteRequest(query);
    }

}
