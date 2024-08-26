package com.receitas_da_vovo.domain.authentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Classe reponsável pelo request dos campos para a autenticação do usuario
 */
public record AuthenticationRequest(
        @Email(message = "O campo email deve conter um email valido") String email,
        @NotBlank(message = "O campo password não pode ser vazio") String password) {
                // @Size(min = 8, message = "A senha deve ter no minimo 8 caracteres")
}
