package com.santacarolina.dto;

import java.time.LocalDate;

public record ProdutoDTO(
        long id,
        long docId,
        long classificacaoId,
        String descricao,
        String und,
        double quantidade,
        double valorUnit,
        LocalDate dataEmissao,
        String nomePasta,
        String nomeContato,
        String classificacao) {
}
