package com.receitas_da_vovo.domain.recipe;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Id;

/**
 * Classe reponsável por representa um data transfer object de receita
 */
public record RecipeResponse(
                @Id UUID id,
                String title,
                String description,
                Double rating) implements Serializable {
}
