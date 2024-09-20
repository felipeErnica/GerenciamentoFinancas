package com.santacarolina.exceptions;

public class MissingFieldException extends Exception implements CustomException {

    public MissingFieldException(String field) {
        super("O campo referente a " + field + "não foi adicionado");
    }

    @Override
    public String getMessageTitle() {
        return "Informação Inválida";
    }

}
