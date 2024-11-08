package com.santacarolina.dto;

import java.time.LocalDate;

import com.santacarolina.enums.FluxoCaixa;
import com.santacarolina.enums.TipoDoc;

/**
 * DocumentoPlainDTO
 */
public class DocumentoPlainDTO {

    private long id;
    private Long numDoc;
    private TipoDoc tipoDoc;
    private long emissorId;
    private String caminho;
    private long pastaId;
    private double valor;
    private LocalDate dataEmissao;
    private FluxoCaixa fluxoCaixa;

    public long getId() { return id; }
    public Long getNumDoc() { return numDoc; }
    public TipoDoc getTipoDoc() { return tipoDoc; }
    public long getEmissorId() { return emissorId; }
    public String getCaminho() { return caminho; }
    public long getPastaId() { return pastaId; }
    public double getValor() { return valor; }
    public LocalDate getDataEmissao() { return dataEmissao; }
    public FluxoCaixa getFluxoCaixa() { return fluxoCaixa; }

}
