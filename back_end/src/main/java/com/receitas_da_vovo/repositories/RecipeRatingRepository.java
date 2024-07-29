package com.receitas_da_vovo.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.receitas_da_vovo.entities.RecipeRatingEntity;

/**
 * Classe responsável pela lógica relacionada as querys da tabela de avaliações
 * das receitas
 */
@Repository
public interface RecipeRatingRepository extends JpaRepository<RecipeRatingEntity, UUID> {
    /**
     * Método responsável por retornar uma media de todas as avaliações de uma
     * receita
     * 
     * @param id recebe um UUID
     * @return retorna um Double
     */
    @Query(value = "SELECT SUM(rating) / ( SELECT COUNT(*) FROM recipe_rating WHERE recipe_id = :id) FROM recipe_rating WHERE recipe_id = :id", nativeQuery = true)
    Double rating(UUID id);
}
