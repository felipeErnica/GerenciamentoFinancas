package com.santacarolina.dto;

import com.santacarolina.enums.TipoMovimento;

import java.util.Optional;

public record ConciliacaoDTO(
        long id,
        TipoMovimento tipoMovimento,
        Long  duplicataId,
        long extratoId) {
}
