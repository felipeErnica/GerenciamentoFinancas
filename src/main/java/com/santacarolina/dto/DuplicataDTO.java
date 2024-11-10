package com.santacarolina.dto;

import com.santacarolina.dao.DadoDAO;
import com.santacarolina.dao.PastaDAO;
import com.santacarolina.dao.PixDAO;
import com.santacarolina.enums.FluxoCaixa;
import com.santacarolina.enums.TipoPagamento;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.FromDTO;
import com.santacarolina.model.Duplicata;

import java.time.LocalDate;

public class DuplicataDTO implements FromDTO<Duplicata> {

    private Long docId;
    private Long dadoId;
    private DadoPlainDTO dado;
    private long id;
    private long pastaId;
    private PastaDTO pasta;
    private int numDup;
    private TipoPagamento tipoPagamento;
    private LocalDate dataVencimento;
    private String nomeContato;
    private String boletoCaminho;
    private double valor;
    private boolean paga;
    private long contaId;
    private String conta;
    private Long pixId;
    private PixPlainDTO pix;
    private FluxoCaixa fluxoCaixa;

    public DuplicataDTO() {}

    public DuplicataDTO (Duplicata d) {
        this.docId = d.getDocumentoId();
        this.dadoId = d.getDadoId();
        this.id = d.getId();
        this.numDup = d.getNumDup();
        this.tipoPagamento = d.getTipoPagamento();
        this.dataVencimento = d.getDataVencimento();
        this.boletoCaminho = d.getBoletoCaminho();
        this.valor = d.getValor();
        this.paga = d.isPayed();
        this.pixId = d.getPixId();
    }

    public Long getDocId() { return docId; }
    public Long getDadoId() { return dadoId; }
    public long getId() { return id; }
    public int getNumDup() { return numDup; }
    public TipoPagamento getTipoPagamento() { return tipoPagamento; }
    public LocalDate getDataVencimento() { return dataVencimento; }
    public String getNomeContato() { return nomeContato; }
    public String getBoletoCaminho() { return boletoCaminho; }
    public double getValor() { return valor; }
    public boolean isPaga() { return paga; }
    public long getContaId() { return contaId; }
    public String getConta() { return conta; }
    public Long getPixId() { return pixId; }
    public FluxoCaixa getFluxoCaixa() { return fluxoCaixa; }
    public long getPastaId() { return pastaId; }

    public PastaDTO getPasta() { 
        if (pasta != null) return pasta;
        if (pastaId != 0){
            try {
                pasta = new PastaDAO().findById(pastaId).map(pasta -> pasta.toDTO()).orElse(null);
            } catch (FetchFailException e) {
                pasta = null;
            }
        } 
        return pasta; 
    }
    
    public PixPlainDTO getPix() { 
        if (pix != null) return pix;
        if (pixId == null) return null;
        try {
            return new PixDAO().findById(pixId).map(pix -> new PixPlainDTO(pix)).orElse(null);
        } catch (FetchFailException e) {
            return null;
        }
    }

    public DadoPlainDTO getDado() {
        if (dado != null) return dado;
        if (dadoId != null) {
            try {
                dado = new DadoDAO().findById(dadoId).map(dado -> new DadoPlainDTO(dado)).orElse(null);
            } catch (FetchFailException e) {
                dado = null;
            }
        }
        return dado;
    }

    public void setValor(double valor) { this.valor = valor; }

    @Override
    public Duplicata fromDTO() { return new Duplicata(this); }

    @Override
    public String toString() {
        return "DuplicataDTO{id=" + id + ", numDup=" + numDup + ", tipoPagamento=" + tipoPagamento + ", dataVencimento="
                + dataVencimento + ", nomeContato=" + nomeContato + ", valor=" + valor + ", paga=" + paga + ", conta="
                + conta + ", fluxoCaixa=" + fluxoCaixa + "}";
    }

}
