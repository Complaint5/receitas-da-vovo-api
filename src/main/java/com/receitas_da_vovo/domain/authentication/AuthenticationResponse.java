package com.receitas_da_vovo.domain.authentication;

import java.time.Instant;

/**
 * Classe reponsável pelo response dos campos da autenticação do usuario
 */
public record AuthenticationResponse(String accessToken, Instant expiration) {
    
}
