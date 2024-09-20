package com.santacarolina.exceptions;

import org.apache.logging.log4j.Logger;

public class FetchFailException extends Exception implements CustomException {

    public FetchFailException(Throwable e, Logger logger) {
        super("ERRO: Não foi possível recuperar os dados no servidor!");
        logger.error(e);
    }

    public String getMessageTitle(){
        return "Falha na Comunicação!";
    }

}
