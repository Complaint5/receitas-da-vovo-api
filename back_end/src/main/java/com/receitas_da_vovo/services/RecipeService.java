package com.receitas_da_vovo.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.receitas_da_vovo.dtos.RecipeDto;
import com.receitas_da_vovo.dtos.RecipeRatingDto;
import com.receitas_da_vovo.dtos.SaveRecipeDto;
import com.receitas_da_vovo.dtos.SaveRecipeRatingDto;
import com.receitas_da_vovo.entities.RecipeEntity;
import com.receitas_da_vovo.entities.RecipeRatingEntity;
import com.receitas_da_vovo.repositories.RecipeRatingRepository;
import com.receitas_da_vovo.repositories.RecipeRepository;

import jakarta.transaction.Transactional;

/**
 * Classe responsálve pela lógica relacionada a RecipeEntity
 */
@Service
public class RecipeService {
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private RecipeRatingRepository recipeRatingRepository;
    @Autowired
    private UserService userService;

    // TODO: Fazer RestControllerAdvice

    /**
     * Método Responsálve pela lógica de salvar uma nova receita no banco de dados
     * 
     * @param SaveRecipeDto Recebe um objeto do tipo SaveRecipeDto
     * @return Retorna um objeto do tipo RecipeDto
     */
    @Transactional
    public RecipeDto saveRecipe(SaveRecipeDto saveRecipeDto) {

        RecipeEntity recipe = RecipeEntity.builder()
                .title(saveRecipeDto.title())
                .description(saveRecipeDto.description())
                .creator(this.userService.findUserEntity(saveRecipeDto.creator().id()))
                .activated(true)
                .build();

        RecipeEntity recipeCreated = this.recipeRepository.save(recipe);

        return new RecipeDto(recipeCreated.getId(), recipeCreated.getTitle(), recipeCreated.getDescription(),
                this.findRating(recipeCreated.getId()));
    }

    /**
     * Método responsável pela lógica de atualizar uma receita no banco de dados
     * 
     * @param recipeDto recebe um objeto do tipo RecipeDto
     * @param id        recebe um uuid
     * @return Retorna um objeto do tipo RecipeDto
     */
    @Transactional
    public RecipeDto updateRecipe(UUID id, RecipeDto recipeDto) {
        RecipeEntity recipe = this.findRecipe(id);

        recipe.setTitle(recipeDto.title());
        recipe.setDescription(recipeDto.description());

        RecipeEntity recipeUpdated = this.recipeRepository.save(recipe);

        return new RecipeDto(recipeUpdated.getId(), recipeUpdated.getTitle(), recipeUpdated.getDescription(),
                this.findRating(recipeUpdated.getId()));
    }

    /**
     * Método Responsálve pela lógica de deletar uma receita no banco de dados
     * 
     * @param id recebe um UUID
     * @return retorna um boolean
     */
    @Transactional
    public RecipeDto deleteRecipe(UUID id) {
        RecipeEntity recipe = this.findRecipe(id);

        recipe.setActivated(false);

        return new RecipeDto(recipe.getId(), recipe.getTitle(), recipe.getDescription(),
                this.findRating(recipe.getId()));
    }

    /**
     * Método Responsálve pela lógica de buscar uma receita no banco de dados pelo
     * id
     * 
     * @param id recebe um UUID
     * @return Retorna um objeto do tipo RecipeDto
     */
    public RecipeDto findRecipeById(UUID id) {
        RecipeEntity recipe = this.findRecipe(id);

        return new RecipeDto(recipe.getId(), recipe.getTitle(), recipe.getDescription(),
                this.findRating(recipe.getId()));
    }

    /**
     * Método Responsálve pela lógica de buscar todas as receitas no banco de dados
     * 
     * @return Retorna uma lista de objetos do tipo RecipeDto
     */
    public List<RecipeDto> findAllRecipes() {
        System.out.println();
        return this.recipeRepository.findAllRecipeByActivatedTrue().stream()
                .map(recipe -> new RecipeDto(recipe.getId(), recipe.getTitle(), recipe.getDescription(),
                        this.findRating(recipe.getId())))
                .toList();
    }

    /**
     * Método Responsável pela lógica de buscar todas as receita no banco de dados
     * pelo id do usuario
     * 
     * @param id recebe um UUID
     * @return retorna uma lista de objetos do tipo RecipeDto
     */
    public List<RecipeDto> findAllRecipesByUser(UUID id) {
        return this.recipeRepository.findAllRecipesByUser(this.userService.findUserEntity(id).getId()).stream()
                .map(recipe -> new RecipeDto(recipe.getId(), recipe.getTitle(), recipe.getDescription(),
                        this.findRating(recipe.getId())))
                .toList();
    }

    /**
     * Método privado reponsável pela lógica de buscar uma receita no banco de dados
     * caso o contrario lançar uma exeção
     * 
     * @param id recebe um UUID
     * @return retorna um objeto do tipo RecipeEntity
     * @throws exception Lançara uma exeção caso não encontre a receita
     */
    public RecipeEntity findRecipe(UUID id) throws RuntimeException {
        return this.recipeRepository.findRecipeByIdAndActivatedTrue(id).orElseThrow(() -> new RuntimeException("Receita não encontrada."));
    }

    /**
     * Método responsável pela lógica de buscar um avaliação no banco de dados
     * 
     * @param id recebe um UUID
     * @return retorna um objeto do tipo RecipeRatingDto
     */
    public RecipeRatingDto rating(UUID id) {
        return new RecipeRatingDto(this.findRating(id));
    }

    /**
     * Método responsável pela lógica de buscar uma avaliação com base no id da
     * receita ou retornar 0 caso não exista avaliação
     * 
     * @param id recebe um UUID
     * @return retorna um Double
     */
    private Double findRating(UUID id) {
        Double rating = this.recipeRatingRepository.rating(id);

        if (rating != null) {
            return rating;
        } else {
            return 0D;
        }
    }

    /**
     * Método responsável pela lógica da salvar uma avaliação de uma receita no
     * banco de dados
     * 
     * @param saveRecipeRatingDto recebe um objeto do tipo SaveRecipeRatingDto
     * @return retorna um objeto do tipo RecipeRatingDto
     */
    @Transactional
    public RecipeRatingDto saveRating(SaveRecipeRatingDto saveRecipeRatingDto) {
        RecipeRatingEntity recipeRating = RecipeRatingEntity.builder()
                .rating(saveRecipeRatingDto.rating())
                .recipe(this.findRecipe(saveRecipeRatingDto.recipe().id()))
                .owner(this.userService.findUserEntity(saveRecipeRatingDto.owner().id()))
                .build();

        RecipeRatingEntity savedRecipeRating = this.recipeRatingRepository.save(recipeRating);

        return new RecipeRatingDto(savedRecipeRating.getRating());
    }
}