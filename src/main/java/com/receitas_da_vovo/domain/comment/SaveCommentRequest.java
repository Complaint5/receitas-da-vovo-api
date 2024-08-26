package com.receitas_da_vovo.domain.comment;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Classe reponsável pelo request dos campos para o cadastro do comentário
 */
public record SaveCommentRequest(@NotBlank(message = "O campo body não pode ser vazio") String body, @NotNull(message = "O campo recipeId não pode ser vazio") @JsonProperty("recipe_id") UUID recipeId) {
    
}
