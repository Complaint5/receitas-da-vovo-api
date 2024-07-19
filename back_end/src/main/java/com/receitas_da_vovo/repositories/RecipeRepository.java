package com.receitas_da_vovo.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.receitas_da_vovo.entities.RecipeEntity;

@Repository
public interface RecipeRepository extends JpaRepository<RecipeEntity, UUID>{
    List<RecipeEntity> findAllRecipeByActivatedTrue();
    Optional<RecipeEntity> findRecipeByIdAndActivatedTrue(UUID id);
    // TODO: javadoc
}
