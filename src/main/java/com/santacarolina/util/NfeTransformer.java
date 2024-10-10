package com.santacarolina.util;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.santacarolina.dto.nfe.NFeDTO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.exceptions.NFeException;
import com.santacarolina.exceptions.SaveFailException;
import com.santacarolina.model.DocumentoFiscal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ParameterizedMessageFactory;

import java.io.File;
import java.io.IOException;

public class NfeTransformer {

    private static final Logger logger = LogManager.getLogger(NfeTransformer.class, new ParameterizedMessageFactory());

    public static DocumentoFiscal transformNFe (File nfeFile) throws NFeException, FetchFailException, SaveFailException {
        try {
            XmlMapper mapper = new XmlMapper();
            NFeDTO nFeDTO = mapper.readValue(nfeFile, NFeDTO.class);
            if (nFeDTO.getNFe() == null) throw new NFeException("NFe possui formato não reconhecido!");
            DocumentoFiscal nfeDoc = new DocumentoFiscal();
            nfeDoc.setNumDoc(nFeDTO.getNumDoc());
            nfeDoc.setTipoDoc(nFeDTO.getTipoDoc());
            nfeDoc.setValor(nFeDTO.getValor());
            nfeDoc.setDataEmissao(nFeDTO.getDataEmissao());
            nfeDoc.setEmissor(nFeDTO.getEmissor());
            nfeDoc.setFluxoCaixa(nFeDTO.getFluxoCaixa());
            System.out.println(nfeDoc);
            nFeDTO.getProdutos().forEach(nfeDoc::addProduto);
            nFeDTO.getDuplicatas().forEach(nfeDoc::addDuplicata);
            return nfeDoc;
        } catch (IOException e) {
            logger.error(e);
            throw new NFeException(e.getMessage());
        }
    }
}
