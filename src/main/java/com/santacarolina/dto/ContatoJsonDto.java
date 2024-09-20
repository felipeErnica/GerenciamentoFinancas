package com.santacarolina.dto;

public record ContatoJsonDto(long id,
                             String nome,
                             String cnpj,
                             String cpf,
                             String ie) {
}
