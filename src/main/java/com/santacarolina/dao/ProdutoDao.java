package com.santacarolina.dao;

import com.santacarolina.dto.ProdutoDTO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.model.beans.Produto;
import com.santacarolina.util.ApiRequest;
import com.santacarolina.util.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class ProdutoDao {

    private final Logger logger = LogManager.getLogger();

    private final String MAPPING  = "/produtos";
    private Service<Produto, ProdutoDTO> service;

    public ProdutoDao() { this.service = new Service<>(Produto.class, ProdutoDTO.class); }

    public List<ProdutoDTO> findAll() throws FetchFailException {
        return service.getListRequestDTO(MAPPING);
    }

}
