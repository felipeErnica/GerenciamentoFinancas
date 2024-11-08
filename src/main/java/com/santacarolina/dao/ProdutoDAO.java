package com.santacarolina.dao;

import com.santacarolina.dto.ProdutoDTO;
import com.santacarolina.exceptions.DeleteFailException;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.model.DocumentoFiscal;
import com.santacarolina.model.Produto;
import com.santacarolina.util.Service;

import java.util.List;
import java.util.stream.Collectors;

public class ProdutoDAO {

    private final String MAPPING  = "/produtos";
    private Service<Produto, ProdutoDTO> service;

    public ProdutoDAO() { this.service = new Service<>(ProdutoDTO.class); }

    public List<Produto> findByDoc(DocumentoFiscal documentoFiscal) throws FetchFailException {
        String query = MAPPING  + "/documento=" + documentoFiscal.getId();
        return service.getListRequest(query);
    }

    public List<ProdutoDTO> findAll() throws FetchFailException {
        return service.getListRequestDTO(MAPPING);
    }

    public List<Produto> findAllProdutos() throws FetchFailException {
        return service.getListRequestDTO(MAPPING).stream()
                .map(ProdutoDTO::fromDTO)
                .collect(Collectors.toList());
    }

    public void deleteAll(List<ProdutoDTO> list) throws DeleteFailException {
        String query = MAPPING + "/delete-batch";
        service.deleteListDTO(query, list);
    }

}
