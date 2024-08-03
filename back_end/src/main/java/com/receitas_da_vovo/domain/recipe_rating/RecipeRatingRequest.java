package com.receitas_da_vovo.domain.recipe_rating;

import java.util.UUID;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * Classe reponsável por representa um data transfer object de avalição da
 * receita para o salvamento
 */
public record RecipeRatingRequest(
        UUID id,
        @Min(0) @Max(10) double rating,
        @NotNull UUID recipeId,
        @NotNull UUID ownerId) {
}
