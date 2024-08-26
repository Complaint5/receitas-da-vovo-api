package com.receitas_da_vovo.domain.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Classe reponsável pelo request dos campos para a atualização do usuario
 */
public record UpdateUserRequest(
    @NotBlank(message = "O campo name não pode ser vazio") String name,
    @NotBlank(message = "O campo email não pode ser vazio") @Email(message = "O email não pode ser invalido") String email) {
    
}
