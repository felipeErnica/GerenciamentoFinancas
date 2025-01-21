package com.santacarolina.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.santacarolina.enums.FluxoCaixa;
import com.santacarolina.enums.TipoDoc;
import com.santacarolina.model.Contato;
import com.santacarolina.model.DocumentoFiscal;
import com.santacarolina.model.PastaContabil;

public class DocumentoDTO {

    private long id;
    private Long numDoc;
    private TipoDoc tipoDoc;
    private Contato emissor;
    private String caminhoDocumento;
    private PastaContabil pasta;
    private double valor;
    private LocalDate dataEmissao;
    private FluxoCaixa fluxoCaixa;

    public DocumentoDTO() {}

    public DocumentoDTO(DocumentoFiscal documento) {
        this.id = documento.getId();
        this.numDoc = documento.getNumDoc();
        this.tipoDoc = documento.getTipoDoc();
        this.emissor = documento.getEmissor();
        this.caminhoDocumento = documento.getCaminhoDocumento();
        this.pasta = documento.getPasta();
        this.valor = documento.getValor();
        this.dataEmissao = documento.getDataEmissao();
        this.fluxoCaixa = documento.getFluxoCaixa();
    }

    public long getId() { return id; }
    public Long getNumDoc() { return numDoc; }
    public String getCaminhoDocumento() { return caminhoDocumento; }
    public double getValor() { return valor; }
    public LocalDate getDataEmissao() { return dataEmissao; }
    public TipoDoc getTipoDoc() { return tipoDoc; }
    public FluxoCaixa getFluxoCaixa() { return fluxoCaixa; }
    public Contato getEmissor() { return emissor; }
    public PastaContabil getPasta() { return pasta; }

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
        sb.append(", emissor=").append(emissor);
        sb.append(", pasta=").append(pasta);
        sb.append(", caminho='").append(caminhoDocumento).append('\'');
        sb.append(", valor=").append(valor);
        sb.append(", dataEmissao=").append(dataEmissao);
        sb.append(", fluxoCaixa=").append(fluxoCaixa);
        sb.append('}');
        return sb.toString();
    }

}
