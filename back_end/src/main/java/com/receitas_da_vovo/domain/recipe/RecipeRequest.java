package com.receitas_da_vovo.domain.recipe;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Classe reponsável por representa um data transfer object de receita para o
 * salvamento
 */
public record RecipeRequest(
                UUID id,
                @NotBlank String title,
                @NotBlank String description,
                @NotNull UUID creatorId) {
}
