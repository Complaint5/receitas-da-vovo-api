package com.receitas_da_vovo.dtos;

import java.util.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;

public record UserDto(
    UUID id,
    String name,
    @Email
    String email,
    @Min(value = 8)
    String password
    ) {
        // TODO: javadoc
    
}
