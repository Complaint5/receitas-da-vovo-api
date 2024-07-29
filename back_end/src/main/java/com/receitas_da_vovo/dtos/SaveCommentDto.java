package com.receitas_da_vovo.dtos;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Classe reponsável por representa um data transfer object de comentário para o
 * salvamento
 */
public record SaveCommentDto(
                UUID id,
                @NotBlank String body,
                @NotNull RecipeDto recipe,
                @NotNull UserDto owner) {
}
