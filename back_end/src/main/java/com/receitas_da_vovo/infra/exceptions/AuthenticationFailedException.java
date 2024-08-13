package com.receitas_da_vovo.infra.exceptions;

/**
 * Classe responsável por representar a exeção de falha ao autenticar
 */
public class AuthenticationFailedException extends RuntimeException {
    public AuthenticationFailedException(){
        super("Falha na autenticação.");
    }
    
    public AuthenticationFailedException(String mensagem){
        super(mensagem);
    }
}
