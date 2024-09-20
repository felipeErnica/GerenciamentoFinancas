package com.santacarolina.dao;

import com.santacarolina.exceptions.DeleteFailException;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.exceptions.SaveFailException;
import com.santacarolina.model.beans.ChavePix;
import com.santacarolina.model.beans.Contato;
import com.santacarolina.model.beans.DadoBancario;
import com.santacarolina.services.ContatoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class ContatoDao {

    private final Logger logger = LogManager.getLogger();
    private ContatoService service = new ContatoService();
    private static final String MAPPING = "/contatos";

    public List<Contato> findAll() throws FetchFailException {
        return service.getContactList(MAPPING);
    }

    public Optional<Contato> findByDocNumber(Contato c) throws FetchFailException {
        String stringRequest = MAPPING + "/doc?";
        StringBuilder stringParam = new StringBuilder();
        if (c.getCpf() != null && !c.getCpf().isEmpty()) stringParam.append("cpf=").append(c.getCpf());
        if (c.getCnpj() != null && !c.getCnpj().isEmpty()) {
            if (!stringParam.isEmpty()) stringParam.append("&");
            stringParam.append("cnpj=").append(c.getCnpj());
        }
        if (c.getIe() != null && !c.getIe().isEmpty()) {
            if (!stringParam.isEmpty()) stringParam.append("&");
            stringParam.append("ie=").append(c.getIe());
        }
        String queryString = stringRequest + stringParam.toString();
        logger.debug(queryString);
        return service.findContato(queryString);
    }

    public Optional<Contato> findByCpf(String cpf) throws FetchFailException {
        String query = MAPPING + "/cpf=" + cpf;
        return service.findContato(query);
    }

    public Optional<Contato> findByCnpj(String cnpj) throws FetchFailException {
        String query = MAPPING + "/cpf=" + cnpj;
        return service.findContato(query);
    }

    public Optional<Contato> findByIe(String ie) throws FetchFailException {
        String query = MAPPING + "/cpf=" + ie;
        return service.findContato(query);
    }

    public List<ChavePix> getPix(Contato c) throws FetchFailException {
        String queryString = MAPPING + "/" + c.getId() + "/pix";
        return service.getPix(queryString);
    }

    public List<DadoBancario> findContas(Contato c) throws FetchFailException {
        String queryString = MAPPING + "/" + c.getId() + "/contas";
        return service.getDados(queryString);
    }

    public Optional<Contato> findByNome(String nome) throws FetchFailException {
        String queryString = MAPPING + "/info?nome=" + nome.replace(" ","+");
        return service.findContato(queryString);
    }

    public void save(Contato c) throws SaveFailException {
        service.save(MAPPING, c);
    }

    public Contato findById(long id) throws FetchFailException {
        String query = MAPPING + "/" + id;
        return service.findContato(query).orElse(null);
    }

    public void deleteById(long id) throws DeleteFailException {
        String query = MAPPING + "/" + id;
        service.delete(query);
    }
}
