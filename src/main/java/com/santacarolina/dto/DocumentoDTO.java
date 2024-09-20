package com.santacarolina.dto;

import com.santacarolina.enums.FluxoCaixa;
import com.santacarolina.enums.TipoDoc;
import com.santacarolina.model.beans.Contato;
import com.santacarolina.model.beans.PastaContabil;

public record DocumentoDTO(
        long id,
        String numDoc,
        TipoDoc tipoDoc,
        Long emissorId,
        String caminho,
        Long pastaId,
        double valor,
        String dataEmissao,
        FluxoCaixa fluxoCaixa){
}
