package com.santacarolina.exceptions;

public class OFXTransformerException extends Exception implements CustomException {

    @Override
    public String getMessage() { return "Não foi possível importar o OFX!"; }

    @Override
    public String getMessageTitle() { return "Falha na Importação!"; }

}
