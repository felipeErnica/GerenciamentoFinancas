package com.santacarolina.exceptions;

import org.apache.logging.log4j.Logger;

public class SaveFailException extends Exception implements CustomException{

    public SaveFailException(Throwable e, Logger logger) {
        super("Não foi possível registrar no banco de dados!");
        logger.error(e);
    }

    @Override
    public String getMessageTitle() {
        return "Falha ao Salvar";
    }
}
