package com.santacarolina.util.OfxTransformer;

import com.santacarolina.model.Extrato;
import com.webcohesion.ofx4j.domain.data.common.Transaction;

import java.time.LocalDate;
import java.time.ZoneId;

public class SicrediTransformer implements OFXTransformer {
    @Override
    public Extrato getExtrato(Transaction transaction) {
        Extrato e = new Extrato();
        LocalDate localDate = transaction.getDatePosted().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        e.setDataTransacao(localDate);
        e.setCategoriaExtrato(transaction.getMemo());
        e.setDescricao(transaction.getReferenceNumber());
        e.setValor(transaction.getAmount());
        return e;
    }
}
