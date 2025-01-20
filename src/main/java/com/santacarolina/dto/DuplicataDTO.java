package com.santacarolina.dto;

import java.time.LocalDate;

import com.santacarolina.enums.FluxoCaixa;
import com.santacarolina.enums.TipoPagamento;
import com.santacarolina.interfaces.FromDTO;
import com.santacarolina.model.Duplicata;

public class DuplicataDTO implements FromDTO<Duplicata> {

    private DocumentoDTO documento;
    private DadoDTO dado;
    private long id;
    private int numDup;
    private TipoPagamento tipoPagamento;
    private LocalDate dataVencimento;
    private String boletoCaminho;
    private double valor;
    private boolean paga;
    private PixDTO pix;
    private FluxoCaixa fluxoCaixa;

    public DuplicataDTO() {}

    public DuplicataDTO (Duplicata d) {
        this.documento = new DocumentoDTO(d.getDocumento());
        this.dado = new DadoDTO(d.getDadoBancario());
        this.id = d.getId();
        this.numDup = d.getNumDup();
        this.tipoPagamento = d.getTipoPagamento();
        this.dataVencimento = d.getDataVencimento();
        this.boletoCaminho = d.getBoletoCaminho();
        this.valor = d.getValor();
        this.paga = d.isPayed();
        this.pix = new PixDTO(d.getPix());
    }

    public DocumentoDTO getDocumento() { return documento; }
    public long getId() { return id; }
    public int getNumDup() { return numDup; }
    public TipoPagamento getTipoPagamento() { return tipoPagamento; }
    public LocalDate getDataVencimento() { return dataVencimento; }
    public String getBoletoCaminho() { return boletoCaminho; }
    public double getValor() { return valor; }
    public boolean isPaga() { return paga; }
    public FluxoCaixa getFluxoCaixa() { return fluxoCaixa; }
    public PixDTO getPix() { return pix; }
    public DadoDTO getDado() { return dado; }

    @Override
    public Duplicata fromDTO() { return new Duplicata(this); }

    @Override
    public String toString() {
        return "DuplicataDTO{id=" + id + ", numDup=" + numDup + ", tipoPagamento=" + tipoPagamento + ", dataVencimento="
                + dataVencimento + ", nomeContato=" + documento.getEmissor().getNome() + ", valor=" + valor + ", paga=" + paga + ", conta="
                + documento.getPasta().getConta() + ", fluxoCaixa=" + fluxoCaixa + "}";
    }

}
