package com.receitas_da_vovo.domain.recipe_rating;

import java.util.UUID;

/**
 * Classe reponsável por representa um data transfer object de avaliação da
 * receita
 */
public record RecipeRatingResponse(
        UUID id,
        double rating) {
}
