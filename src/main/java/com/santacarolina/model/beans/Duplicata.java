package com.santacarolina.model.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.santacarolina.dao.DadoDao;
import com.santacarolina.dao.DocumentoDao;
import com.santacarolina.dto.DuplicataDTO;
import com.santacarolina.dto.nfe.DuplicataNfeDTO;
import com.santacarolina.enums.TipoPagamento;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.DTOConversible;

import java.time.LocalDate;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Duplicata implements DTOConversible<Duplicata, DuplicataDTO> {

    private long id;
    private DocumentoFiscal documento;
    private Long documentoId;
    private int numDup;
    private LocalDate dataVencimento;
    private TipoPagamento tipoPagamento;
    private double valor;
    private String boletoCaminho;
    private DadoBancario dadoBancario;
    private Long dadoId;
    private boolean isPayed;
    private List<Conciliacao> conciliacaos;

    public DocumentoFiscal getDocumento() {
        try {
            if (documento == null) {
                if (documentoId != null) documento = new DocumentoDao().findById(documentoId).orElse(null);
            }
            return documento;
        } catch (FetchFailException e) {
            return null;
        }
    }

    public DadoBancario getDadoBancario() {
        try {
            if (dadoBancario == null) {
                if (dadoId != null) dadoBancario = new DadoDao().findById(id).orElse(null);
            }
            return dadoBancario;
        } catch (FetchFailException e) {
            return null;
        }
    }

    public long getId() { return id; }
    public int getNumDup() { return numDup; }
    public LocalDate getDataVencimento() { return dataVencimento; }
    public TipoPagamento getTipoPagamento() { return tipoPagamento; }
    public double getValor() { return this.valor; }
    public String getBoletoCaminho() { return boletoCaminho; }
    public boolean isPayed() { return isPayed; }
    public List<Conciliacao> getConciliacaos() { return conciliacaos; }

    public void setDocumento(DocumentoFiscal documento) {
        this.documento = documento;
        this.documentoId = documento != null ? documento.getId() : null;
    }

    public void setDadoBancario(DadoBancario dadoBancario) {
        this.dadoBancario = dadoBancario;
        this.dadoId = dadoBancario.getId();
    }

    public void setNumDup(int numDup) { this.numDup = numDup; }
    public void setDataVencimento(LocalDate dataVencimento) { this.dataVencimento = dataVencimento; }
    public void setTipoPagamento(TipoPagamento tipoPagamento) { this.tipoPagamento = tipoPagamento; }
    public void setValor(double valor) { this.valor = documento.isExpense() ? Math.abs(valor) * -1 : Math.abs(valor); }
    public void setBoletoCaminho(String boletoCaminho) { this.boletoCaminho = boletoCaminho; }
    public void setPayed(boolean payed) { isPayed = payed; }
    public Long getDocumentoId() { return documentoId; }
    public Long getDadoId() { return dadoId; }

    @Override
    public Duplicata returnNew() { return new Duplicata(); }

    @Override
    public Duplicata fromDTO(DuplicataDTO dto) {
        this.id = dto.id();
        documentoId = dto.docId();
        numDup = dto.numDup();
        dataVencimento = LocalDate.parse(dto.dataVencimento());
        tipoPagamento = dto.tipoPagamento();
        valor = dto.valor();
        boletoCaminho = dto.boletoCaminho();
        dadoId = dto.dadoId();
        isPayed = dto.paga();
        return this;
    }

    @Override
    public DuplicataDTO toDTO() {
        return new DuplicataDTO(
                documentoId,
                dadoId,
                id,
                numDup,
                tipoPagamento,
                dataVencimento.toString(),
                null,
                boletoCaminho,
                valor,
                isPayed,
                null);
    }

    public Duplicata fromNFe(DuplicataNfeDTO nfe) {
        this.numDup = nfe.nDup();
        this.dataVencimento = LocalDate.parse(nfe.dVenc());
        this.valor = Math.abs(nfe.vDup() * -1);
        return this;
    }

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
        sb.append(", conciliacaos=").append(conciliacaos);
        sb.append('}');
        return sb.toString();
    }

}