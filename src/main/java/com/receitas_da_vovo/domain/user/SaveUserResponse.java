package com.receitas_da_vovo.domain.user;

import java.util.UUID;

/**
 * Classe reponsável pelo response dos campos da criação do usuario
 */
public record SaveUserResponse(UUID id, String password, String email) {
    
}
