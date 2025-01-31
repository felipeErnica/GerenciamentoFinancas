package com.santacarolina.exceptions;

public class AuthenticationException extends Throwable implements CustomException {

    @Override
    public String getMessage() {
        return "O nome de usuário ou a senha estão incorretos!";
    }

    @Override
    public String getMessageTitle() {
        return "Falha de Autenticação - Credenciais Incorretas!";
    }
    
}
