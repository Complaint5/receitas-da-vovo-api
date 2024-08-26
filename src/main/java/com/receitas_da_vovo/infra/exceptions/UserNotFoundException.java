package com.receitas_da_vovo.infra.exceptions;

/**
 * Classe responsável por representar a exeção de usuario não encontrado
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(){
        super("Usuario não encontrado.");
    }
    
    public UserNotFoundException(String mensagem){
        super(mensagem);
    }
}