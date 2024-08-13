package com.receitas_da_vovo.domain.user;

import java.util.UUID;

/**
 * Classe reponsável pelo response dos campos da atualização do usuario
 */
public record UpdateUserResponse(UUID id, String name, String email) {

}
