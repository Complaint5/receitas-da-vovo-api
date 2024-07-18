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

@Service
public class RecipeService {
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private ClassConverter classConverter;

    /**
     * Método Responsálve pela logica para salvar uma nova receita
     * 
     * @param recipeDto Recebe um RecipeDto
     * @return Retorna um recipeDto com os dados da receita salva no banco de dados
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

        return new RecipeDto(recipeCreated.getId(), recipeCreated.getTitle(), recipeCreated.getDescription(),
                recipeCreated.getRating());
    }

    /**
     * Método Responsálve por adicionar uma nota a lista de notas da receita
     * 
     * @param id     recebe um UUID
     * @param rating recebe um Double
     * @return Retorna um boolean
     */
    @Transactional
    public boolean updateRating(UUID id, Double rating) {
        RecipeEntity recipe = this.findRecipe(id);

        recipe.setRating(rating + recipe.getRating());

        this.recipeRepository.save(recipe);

        return true;
    }

    /**
     * Método responsável pela logica para atualizar a receita no banco de dados
     * 
     * @param recipeDto recebe um recipeDto
     * @param id        recebe um uuid
     * @return Retorna um recipeDto com os dados da receita atualizados no banco de
     *         dados
     */
    @Transactional
    public RecipeDto updateRecipe(UUID id, RecipeDto recipeDto) {
        RecipeEntity recipe = this.findRecipe(id);

        recipe.setDescription(recipeDto.description());

        RecipeEntity recipea = this.recipeRepository.save(recipe);

        return classConverter.recipeEntityToRecipeDto(recipea);
    }

    /**
     * Método Responsálve por setar a variavel activated com false
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
     * Método Responsálve por retornar uma receita
     * 
     * @param id recebe um UUID
     * @return Retorna um DTO de recipe
     */
    public RecipeDto findRecipeById(UUID id) {
        RecipeEntity recipe = this.findRecipe(id);
        return classConverter.recipeEntityToRecipeDto(recipe);
    }

    /**
     * Método Responsálve por retornar uma lista de receitas salvas no banco de
     * dados
     * 
     * @return Retorna uma lista de dtos de receitas
     */
    public List<RecipeDto> findAllRecipes() {
        return this.recipeRepository.findAllRecipeByActivatedTrue().stream()
                .map(recipe -> classConverter.recipeEntityToRecipeDto(recipe))
                .toList();
    }

    /**
     * Método privado usado para retornar uma receita salva no banco de dados
     * 
     * @param id recebe um UUID
     * @return retorna uma entidade de recipe
     * @throws exception Lançara uma exeção caso não encontre a receita /////////////////////////////////////////////
     */
    private RecipeEntity findRecipe(UUID id) throws RuntimeException{
        Optional<RecipeEntity> recipe = this.recipeRepository.findRecipeByIdAndActivatedTrue(id);

        if (recipe.isPresent()) {
            return recipe.get();
        } else {
            throw new RuntimeException("Receita não encontrada.");
        }
    }
}
