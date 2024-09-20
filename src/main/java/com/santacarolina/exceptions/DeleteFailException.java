package com.santacarolina.exceptions;

import org.apache.logging.log4j.Logger;

public class DeleteFailException extends Throwable implements CustomException {
    public DeleteFailException(Throwable e, Logger logger) {
        super("Não foi possível deletar o objeto!");
        logger.error(e);
    }

    @Override
    public String getMessageTitle() {
        return "Falha na Exclusão!";
    }
}
