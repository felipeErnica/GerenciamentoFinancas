package com.santacarolina.dto;

import com.santacarolina.model.beans.Banco;
import com.santacarolina.model.beans.Contato;

public record DadoDto(
        long id,
        String agencia,
        Banco banco,
        String numeroConta,
        Long pixId,
        Contato contato) {
}
