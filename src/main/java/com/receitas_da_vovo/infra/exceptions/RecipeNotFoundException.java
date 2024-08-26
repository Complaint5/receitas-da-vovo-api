package com.receitas_da_vovo.infra.exceptions;

/**
 * Classe responsável por representar a exeção de receita não encontrado
 */
public class RecipeNotFoundException extends RuntimeException {
    public RecipeNotFoundException(){
        super("Receita não encontrado.");
    }
    
    public RecipeNotFoundException(String mensagem){
        super(mensagem);
    }
}