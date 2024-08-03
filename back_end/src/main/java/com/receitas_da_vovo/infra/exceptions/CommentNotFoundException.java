package com.receitas_da_vovo.infra.exceptions;

/**
 * Classe responsável por representar a exeção de comentário não encontrado
 */
public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException(){
        super("Comentário não encontrado.");
    }
    
    public CommentNotFoundException(String mensagem){
        super(mensagem);
    }
}
