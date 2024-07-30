package com.receitas_da_vovo.exceptions;

public class RecipeNotFoundException extends RuntimeException {
    public RecipeNotFoundException(){
        super("Receita não encontrado.");
    }
    
    public RecipeNotFoundException(String mensagem){
        super(mensagem);
    }
}