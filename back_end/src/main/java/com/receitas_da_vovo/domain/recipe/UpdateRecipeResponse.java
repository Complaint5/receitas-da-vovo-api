package com.receitas_da_vovo.domain.recipe;

import java.util.UUID;

/**
 * Classe reponsável pelo response dos campos da atualização da receita
 */
public record UpdateRecipeResponse(UUID id, String title, String description, double rating) {
    
}
