package com.santacarolina.dto;

import com.santacarolina.dao.DuplicataDAO;
import com.santacarolina.dao.ProdutoDAO;
import com.santacarolina.enums.FluxoCaixa;
import com.santacarolina.enums.TipoDoc;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.FromDTO;
import com.santacarolina.model.DocumentoFiscal;

import com.santacarolina.model.Duplicata;
import com.santacarolina.model.Produto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class DocumentoDTO implements FromDTO<DocumentoFiscal> {

    private long id;
    private Long numDoc;
    private TipoDoc tipoDoc;
    private Long emissorId;
    private String caminho;
    private Long pastaId;
    private double valor;
    private LocalDate dataEmissao;
    private FluxoCaixa fluxoCaixa;
    private List<DuplicataDTO> duplicataList;
    private List<ProdutoDTO> produtoList;
    private String nomePasta;
    private String nomeContato;

    public DocumentoDTO() {}

    public DocumentoDTO (DocumentoFiscal d) {
        this.id = d.getId();
        this.numDoc = d.getNumDoc();
        this.tipoDoc = d.getTipoDoc();
        this.emissorId = d.getEmissorId();
        this.caminho = d.getCaminho();
        this.pastaId = d.getPastaId();
        this.valor = d.getValor();
        this.dataEmissao = d.getDataEmissao();
        this.fluxoCaixa = d.getFluxoCaixa();
        this.duplicataList = d.getDuplicatas().stream()
                .map(Duplicata::toDTO)
                .collect(Collectors.toList());
        this.produtoList = d.getProdutos().stream()
                .map(Produto::toDTO)
                .collect(Collectors.toList());
    }

    public long getId() { return id; }
    public Long getNumDoc() { return numDoc; }
    public TipoDoc getTipoDoc() { return tipoDoc; }
    public Long getEmissorId() { return emissorId; }
    public String getCaminho() { return caminho; }
    public Long getPastaId() { return pastaId; }
    public double getValor() { return valor; }
    public LocalDate getDataEmissao() { return dataEmissao; }
    public FluxoCaixa getFluxoCaixa() { return fluxoCaixa; }
    
    public List<DuplicataDTO> getDuplicataList() {
        if (duplicataList == null) {
            try {
                duplicataList = new DuplicataDAO().findByDocId(id);
            } catch (FetchFailException e) {
                duplicataList = null;
            }
        }
        return duplicataList; 
    }

    public List<ProdutoDTO> getProdutoList() { 
        if (produtoList == null) {
            try {
                produtoList = new ProdutoDAO().findByDocId(id);
            } catch (FetchFailException e) {
                produtoList = null;
            }
        }
        return produtoList; 
    }

    public String getNomePasta() { return nomePasta; }
    public String getNomeContato() { return nomeContato; }

    @Override
    public DocumentoFiscal fromDTO() { return new DocumentoFiscal(this); }

}
