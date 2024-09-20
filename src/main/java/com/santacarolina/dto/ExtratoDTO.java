package com.santacarolina.dto;

public record ExtratoDTO(
        long id,
        Long contaId,
        String dataTransacao,
        String contaBancaria,
        String categoriaExtrato,
        String descricao,
        double valor,
        boolean conciliado) {
}
