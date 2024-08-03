package com.receitas_da_vovo.domain.comment;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;

/**
 * Classe reponsável por representa um data transfer object de comentário
 */
public record CommentResponse(
        UUID id,
        @NotBlank String body) {
}
