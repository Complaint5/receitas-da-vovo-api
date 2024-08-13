package com.receitas_da_vovo.domain.recipe_rating;

import jakarta.validation.constraints.NotNull;

/**
 * Classe reponsável pelo request dos campos para a atualização da avaliação
 */
public record UpdateRecipeRatingRequest(@NotNull(message = "O campo rating não pode ser vazio") Double rating) {
}
