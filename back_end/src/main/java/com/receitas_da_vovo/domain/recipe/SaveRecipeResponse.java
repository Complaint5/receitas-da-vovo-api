package com.receitas_da_vovo.domain.recipe;

import java.util.UUID;

/**
 * Classe reponsável pelo response dos campos da criação da receita
 */
public record SaveRecipeResponse(UUID id, String title, String description, double rating) {
    
}
