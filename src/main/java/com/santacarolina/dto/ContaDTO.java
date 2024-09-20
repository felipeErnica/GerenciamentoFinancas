package com.santacarolina.dto;

import com.santacarolina.model.beans.Banco;

public record ContaDTO(
        long id,
        String nomeConta,
        String agencia,
        long bancoId,
        String numeroConta
) {
}
