package com.receitas_da_vovo.exceptions;

public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException(){
        super("Comentário não encontrado.");
    }
    
    public CommentNotFoundException(String mensagem){
        super(mensagem);
    }
}
