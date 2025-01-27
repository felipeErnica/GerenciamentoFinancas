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
import com.santacarolina.dto.DuplicataDTO;
import com.santacarolina.dto.ProdutoDTO;
import com.santacarolina.enums.FluxoCaixa;
import com.santacarolina.enums.TipoDoc;
import com.santacarolina.exceptions.FetchFailException;

public class DocumentoFiscal implements Cloneable, Serializable {

    private long id;
    private Long numDoc;
    private TipoDoc tipoDoc;
    private Contato emissor;
    private long emissorId;
    private String caminhoDocumento;
    private PastaContabil pasta;
    private long pastaId;
    private double valor;
    private LocalDate dataEmissao;
    private FluxoCaixa fluxoCaixa;

    private List<DuplicataDTO> duplicataList;
    private List<ProdutoDTO> produtoList;

    public long getId() { return id; }
    public Long getNumDoc() { return numDoc; }
    public String getCaminhoDocumento() { return caminhoDocumento; }
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
            if (emissor == null && emissorId != 0)
                emissor = new ContatoDAO().findById(emissorId).orElse(null);
        } catch (FetchFailException ignored) {
        }
        return emissor;
    }

    public PastaContabil getPasta() {
        try {
            if (pasta == null && pastaId != 0)
                pasta = new PastaDAO().findById(pastaId).orElse(null);
        } catch (FetchFailException ignored) {
        }
        return pasta;
    }

    public List<DuplicataDTO> getDuplicataList() {
        try {
            if (duplicataList == null)
                duplicataList = new DuplicataDAO().findByDoc(this);
        } catch (FetchFailException ignored) {
        }
        return duplicataList;
    }

    public List<ProdutoDTO> getProdutoList() {
        try {
            if (produtoList == null)
                produtoList = new ProdutoDAO().findByDoc(this);
        } catch (FetchFailException ignored) {
        }
        return produtoList;
    }

    public void setId(long id) { this.id = id; }
    public void setNumDoc(Long numDoc) { this.numDoc = numDoc; }
    public void setTipoDoc(TipoDoc tipoDoc) { this.tipoDoc = tipoDoc; }
    public void setCaminhoDocumento(String caminho) { this.caminhoDocumento = caminho; }
    public void setValor(double valor) { this.valor = valor; }
    public void setDataEmissao(LocalDate dataEmissao) { this.dataEmissao = dataEmissao; }
    public void setEmissorId(Long emissorId) { this.emissorId = emissorId; }
    public void setPastaId(Long pastaId) { this.pastaId = pastaId; }
    public void setFluxoCaixa(FluxoCaixa fluxoCaixa) { this.fluxoCaixa = fluxoCaixa; }
    public void setDuplicataList(List<DuplicataDTO> duplicatas) { this.duplicataList = duplicatas; }
    public void setProdutoList(List<ProdutoDTO> produtos) { this.produtoList = produtos; }

    public void setEmissor(Contato emissor) {
        this.emissor = emissor;
        this.emissorId = emissor != null ? emissor.getId() : 0;
    }

    public void setPasta(PastaContabil pastaContabil) {
        this.pasta = pastaContabil;
        pastaId = pastaContabil != null ? pastaContabil.getId() : 0;
    }

    public void addProduto(ProdutoDTO produto) {
        if (produtoList == null)
            produtoList = new ArrayList<>();
        if (isExpense())
            produto.setValorUnit(Math.abs(produto.getValorUnit()) * -1);
        else
            produto.setValorUnit(Math.abs(produto.getValorUnit()));
        produtoList.add(produto);
    }

    public void addDuplicata(DuplicataDTO dup) {
        if (duplicataList == null)
            duplicataList = new ArrayList<>();
        if (isExpense())
            dup.setValor(Math.abs(dup.getValor()) * -1);
        else
            dup.setValor(Math.abs(dup.getValor()));
        duplicataList.add(dup);
        dup.setNumDup(duplicataList.size());
    }

    @Override
    public DocumentoFiscal clone() {
        DocumentoFiscal clone = new DocumentoFiscal();
        clone.setCaminhoDocumento(caminhoDocumento);
        clone.setId(id);
        clone.setEmissorId(emissorId);
        clone.setValor(valor);
        clone.setDataEmissao(dataEmissao);
        clone.setFluxoCaixa(fluxoCaixa);
        clone.setNumDoc(numDoc);
        clone.setPastaId(pastaId);
        clone.setTipoDoc(tipoDoc);
        clone.setDuplicataList(duplicataList);
        clone.setProdutoList(produtoList);
        return clone;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(tipoDoc != null ? tipoDoc.toString() : "");
        if (numDoc != null)
            sb.append(" - Número: ").append(numDoc);
        if (emissor != null)
            sb.append(" - Emissor: ").append(emissor);
        if (dataEmissao != null)
            sb.append(" - Data: ").append(dataEmissao.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        return sb.toString();
    }

    public String printDoc() {
        final StringBuffer sb = new StringBuffer("DocumentoFiscal{");
        sb.append("id=").append(id);
        sb.append(", numDoc=").append(numDoc);
        sb.append(", tipoDoc=").append(tipoDoc);
        sb.append(", emissorId=").append(emissorId);
        sb.append(", caminho='").append(caminhoDocumento).append('\'');
        sb.append(", pastaId=").append(pastaId);
        sb.append(", valor=").append(valor);
        sb.append(", dataEmissao=").append(dataEmissao);
        sb.append(", fluxoCaixa=").append(fluxoCaixa);
        sb.append(",duplicataList=[");
        if (duplicataList != null)
            duplicataList.forEach(d -> sb.append(d.toString()));
        sb.append("]");
        sb.append(", Produtos=[");
        if (produtoList != null)
            produtoList.forEach(sb::append);
        sb.append("]");
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        DocumentoFiscal equal = (DocumentoFiscal) obj;
        if (equal == this) return true;
        System.out.println(this.id == equal.getId);
        return equal.getId() == this.id;
    }

}
