package com.santacarolina.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.santacarolina.dao.DuplicataDAO;
import com.santacarolina.dao.ProdutoDAO;
import com.santacarolina.enums.FluxoCaixa;
import com.santacarolina.enums.TipoDoc;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.FromDTO;
import com.santacarolina.model.DocumentoFiscal;

public class DocumentoDTO implements FromDTO<DocumentoFiscal> {

    private long id;
    private Long numDoc;
    private TipoDoc tipoDoc;
    private Long emissorId;
    private String caminhoDocumento;
    private Long pastaId;
    private double valor;
    private LocalDate dataEmissao;
    private FluxoCaixa fluxoCaixa;
    private List<DuplicataPlainDTO> duplicataList;
    private List<ProdutoPlainDTO> produtoList;
    private String nomePasta;
    private String nomeContato;

    public DocumentoDTO() {}

    public DocumentoDTO (DocumentoFiscal d) {
        this.id = d.getId();
        this.numDoc = d.getNumDoc();
        this.tipoDoc = d.getTipoDoc();
        this.emissorId = d.getEmissorId();
        this.caminhoDocumento = d.getCaminho();
        this.pastaId = d.getPastaId();
        this.valor = d.getValor();
        this.dataEmissao = d.getDataEmissao();
        this.fluxoCaixa = d.getFluxoCaixa();

        this.duplicataList = d.getDuplicatas().stream()
                .map(dup -> new DuplicataPlainDTO(dup))
                .collect(Collectors.toList());
        
        this.produtoList = d.getProdutos().stream()
                .map(prod -> new ProdutoPlainDTO(prod))
                .collect(Collectors.toList());
    }

    public long getId() { return id; }
    public Long getNumDoc() { return numDoc; }
    public TipoDoc getTipoDoc() { return tipoDoc; }
    public Long getEmissorId() { return emissorId; }
    public String getCaminhoDocumento() { return caminhoDocumento; }
    public Long getPastaId() { return pastaId; }
    public double getValor() { return valor; }
    public LocalDate getDataEmissao() { return dataEmissao; }
    public FluxoCaixa getFluxoCaixa() { return fluxoCaixa; }
    public String getNomePasta() { return nomePasta; }
    public String getNomeContato() { return nomeContato; }

    public List<DuplicataPlainDTO> getDuplicataList() {
        if (duplicataList == null) {
            try {
                duplicataList = new DuplicataDAO().findByDocId(id).stream()
                    .map(dup -> new DuplicataPlainDTO(dup))
                    .collect(Collectors.toList());
            } catch (FetchFailException e) {
                duplicataList = null;
            }
        }
        return duplicataList; 
    }

    public List<ProdutoPlainDTO> getProdutoList() { 
        if (produtoList == null) {
            try {
                produtoList = new ProdutoDAO().findByDocId(id).stream()
                    .map(prod -> new ProdutoPlainDTO(prod))
                    .collect(Collectors.toList());
            } catch (FetchFailException e) {
                produtoList = null;
            }
        }
        return produtoList; 
    }

    @Override
    public DocumentoFiscal fromDTO() { return new DocumentoFiscal(this); }

    @Override
    public String toString() {
        String print = "DocumentoDTO{id=" + id + ", numDoc=" + numDoc + ", tipoDoc=" + tipoDoc + ", emissorId=" + emissorId
                + ", caminhoDocumento=" + caminhoDocumento + ", pastaId=" + pastaId + ", valor=" + valor
                + ", dataEmissao=" + dataEmissao + ", fluxoCaixa=" + fluxoCaixa + ", ";
        StringBuffer bf = new StringBuffer(print);
        getProdutoList().forEach(prod -> bf.append(prod));
        getDuplicataList().forEach(dup -> bf.append(dup));
        return bf.append("}").toString();
    }

}
