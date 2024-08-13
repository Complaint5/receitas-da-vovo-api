package com.receitas_da_vovo.domain.user;

import java.util.UUID;

/**
 * Classe reponsável pelo response dos campos do usuario
 */
public record UserResponse(UUID id, String name, String email) {
    
}
