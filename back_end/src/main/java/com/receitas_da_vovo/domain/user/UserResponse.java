package com.receitas_da_vovo.domain.user;

import java.util.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Classe reponsável por representa um data transfer object de usuario
 */
public record UserResponse(
        UUID id,
        @NotBlank String name,
        @Email @NotBlank String email) {
}
