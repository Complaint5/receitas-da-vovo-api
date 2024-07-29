package com.receitas_da_vovo.dtos;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

/**
 * Classe reponsável por representa um data transfer object de receita
 */
public record RecipeDto(
                @Id UUID id,
                @NotBlank String title,
                @NotBlank String description,
                Double rating) implements Serializable {
}
