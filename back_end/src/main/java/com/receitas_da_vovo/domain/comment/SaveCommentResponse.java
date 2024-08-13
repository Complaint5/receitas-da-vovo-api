package com.receitas_da_vovo.domain.comment;

import java.util.UUID;

/**
 * Classe reponsável pelo response dos campos do cadastro do comentário
 */
public record SaveCommentResponse(UUID id, String body) {
    
}
