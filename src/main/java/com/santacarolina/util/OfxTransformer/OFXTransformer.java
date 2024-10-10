package com.santacarolina.util.OfxTransformer;

import com.santacarolina.model.Extrato;
import com.webcohesion.ofx4j.domain.data.common.Transaction;

public interface OFXTransformer {
    Extrato getExtrato(Transaction transaction);
}
