package com.receitas_da_vovo.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.receitas_da_vovo.domain.recipe.Recipe;

/**
 * Classe responsável pela lógica relacionada as querys da tabela de receitas
 */
@Repository
public interface RecipeRepository extends JpaRepository<Recipe, UUID> {
    /**
     * Método responsável por retornar todas as receitas que estejam ativas no banco
     * de dados
     * 
     * @return retorna um lista do tipo RecipeEntity
     */
    List<Recipe> findAllRecipeByActivatedTrue();

    /**
     * Método responsável por retornar uma receita com base no id e que esteja ativa
     * no banco de dados
     * 
     * @param id recebe um UUID
     * @return retorna um optional de RecipeEntity
     */
    Optional<Recipe> findRecipeByIdAndActivatedTrue(UUID id);

    /**
     * Método responsável por retornar todas as receitas com base no id do criador e
     * que estejam ativas no banco de dados
     * 
     * @param id recebe um UUID
     * @return retorna uma lista do tipo RecipeEntity
     */
    @Query(value = "SELECT * FROM recipes WHERE creator_id = :id AND activated = true", nativeQuery = true)
    List<Recipe> findAllRecipesByUser(@Param("id") UUID id);
}
