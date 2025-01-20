package com.santacarolina.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.santacarolina.dao.DadoDAO;
import com.santacarolina.dao.DocumentoDAO;
import com.santacarolina.dao.PixDAO;
import com.santacarolina.dto.DuplicataDTO;
import com.santacarolina.enums.TipoPagamento;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.ToDTO;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Duplicata implements ToDTO<DuplicataDTO> {

    private long id;
    private DocumentoFiscal documento;
    private long documentoId;
    private int numDup;
    private LocalDate dataVencimento;
    private TipoPagamento tipoPagamento;
    private double valor;
    private String boletoCaminho;
    private DadoBancario dadoBancario;
    private Long pixId;
    private ChavePix pix;
    private Long dadoId;
    private boolean isPayed;

    public Duplicata (DuplicataDTO dto) {
        this.id = dto.getId();
        this.documento = new DocumentoFiscal(dto.getDocumento());
        this.numDup = dto.getNumDup();
        this.dataVencimento = dto.getDataVencimento();
        this.tipoPagamento = dto.getTipoPagamento();
        this.valor = dto.getValor();
        this.boletoCaminho = dto.getBoletoCaminho();
        this.pix = new ChavePix(dto.getPix());
        this.dadoBancario = new DadoBancario(dto.getDado());
        this.isPayed = dto.isPaga();
    }

    public Duplicata() {}

    public DocumentoFiscal getDocumento() {
        try {
            if (documento == null) documento = new DocumentoDAO().findById(documentoId).orElse(null);
        } catch (FetchFailException ignored) {
        }
        return documento;
    }

    public DadoBancario getDadoBancario() {
        try {
            if (dadoBancario == null && dadoId != null) dadoBancario = new DadoDAO().findById(dadoId).orElse(null);
        } catch (FetchFailException ignored) {
        }
        return dadoBancario;
    }

    public ChavePix getPix() {
        try {
            if (pix == null && pixId != null) pix = new PixDAO().findById(pixId).orElse(null);
        } catch (FetchFailException ignored) {
        }
        return pix;
    }

    public long getId() { return id; }
    public int getNumDup() { return numDup; }
    public LocalDate getDataVencimento() { return dataVencimento; }
    public TipoPagamento getTipoPagamento() { return tipoPagamento; }
    public double getValor() { return this.valor; }
    public String getBoletoCaminho() { return boletoCaminho; }
    public boolean isPayed() { return isPayed; }
    public Long getDocumentoId() { return documentoId; }
    public Long getDadoId() { return dadoId; }
    public Long getPixId() { return pixId; }

    public void setDocumento(DocumentoFiscal documento) {
        this.documento = documento;
        this.documentoId = documento != null ? documento.getId() : 0;
    }

    public void setDadoBancario(DadoBancario dadoBancario) {
        this.dadoBancario = dadoBancario;
        this.dadoId = dadoBancario != null ? dadoBancario.getId() : null;
    }

    public void setPix(ChavePix pix) {
        this.pix = pix;
        this.pixId = pix != null ? pix.getId() : null;
    }

    public void setNumDup(int numDup) { this.numDup = numDup; }
    public void setDataVencimento(LocalDate dataVencimento) { this.dataVencimento = dataVencimento; }
    public void setTipoPagamento(TipoPagamento tipoPagamento) { this.tipoPagamento = tipoPagamento; }
    public void setValor(double valor) { this.valor = valor; }
    public void setBoletoCaminho(String boletoCaminho) { this.boletoCaminho = boletoCaminho; }
    public void setPayed(boolean payed) { isPayed = payed; }
    public void setPixId(Long pixId) { this.pixId = pixId; }

    @Override
    public DuplicataDTO toDTO() { return new DuplicataDTO(this); }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Duplicata{");
        sb.append("id=").append(id);
        sb.append(", parcela=").append(numDup);
        sb.append(", dataVenc=").append(dataVencimento);
        sb.append(", metodoPagto=").append(tipoPagamento);
        sb.append(", valor=").append(valor);
        sb.append(", caminhoPagto='").append(boletoCaminho).append('\'');
        sb.append(", dadoBancario=").append(dadoBancario);
        sb.append(", paga=").append(isPayed);
        sb.append('}');
        return sb.toString();
    }

}
