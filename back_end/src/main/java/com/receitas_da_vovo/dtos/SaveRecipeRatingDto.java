package com.receitas_da_vovo.dtos;

import java.util.UUID;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * Classe reponsável por representa um data transfer object de avalição da
 * receita para o salvamento
 */
public record SaveRecipeRatingDto(
        UUID id,
        @Min(0) @Max(10) double rating,
        @NotNull RecipeDto recipe,
        @NotNull UserDto owner) {
}
