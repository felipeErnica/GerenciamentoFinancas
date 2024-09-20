package com.santacarolina.model.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.santacarolina.dto.DocumentoDTO;
import com.santacarolina.enums.FluxoCaixa;
import com.santacarolina.enums.TipoDoc;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DocumentoFiscal implements Serializable {

    private long id;
    private String numDoc;
    private TipoDoc tipoDoc;
    private Contato emissor;
    private Long emissorId;
    private String caminho;
    private PastaContabil pastaContabil;
    private Long pastaId;
    private double valor;
    private LocalDate dataEmissao;
    private FluxoCaixa fluxoCaixa;
    private List<Duplicata> duplicatas = new ArrayList<>();
    private List<Produto> produtos = new ArrayList<>();

    public DocumentoFiscalBuilder builder() {
        return new DocumentoFiscalBuilder();
    }

    public DocumentoFiscal fromDTO(DocumentoDTO dto) {
        id = dto.id();
        numDoc = dto.numDoc();
        tipoDoc = dto.tipoDoc();
        emissorId = dto.emissorId();
        caminho = dto.caminho();
        pastaId = dto.pastaId();
        valor = dto.valor();
        dataEmissao = LocalDate.parse(dto.dataEmissao());
        fluxoCaixa = dto.fluxoCaixa();
        return this;
    };

    private DocumentoFiscal fromBuilder(DocumentoFiscalBuilder builder) {
        this.id = builder.id;
        this.numDoc= builder.numDoc;
        this.tipoDoc = builder.tipoDoc;
        this.emissor= builder.emissor;
        this.caminho = builder.docPath;
        this.pastaContabil= builder.pastaContabil;
        this.valor= builder.fluxoCaixa == FluxoCaixa.DESPESA ? Math.abs(builder.valor)*-1 : Math.abs(builder.valor);
        this.dataEmissao= builder.dataEmissao;
        this.fluxoCaixa= builder.fluxoCaixa;
        return this;
    }

    public long getId() { return id; }
    public String getNumDoc() { return numDoc; }
    public TipoDoc getDocType() { return tipoDoc; }
    public Contato getEmissor() { return emissor; }
    public String getCaminho() { return caminho; }
    public PastaContabil getPastaContabil() { return pastaContabil; }
    public double getValor() { return this.valor; }
    public LocalDate getDataEmissao() { return dataEmissao; }
    public List<Duplicata> getDuplicatas() { return duplicatas; }
    public List<Produto> getProdutos() { return produtos; }
    public TipoDoc getTipoDoc() { return tipoDoc; }
    public FluxoCaixa getFluxoCaixa() { return fluxoCaixa; }
    public Long getEmissorId() { return emissorId; }
    public Long getPastaId() { return pastaId; }

    @JsonIgnore
    public boolean isExpense() { return fluxoCaixa == FluxoCaixa.DESPESA; }

    @JsonIgnore
    public boolean isIncome() { return fluxoCaixa == FluxoCaixa.RECEITA; }


    public void setEmissor(Contato emissor) { this.emissor = emissor; }
    public void setNumDoc(String numDoc) { this.numDoc = numDoc; }
    public void setTipoDoc(TipoDoc tipoDoc) { this.tipoDoc = tipoDoc; }
    public void setCaminho(String caminho) { this.caminho = caminho; }
    public void setValor(double valor) { this.valor = isExpense() ? Math.abs(valor)*-1 : Math.abs(valor); }
    public void setDataEmissao(LocalDate dataEmissao) { this.dataEmissao = dataEmissao; }

    public void setFluxoCaixa(FluxoCaixa fluxoCaixa) {
        this.fluxoCaixa = fluxoCaixa;
        if (fluxoCaixa == FluxoCaixa.DESPESA) {
            valor = Math.abs(valor) *-1;
            duplicatas.forEach(d -> d.setValor(Math.abs(d.getValor())*-1));
            produtos.forEach(p -> p.setValorUnit(Math.abs(p.getValorUnit())*-1));
        } else {
            valor = Math.abs(valor);
            duplicatas.forEach(d -> d.setValor(Math.abs(d.getValor())));
            produtos.forEach(p -> p.setValorUnit(Math.abs(p.getValorUnit())));
        }
    }

    public void setPastaContabil(PastaContabil pastaContabil) { this.pastaContabil = pastaContabil; }

    public void addProduto(Produto produto){
        produto.setDocumento(this);
        if (isExpense()) produto.setValorUnit(Math.abs(produto.getValorUnit())*-1);
        else produto.setValorUnit(Math.abs(produto.getValorUnit()));
        produtos.add(produto);
    }

    public void  addDuplicata(Duplicata duplicata){
        duplicata.setDocumento(this);
        duplicatas.add(duplicata);
        if (isExpense()) duplicata.setValor(Math.abs(duplicata.getValor())*-1);
        else duplicata.setValor(Math.abs(duplicata.getValor()));
        duplicata.setNumDup(duplicatas.indexOf(duplicata) + 1);
    }

    public void removeDuplicata(Duplicata duplicata){
        duplicatas.remove(duplicata);
        duplicatas.forEach(d -> d.setNumDup(duplicatas.indexOf(d) + 1));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(tipoDoc != null ? tipoDoc.toString() : "");
        if (StringUtils.isBlank(numDoc)) sb.append(" - Número: ").append(numDoc);
        if (emissor != null) sb.append(" - Emissor: ").append(emissor);
        if (dataEmissao != null) sb.append(" - Data: ").append(dataEmissao.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        return sb.toString();
    }

    public static class DocumentoFiscalBuilder {

        private long id;
        private String numDoc;
        private TipoDoc tipoDoc;
        private Contato emissor;
        private String docPath;
        private PastaContabil pastaContabil;
        private double valor;
        private LocalDate dataEmissao;
        private FluxoCaixa fluxoCaixa;

        public DocumentoFiscalBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public DocumentoFiscalBuilder setNumDoc(String numDoc) {
            this.numDoc = numDoc;
            return this;
        }

        public DocumentoFiscalBuilder setDocType(TipoDoc tipoDoc) {
            this.tipoDoc = tipoDoc;
            return this;
        }

        public DocumentoFiscalBuilder setEmissor(Contato emissor) {
            this.emissor = emissor;
            return this;
        }

        public DocumentoFiscalBuilder setDocPath(String docPath) {
            this.docPath = docPath;
            return this;
        }

        public DocumentoFiscalBuilder setPastaContabil(PastaContabil pastaContabil) {
            this.pastaContabil = pastaContabil;
            return this;
        }

        public DocumentoFiscalBuilder setValor(double valor) {
            this.valor = valor;
            return this;
        }

        public DocumentoFiscalBuilder setDataEmissao(LocalDate dataEmissao) {
            this.dataEmissao = dataEmissao;
            return this;
        }

        public DocumentoFiscalBuilder setFluxoCaixa(FluxoCaixa fluxoCaixa) {
            this.fluxoCaixa = fluxoCaixa;
            return this;
        }

        public DocumentoFiscal build(){ return new DocumentoFiscal().fromBuilder(this); }

    }

}
