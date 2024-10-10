package com.santacarolina.util.OfxTransformer;

import com.santacarolina.model.Extrato;
import com.webcohesion.ofx4j.domain.data.common.Transaction;

import java.time.LocalDate;
import java.time.ZoneId;

public class DefaultTransformer implements OFXTransformer {

    @Override
    public Extrato getExtrato(Transaction transaction) {
        Extrato e = new Extrato();
        LocalDate localDate = transaction.getDatePosted().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        e.setDataTransacao(localDate);
        e.setDescricao(transaction.getMemo());
        e.setValor(transaction.getAmount());
        return e;
    }

}
