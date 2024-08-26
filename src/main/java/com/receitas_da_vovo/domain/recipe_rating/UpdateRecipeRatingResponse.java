package com.receitas_da_vovo.domain.recipe_rating;

import java.util.UUID;

/**
 * Classe reponsável pelo response dos campos da atualização da avaliação
 */
public record UpdateRecipeRatingResponse(UUID id, double rating) {
    
}
