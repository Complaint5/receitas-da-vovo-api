package com.receitas_da_vovo.infra.exceptions;

/**
 * Classe responsável por representar a exeção de usuario não autorizado
 */
public class UserForbiddenException extends RuntimeException {
    public UserForbiddenException(){
        super("Usuario não autorizado.");
    }
    
    public UserForbiddenException(String mensagem){
        super(mensagem);
    }
    
}
