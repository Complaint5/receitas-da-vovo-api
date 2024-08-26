package com.receitas_da_vovo.domain.recipe_rating;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;

/**
 * Classe reponsável pelo request dos campos para a criação da avaliação
 */
public record SaveRecipeRatingRequest(
                @NotNull(message = "O campo rating não pode ser vazio") Double rating,
                @NotNull(message = "O campo recipeId não pode ser vazio") @JsonProperty("recipe_id") UUID recipeId) {

}
