package com.receitas_da_vovo.dtos;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Classe reponsável por representa um data transfer object de receita para o
 * salvamento
 */
public record SaveRecipeDto(
                UUID id,
                @NotBlank String title,
                @NotBlank String description,
                @NotNull UserDto creator) {
}
