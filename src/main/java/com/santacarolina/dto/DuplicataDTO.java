package com.santacarolina.dto;

import com.santacarolina.enums.TipoPagamento;

public record DuplicataDTO(
        Long docId,
        Long dadoId,
        long id,
        int numDup,
        TipoPagamento tipoPagamento,
        String dataVencimento,
        String nomeContato,
        String boletoCaminho,
        double valor,
        boolean paga,
        String conta) {
}
