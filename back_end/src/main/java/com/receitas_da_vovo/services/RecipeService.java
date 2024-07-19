package com.receitas_da_vovo.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.receitas_da_vovo.dtos.RecipeDto;
import com.receitas_da_vovo.entities.RecipeEntity;
import com.receitas_da_vovo.repositories.RecipeRepository;
import com.receitas_da_vovo.utils.ClassConverter;

import jakarta.transaction.Transactional;

/**
 * Classe responsálve pela logica relacionada a RecipeEntity
 */
@Service
public class RecipeService {
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private ClassConverter classConverter;

    /**
     * Método Responsálve pela logica de salvar uma nova receita no banco de dados
     * 
     * @param recipeDto Recebe um objeto do tipo RecipeDto
     * @return Retorna um objeto do tipo RecipeDto
     */
    @Transactional
    public RecipeDto saveRecipe(RecipeDto recipeDto) {
        RecipeEntity recipe = RecipeEntity.builder()
                .title(recipeDto.title())
                .description(recipeDto.description())
                .rating(0D)
                .activated(true)
                .build();

        RecipeEntity recipeCreated = this.recipeRepository.save(recipe);

        return this.classConverter.recipeEntityToRecipeDto(recipeCreated);
    }

    /**
     * Método Responsálve pela logica de atualizar a nota da receita no banco de
     * dados
     * 
     * @param id     recebe um UUID
     * @param rating recebe um Double
     * @return Retorna um boolean
     */
    @Transactional
    public RecipeDto updateRating(UUID id, Double rating) {
        RecipeEntity recipe = this.findRecipe(id);

        recipe.setRating(rating + recipe.getRating());

        return this.classConverter.recipeEntityToRecipeDto(this.recipeRepository.save(recipe));
    }

    /**
     * Método responsável pela logica de atualizar uma receita no banco de dados
     * 
     * @param recipeDto recebe um objeto do tipo recipeDto
     * @param id        recebe um uuid
     * @return Retorna um objeto do tipo RecipeDto
     */
    @Transactional
    public RecipeDto updateRecipe(UUID id, RecipeDto recipeDto) {
        RecipeEntity recipe = this.findRecipe(id);

        recipe.setDescription(recipeDto.description());

        RecipeEntity recipea = this.recipeRepository.save(recipe);

        return this.classConverter.recipeEntityToRecipeDto(recipea);
    }

    /**
     * Método Responsálve pela logica de deletar uma receita no banco de dados
     * 
     * @param id recebe um uuid
     * @return retorna um boolean
     */
    @Transactional
    public boolean deleteRecipe(UUID id) {
        RecipeEntity recipe = this.findRecipe(id);

        recipe.setActivated(false);

        return true;
    }

    /**
     * Método Responsálve pela logica de buscar uma receita no banco de dados pelo
     * id
     * 
     * @param id recebe um UUID
     * @return Retorna um objeto do tipo RecipeDto
     */
    public RecipeDto findRecipeById(UUID id) {
        RecipeEntity recipe = this.findRecipe(id);
        return this.classConverter.recipeEntityToRecipeDto(recipe);
    }

    /**
     * Método Responsálve pela logica de buscar todas as receitas no banco de dados
     * 
     * @return Retorna uma lista de objetos do tipo RecipeDto
     */
    public List<RecipeDto> findAllRecipes() {
        return this.recipeRepository.findAllRecipeByActivatedTrue().stream()
                .map(recipe -> this.classConverter.recipeEntityToRecipeDto(recipe))
                .toList();
    }

    /**
     * Método privado reponsável pela logica de buscar uma receita no banco de dados
     * caso o contrario lançar uma exeção
     * 
     * @param id recebe um UUID
     * @return retorna um objeto do tipo RecipeEntity
     * @throws exception Lançara uma exeção caso não encontre a receita
     *                   /////////////////////////////////////////////
     */
    private RecipeEntity findRecipe(UUID id) throws RuntimeException {
        Optional<RecipeEntity> recipe = this.recipeRepository.findRecipeByIdAndActivatedTrue(id);

        if (recipe.isPresent()) {
            return recipe.get();
        } else {
            throw new RuntimeException("Receita não encontrada.");
        }
    }
}
