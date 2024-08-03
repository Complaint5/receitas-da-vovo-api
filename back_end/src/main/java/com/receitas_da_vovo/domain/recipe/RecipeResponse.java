package com.receitas_da_vovo.domain.recipe;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

/**
 * Classe reponsável por representa um data transfer object de receita
 */
public record RecipeResponse(
                @Id UUID id,
                @NotBlank String title,
                @NotBlank String description,
                Double rating) implements Serializable {
}
