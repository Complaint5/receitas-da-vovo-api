package com.receitas_da_vovo.domain.comment;

import jakarta.validation.constraints.NotBlank;

/**
 * Classe reponsável pelo request dos campos para a atualização do comentário
 */
public record UpdateCommentRequest(@NotBlank(message = "O campo body não pode ser vazio") String body) {
    
}
