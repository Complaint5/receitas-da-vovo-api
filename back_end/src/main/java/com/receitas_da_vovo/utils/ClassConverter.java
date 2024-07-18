package com.receitas_da_vovo.utils;

import org.springframework.stereotype.Component;

import com.receitas_da_vovo.dtos.RecipeDto;
import com.receitas_da_vovo.entities.RecipeEntity;

@Component
public class ClassConverter {
    // TODO: Fazer javadoc
    
    public RecipeEntity recipeDtoToRecipeEntity(RecipeDto recipeDto){
        return new RecipeEntity(recipeDto.id(), recipeDto.title(), recipeDto.description(), recipeDto.rating(), null);
    }

    public RecipeDto recipeEntityToRecipeDto(RecipeEntity recipeEntity){
        return new RecipeDto(recipeEntity.getId(), recipeEntity.getTitle(), recipeEntity.getDescription(), recipeEntity.getRating());
    }
}
