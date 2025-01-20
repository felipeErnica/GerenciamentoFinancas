package com.santacarolina.dto;

import java.time.LocalDate;

import com.santacarolina.enums.FluxoCaixa;
import com.santacarolina.enums.TipoDoc;
import com.santacarolina.interfaces.FromDTO;
import com.santacarolina.model.DocumentoFiscal;

public class DocumentoDTO implements FromDTO<DocumentoFiscal> {

    private long id;
    private Long numDoc;
    private TipoDoc tipoDoc;
    private ContatoDTO emissor;
    private String caminhoDocumento;
    private PastaDTO pasta;
    private double valor;
    private LocalDate dataEmissao;
    private FluxoCaixa fluxoCaixa;

    public DocumentoDTO() {}

    public DocumentoDTO (DocumentoFiscal d) {
        this.id = d.getId();
        this.numDoc = d.getNumDoc();
        this.tipoDoc = d.getTipoDoc();
        this.emissor = new ContatoDTO(d.getEmissor());
        this.caminhoDocumento = d.getCaminho();
        this.pasta = new PastaDTO(d.getPastaContabil());
        this.valor = d.getValor();
        this.dataEmissao = d.getDataEmissao();
        this.fluxoCaixa = d.getFluxoCaixa();
    }

    public long getId() { return id; }
    public Long getNumDoc() { return numDoc; }
    public TipoDoc getTipoDoc() { return tipoDoc; }
    public String getCaminhoDocumento() { return caminhoDocumento; }
    public double getValor() { return valor; }
    public LocalDate getDataEmissao() { return dataEmissao; }
    public FluxoCaixa getFluxoCaixa() { return fluxoCaixa; }
    public ContatoDTO getEmissor() { return emissor; }
    public PastaDTO getPasta() { return pasta; }

    @Override
    public DocumentoFiscal fromDTO() { return new DocumentoFiscal(this); }

    @Override
    public String toString() {
        String print = "DocumentoDTO{id=" + id + ", numDoc=" + numDoc + ", tipoDoc=" + tipoDoc + ", emissorId=" + emissor.getId()
                + ", caminhoDocumento=" + caminhoDocumento + ", pastaId=" + pasta.getId() + ", valor=" + valor
                + ", dataEmissao=" + dataEmissao + ", fluxoCaixa=" + fluxoCaixa + ", ";
        StringBuffer bf = new StringBuffer(print);
        return bf.append("}").toString();
    }

}
