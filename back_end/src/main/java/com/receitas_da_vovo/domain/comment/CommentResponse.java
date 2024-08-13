package com.receitas_da_vovo.domain.comment;

import java.util.UUID;

/**
 * Classe reponsável por representa um data transfer object de comentário
 */
public record CommentResponse(
        UUID id,
        String body) {
}
