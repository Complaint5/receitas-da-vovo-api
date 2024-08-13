package com.receitas_da_vovo.domain.comment;

import java.util.UUID;

/**
 * Classe reponsável pelo responde dos campos da atualização do comentário
 */
public record UpdateCommentResponse(UUID id, String body) {
    
}
