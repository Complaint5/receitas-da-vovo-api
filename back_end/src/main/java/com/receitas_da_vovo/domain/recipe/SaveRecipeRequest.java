package com.receitas_da_vovo.domain.recipe;

import jakarta.validation.constraints.NotBlank;

/**
 * Classe reponsável pelo request dos campos para a criação da receita
 */
public record SaveRecipeRequest(
        @NotBlank(message = "O campo title não pode ser vazio") String title,
        @NotBlank(message = "O campo descripton não pode ser vazio") String description) {

}
