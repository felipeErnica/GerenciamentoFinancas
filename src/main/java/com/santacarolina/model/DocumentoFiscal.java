package com.santacarolina.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.santacarolina.dao.ContatoDAO;
import com.santacarolina.dao.DuplicataDAO;
import com.santacarolina.dao.PastaDAO;
import com.santacarolina.dao.ProdutoDAO;
import com.santacarolina.dto.DocumentoDTO;
import com.santacarolina.enums.FluxoCaixa;
import com.santacarolina.enums.TipoDoc;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.ToDTO;

public class DocumentoFiscal implements Cloneable, Serializable, ToDTO<DocumentoDTO> {

    private long id;
    private Long numDoc;
    private TipoDoc tipoDoc;
    private Contato emissor;
    private long emissorId;
    private String caminho;
    private PastaContabil pastaContabil;
    private long pastaId;
    private double valor;
    private LocalDate dataEmissao;
    private FluxoCaixa fluxoCaixa;
    private List<Duplicata> duplicatas;
    private List<Produto> produtos;

    public DocumentoFiscal (DocumentoDTO dto) {
        this.id = dto.getId();
        this.numDoc = dto.getNumDoc();
        this.tipoDoc = dto.getTipoDoc();
        this.emissorId = dto.getEmissorId();
        this.caminho = dto.getCaminho();
        this.pastaId = dto.getPastaId();
        this.valor = dto.getValor();
        this.dataEmissao = dto.getDataEmissao();
        this.fluxoCaixa = dto.getFluxoCaixa();
    }

    public DocumentoFiscal() {}

    public long getId() { return id; }
    public Long getNumDoc() { return numDoc; }
    public String getCaminho() { return caminho; }
    public double getValor() { return this.valor; }
    public LocalDate getDataEmissao() { return dataEmissao; }
    public TipoDoc getTipoDoc() { return tipoDoc; }
    public FluxoCaixa getFluxoCaixa() { return fluxoCaixa; }
    public long getEmissorId() { return emissorId; }
    public long getPastaId() { return pastaId; }
    public boolean isExpense() { return fluxoCaixa == FluxoCaixa.DESPESA; }
    public boolean isIncome() { return fluxoCaixa == FluxoCaixa.RECEITA; }

    public Contato getEmissor() {
        try {
            if (emissor == null) emissor = new ContatoDAO().findById(emissorId).orElse(null);
        } catch (FetchFailException ignored) {
        }
        return emissor;
    }

    public PastaContabil getPastaContabil() {
        try {
            if (pastaContabil == null) pastaContabil = new PastaDAO().findById(pastaId).orElse(null);
        } catch (FetchFailException ignored) {
        }
        return pastaContabil;
    }

    public List<Duplicata> getDuplicatas() {
        try {
            if (duplicatas == null) duplicatas = new DuplicataDAO().findByDoc(this);
        } catch (FetchFailException ignored) {
        }
        return duplicatas;
    }

    public List<Produto> getProdutos() {
        try {
            if (produtos == null) produtos = new ProdutoDAO().findByDoc(this);
        } catch (FetchFailException ignored) {
        }
        return produtos;
    }

    public void setId(long id) { this.id = id; }
    public void setNumDoc(Long numDoc) { this.numDoc = numDoc; }
    public void setTipoDoc(TipoDoc tipoDoc) { this.tipoDoc = tipoDoc; }
    public void setCaminho(String caminho) { this.caminho = caminho; }
    public void setValor(double valor) { this.valor = valor; }
    public void setDataEmissao(LocalDate dataEmissao) { this.dataEmissao = dataEmissao; }
    public void setEmissorId(Long emissorId) { this.emissorId = emissorId; }
    public void setPastaId(Long pastaId) { this.pastaId = pastaId; }
    public void setFluxoCaixa(FluxoCaixa fluxoCaixa) { this.fluxoCaixa = fluxoCaixa; }
    public void setDuplicatas(List<Duplicata> duplicatas) { this.duplicatas = duplicatas; }
    public void setProdutos(List<Produto> produtos) { this.produtos = produtos; }

    public void setEmissor(Contato emissor) {
        this.emissor = emissor;
        this.emissorId = emissor != null ? emissor.getId() : 0;
    }

    public void setPastaContabil(PastaContabil pastaContabil) {
        this.pastaContabil = pastaContabil;
        pastaId = pastaContabil != null ? pastaContabil.getId() : 0;
    }

    public void addProduto(Produto produto){
        if (produtos == null) produtos = new ArrayList<>();
        produto.setDocumento(this);
        if (isExpense()) produto.setValorUnit(Math.abs(produto.getValorUnit())*-1);
        else produto.setValorUnit(Math.abs(produto.getValorUnit()));
        produtos.add(produto);
    }

    public void addDuplicata(Duplicata dup) {
        if (duplicatas == null) duplicatas = new ArrayList<>();
        dup.setDocumento(this);
        if (isExpense()) dup.setValor(Math.abs(dup.getValor())*-1);
        else dup.setValor(Math.abs(dup.getValor()));
        duplicatas.add(dup);
        dup.setNumDup(duplicatas.size());
    }

    @Override
    public DocumentoFiscal clone() {
        DocumentoFiscal clone = new DocumentoFiscal();
        clone.setCaminho(caminho);
        clone.setId(id);
        clone.setEmissorId(emissorId);
        clone.setValor(valor);
        clone.setDataEmissao(dataEmissao);
        clone.setFluxoCaixa(fluxoCaixa);
        clone.setNumDoc(numDoc);
        clone.setPastaId(pastaId);
        clone.setTipoDoc(tipoDoc);
        clone.setDuplicatas(duplicatas);
        clone.setProdutos(produtos);
        return clone;
    }

    @Override
    public DocumentoDTO toDTO() { return new DocumentoDTO(this); }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(tipoDoc != null ? tipoDoc.toString() : "");
        if (numDoc != null) sb.append(" - Número: ").append(numDoc);
        if (emissor != null) sb.append(" - Emissor: ").append(emissor);
        if (dataEmissao != null) sb.append(" - Data: ").append(dataEmissao.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        return sb.toString();
    }

    public String printDoc() {
        final StringBuffer sb = new StringBuffer("DocumentoFiscal{");
        sb.append("id=").append(id);
        sb.append(", numDoc=").append(numDoc);
        sb.append(", tipoDoc=").append(tipoDoc);
        sb.append(", emissorId=").append(emissorId);
        sb.append(", caminho='").append(caminho).append('\'');
        sb.append(", pastaId=").append(pastaId);
        sb.append(", valor=").append(valor);
        sb.append(", dataEmissao=").append(dataEmissao);
        sb.append(", fluxoCaixa=").append(fluxoCaixa);
        sb.append(",duplicataList=[");
        if (duplicatas != null) duplicatas.forEach(d -> sb.append(d.toString()));
        sb.append("]");
        sb.append(", Produtos=[");
        if (produtos != null) produtos.forEach(sb::append);
        sb.append("]");
        sb.append('}');
        return sb.toString();
    }

}
