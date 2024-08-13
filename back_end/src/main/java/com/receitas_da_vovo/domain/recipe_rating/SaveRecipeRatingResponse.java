package com.receitas_da_vovo.domain.recipe_rating;

import java.util.UUID;

/**
 * Classe reponsável pelo respoinse dos campos da criação da avaliação
 */
public record SaveRecipeRatingResponse(UUID id, double rating) {
    
}
