package com.santacarolina.services;

import com.santacarolina.exceptions.DeleteFailException;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.exceptions.SaveFailException;
import com.santacarolina.model.beans.ChavePix;
import com.santacarolina.model.beans.Contato;
import com.santacarolina.model.beans.DadoBancario;
import com.santacarolina.dto.ContatoJsonDto;
import com.santacarolina.util.ApiRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ContatoService {

    private final Logger logger = LogManager.getLogger();

    ApiRequest<ContatoJsonDto> apiContato = new ApiRequest<>();

    public List<Contato> getContactList(String queryString) throws FetchFailException {
        List<ContatoJsonDto> listDTO = null;
        try {
            listDTO = apiContato.getListRequest(queryString, ContatoJsonDto.class);
            return listDTO.stream()
                    .map(this::convertJson)
                    .sorted(Comparator.comparing(Contato::getNome))
                    .collect(Collectors.toList());
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new FetchFailException(e, logger);
        }
    }

    public Optional<Contato> findContato(String queryString) throws FetchFailException {
        try {
            Optional<ContatoJsonDto> contatoJsonDto = apiContato.getRequest(queryString,ContatoJsonDto.class);
            return contatoJsonDto.map(this::convertJson);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new FetchFailException(e, logger);
        }
    }

    public List<ChavePix> getPix(String queryString) throws FetchFailException {
        try {
            ApiRequest<ChavePix> apiPix = new ApiRequest<>();
            return apiPix.getListRequest(queryString,ChavePix.class);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new FetchFailException(e, logger);
        }
    }

    public List<DadoBancario> getDados(String queryString) throws FetchFailException {
        try {
            ApiRequest<DadoBancario> apiDados = new ApiRequest<>();
            return apiDados.getListRequest(queryString, DadoBancario.class);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new FetchFailException(e, logger);
        }
    }

    public void save(String queryString, Contato c) throws SaveFailException {
        try {
            ApiRequest<Contato> apiPost = new ApiRequest<>();
            apiPost.postRequest(queryString, c);
        } catch (IOException | URISyntaxException | InterruptedException e) {
            throw new SaveFailException(e, logger);
        }
    }

    public void delete(String query) throws DeleteFailException {
        try {
            apiContato.deleteRequest(query);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new DeleteFailException(e, logger);
        }
    }

    private Contato convertJson(ContatoJsonDto contatoJson) {
        if (contatoJson.id() == 0) return null;
        else return new Contato.ContatoBuilder()
                .setId(contatoJson.id())
                .setNome(contatoJson.nome())
                .setCpf(contatoJson.cpf())
                .setCnpj(contatoJson.cnpj())
                .setIe(contatoJson.ie())
                .build();
    }

}
