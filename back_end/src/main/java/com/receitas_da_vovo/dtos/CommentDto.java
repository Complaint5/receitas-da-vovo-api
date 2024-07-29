package com.receitas_da_vovo.dtos;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;

/**
 * Classe reponsável por representa um data transfer object de comentário
 */
public record CommentDto(
        UUID id,
        @NotBlank String body) {
}
