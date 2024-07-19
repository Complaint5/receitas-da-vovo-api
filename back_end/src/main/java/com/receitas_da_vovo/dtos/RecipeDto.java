package com.receitas_da_vovo.dtos;

import java.util.UUID;

public record RecipeDto(
    UUID id,
    String title,
    String description,
    Double rating
    ) {
        // TODO: javadoc

}
