package com.santacarolina.util.OfxTransformer;

import com.santacarolina.model.Extrato;
import com.webcohesion.ofx4j.domain.data.common.Transaction;

import java.time.LocalDate;
import java.time.ZoneId;

public class BBTransformer implements OFXTransformer{

    @Override
    public Extrato getExtrato(Transaction transaction) {
        Extrato e = new Extrato();
        LocalDate localDate = transaction.getDatePosted().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        String[] strings = transaction.getMemo().split("-");
        StringBuffer catBuffer = new StringBuffer(strings[0].trim());
        StringBuffer descBuffer = new StringBuffer();

        if (catBuffer.toString().equals("Pix")) {
            catBuffer.append(" - ").append(strings[1].trim());
            descBuffer.append(strings[2].trim());
            for (int i = 3; i < strings.length; i++) {
                descBuffer.append(" - ").append(strings[i].trim());
            }
        } else {
            if (strings.length > 1) {
                descBuffer.append(strings[1].trim());
                for (int i = 2; i < strings.length; i++) {
                    descBuffer.append(" - ").append(strings[i].trim());
                }
            }
        }

        e.setDataTransacao(localDate);
        e.setCategoriaExtrato(catBuffer.toString());
        e.setDescricao(descBuffer.toString());
        e.setValor(transaction.getAmount());
        return e;
    }

}
