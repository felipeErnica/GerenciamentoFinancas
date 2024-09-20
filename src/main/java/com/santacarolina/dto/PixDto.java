package com.santacarolina.dto;

import com.santacarolina.model.beans.Contato;
import com.santacarolina.enums.TipoPix;

public record PixDto (
    long id,
    Long dadoId,
    Contato contato,
    TipoPix tipoPix,
    String chave
){
}