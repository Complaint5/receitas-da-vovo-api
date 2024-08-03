package com.receitas_da_vovo.domain.recipe_rating;

import java.util.UUID;

import com.receitas_da_vovo.domain.recipe.Recipe;
import com.receitas_da_vovo.domain.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Classe reponsável por representa a tabela de avaliações das receitas
 */
@Entity
@Table(name = "recipe_rating")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class RecipeRating {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Double rating;
    @ManyToOne
    private Recipe recipe;
    @ManyToOne
    private User owner;
}
