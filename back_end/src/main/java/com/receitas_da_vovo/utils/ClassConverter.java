package com.receitas_da_vovo.utils;

import org.springframework.stereotype.Component;

import com.receitas_da_vovo.dtos.RecipeDto;
import com.receitas_da_vovo.dtos.UserDto;
import com.receitas_da_vovo.entities.RecipeEntity;
import com.receitas_da_vovo.entities.UserEntity;

/**
 * Classe utilitaria para converter classes em outra 
 */
@Component
public class ClassConverter {
    
    /**
     * Método responsável por transformar um RecipeDto em um RecipeEntity
     * @param recipeDto recebe um objeto do tipo RecipeDto
     * @return retorna um objeto de RecipeEntity
     */
    public RecipeEntity recipeDtoToRecipeEntity(RecipeDto recipeDto){
        return new RecipeEntity(null, recipeDto.title(), recipeDto.description(), recipeDto.rating(), null);
    }

    /**
     * Método responsável por transformar um RecipeEntity em um RecipeDto
     * @param recipeEntity recebe um objeto do tipo RecipeEntity
     * @return retorna um objeto de RecipeDto
     */
    public RecipeDto recipeEntityToRecipeDto(RecipeEntity recipeEntity){
        return new RecipeDto(recipeEntity.getId(), recipeEntity.getTitle(), recipeEntity.getDescription(), recipeEntity.getRating());
    }

    /**
     * Método responsável por transformar um UserDto em um UserEntity
     * @param userDto recebe um objeto do tipo UserDto
     * @return retorna um objeto de UserEntity
     */
    public UserEntity userDtoToUserEntity(UserDto userDto){
        return new UserEntity(null, userDto.name(), userDto.email(), userDto.password(), null, null);
    }

    /**
     * Método responsável por transformar um UserEntity em um UserDto
     * @param userEntity recebe um objeto do tipo UserEntity
     * @return retorna um objeto de UserDto
     */
    public UserDto userEntityToUserDto (UserEntity userEntity){
        return new UserDto(userEntity.getId(), userEntity.getName(), userEntity.getEmail(), userEntity.getPassword());
    }
}
