package com.santacarolina.util.OfxTransformer;

import com.santacarolina.exceptions.OFXTransformerException;
import com.santacarolina.model.ContaBancaria;
import com.santacarolina.model.Extrato;
import com.webcohesion.ofx4j.domain.data.MessageSetType;
import com.webcohesion.ofx4j.domain.data.ResponseEnvelope;
import com.webcohesion.ofx4j.domain.data.banking.BankStatementResponse;
import com.webcohesion.ofx4j.domain.data.banking.BankStatementResponseTransaction;
import com.webcohesion.ofx4j.domain.data.banking.BankingResponseMessageSet;
import com.webcohesion.ofx4j.domain.data.common.Transaction;
import com.webcohesion.ofx4j.io.AggregateUnmarshaller;
import com.webcohesion.ofx4j.io.OFXParseException;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OFXTransformerImpl {

    private OFXTransformer transformer;
    private ContaBancaria conta;

    public OFXTransformerImpl(ContaBancaria conta) {
        this.conta = conta;
        if (StringUtils.isBlank(conta.getBanco().getApelidoBanco())) {
            transformer = new DefaultTransformer();
        } else {
            switch (conta.getBanco().getApelidoBanco()) {
                case "BB" -> transformer = new BBTransformer();
                case "SICREDI" -> transformer = new SicrediTransformer();
                default -> transformer = new DefaultTransformer();
            }
        }
    }

    public List<Extrato> getExtratoList(String filePath) throws OFXTransformerException {
        try {
            List<Extrato> list = new ArrayList<>();
            File ofxFile = new File(filePath);
            AggregateUnmarshaller<ResponseEnvelope> unmarshaller = new AggregateUnmarshaller<>(ResponseEnvelope.class);
            FileInputStream inputStream = new FileInputStream(ofxFile);
            ResponseEnvelope envelope = unmarshaller.unmarshal(inputStream);
            BankingResponseMessageSet response = (BankingResponseMessageSet) envelope.getMessageSet(MessageSetType.banking);
            for (BankStatementResponseTransaction statement : response.getStatementResponses()) {
                BankStatementResponse statementResponse = statement.getMessage();
                for (Transaction transaction : statementResponse.getTransactionList().getTransactions()) {
                    Extrato e = transformer.getExtrato(transaction);
                    e.setConta(conta);
                    list.add(e);
                }
            }
            return list;
        } catch (IOException | OFXParseException e) {
            throw new OFXTransformerException();
        }
    }

}
