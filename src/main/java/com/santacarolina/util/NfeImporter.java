package com.santacarolina.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.santacarolina.model.beans.DocumentoFiscal;
import com.santacarolina.dao.ContatoDao;
import com.santacarolina.dto.nfe.NfeDTO;
import com.santacarolina.services.NfeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

public class NfeImporter {

    private Logger logger = LogManager.getLogger();
    private ContatoDao contatoDao = new ContatoDao();

    public static DocumentoFiscal openNfe(File fileXML) throws IOException {
        XmlMapper mapper = new XmlMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        NfeDTO nfeDTO = mapper.readValue(fileXML, NfeDTO.class);
        return NfeService.returnDoc(nfeDTO);
    }
}
