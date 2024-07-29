package com.receitas_da_vovo.dtos;

import java.util.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Classe reponsável por representa um data transfer object de usuario
 */
public record UserDto(
        UUID id,
        @NotBlank String name,
        @Email @NotBlank String email) {
}
