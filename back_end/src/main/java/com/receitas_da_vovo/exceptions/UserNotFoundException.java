package com.receitas_da_vovo.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(){
        super("Usuario não encontrado.");
    }
    
    public UserNotFoundException(String mensagem){
        super(mensagem);
    }
}