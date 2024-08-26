package com.receitas_da_vovo.domain.recipe;

import jakarta.validation.constraints.NotBlank;

/**
 * Classe reponsável pelo request dos campos para a atualização da receita
 */
public record UpdateRecipeRequest(
        @NotBlank(message = "O Campo title não pode ser vazio") String title,
        @NotBlank(message = "O Campo description não pode ser vazio") String description) {
}
