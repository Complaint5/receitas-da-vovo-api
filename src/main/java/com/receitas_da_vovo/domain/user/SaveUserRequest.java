package com.receitas_da_vovo.domain.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Classe reponsável pelo request dos campos para a criação do usuario
 */
public record SaveUserRequest(
        @NotBlank(message = "O campo name não pode ser vazio") String name,
        @NotBlank(message = "O campo email não pode ser vazio") @Email(message = "O email não pode ser invalido") String email,
        @NotBlank(message = "O campo password não pode ser vazio") @Size(min = 8, message = "A senha não pode ser menor do que 8 caracteres") String password) {

}
