package com.receitas_da_vovo.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.receitas_da_vovo.domain.recipe.RecipeResponse;
import com.receitas_da_vovo.domain.recipe_rating.RecipeRating;
import com.receitas_da_vovo.domain.recipe_rating.RecipeRatingRequest;
import com.receitas_da_vovo.domain.recipe_rating.RecipeRatingResponse;
import com.receitas_da_vovo.domain.recipe.Recipe;
import com.receitas_da_vovo.domain.recipe.RecipeRequest;
import com.receitas_da_vovo.infra.exceptions.RecipeNotFoundException;
import com.receitas_da_vovo.repositories.RecipeRatingRepository;
import com.receitas_da_vovo.repositories.RecipeRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

/**
 * Classe responsálve pela lógica relacionada a Recipe
 */
@Slf4j
@Service
public class RecipeService {
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private RecipeRatingRepository recipeRatingRepository;
    @Autowired
    private UserService userService;

    /**
     * Método Responsálve pela lógica de salvar uma nova receita no banco de dados
     * 
     * @param recipeRequest Recebe um objeto do tipo RecipeRequest
     * @return Retorna um objeto do tipo RecipeResponse
     */
    @Transactional
    public RecipeResponse saveRecipe(RecipeRequest recipeRequest) {

        Recipe recipe = Recipe.builder()
                .title(recipeRequest.title())
                .description(recipeRequest.description())
                .creator(this.userService.findUser(recipeRequest.creatorId()))
                .activated(true)
                .build();

        Recipe recipeCreated = this.recipeRepository.save(recipe);

        log.info("receita {} foi salva no banco de dados.", recipeCreated.getId());

        return new RecipeResponse(recipeCreated.getId(), recipeCreated.getTitle(), recipeCreated.getDescription(),
                this.findRatingAverage(recipeCreated.getId()));
    }

    /**
     * Método responsável pela lógica de atualizar uma receita no banco de dados
     * 
     * @param recipeResponse recebe um objeto do tipo RecipeResponse
     * @param id        recebe um uuid
     * @return Retorna um objeto do tipo RecipeResponse
     */
    @Transactional
    public RecipeResponse updateRecipe(UUID id, RecipeResponse recipeResponse) {
        Recipe recipe = this.findRecipe(id);

        recipe.setTitle(recipeResponse.title());
        recipe.setDescription(recipeResponse.description());

        Recipe recipeUpdated = this.recipeRepository.save(recipe);

        log.info("receita {} foi atualizada no banco de dados.", recipeUpdated.getId());

        return new RecipeResponse(recipeUpdated.getId(), recipeUpdated.getTitle(), recipeUpdated.getDescription(),
                this.findRatingAverage(recipeUpdated.getId()));
    }

    /**
     * Método Responsálve pela lógica de deletar uma receita no banco de dados
     * 
     * @param id recebe um UUID
     * @return retorna um boolean
     */
    @Transactional
    public RecipeResponse deleteRecipe(UUID id) {
        Recipe recipe = this.findRecipe(id);

        recipe.setActivated(false);

        log.info("receita {} foi desativada no banco de dados.", recipe.getId());

        return new RecipeResponse(recipe.getId(), recipe.getTitle(), recipe.getDescription(),
                this.findRatingAverage(recipe.getId()));
    }

    /**
     * Método Responsálve pela lógica de buscar uma receita no banco de dados pelo
     * id
     * 
     * @param id recebe um UUID
     * @return Retorna um objeto do tipo RecipeResponse
     */
    public RecipeResponse findRecipeById(UUID id) {
        Recipe recipe = this.findRecipe(id);

        return new RecipeResponse(recipe.getId(), recipe.getTitle(), recipe.getDescription(),
                this.findRatingAverage(recipe.getId()));
    }

    /**
     * Método Responsálve pela lógica de buscar todas as receitas no banco de dados
     * 
     * @return Retorna uma lista de objetos do tipo RecipeResponse
     */
    public List<RecipeResponse> findAllRecipes() {
        System.out.println();
        return this.recipeRepository.findAllRecipeByActivatedTrue().stream()
                .map(recipe -> new RecipeResponse(recipe.getId(), recipe.getTitle(), recipe.getDescription(),
                        this.findRatingAverage(recipe.getId())))
                .toList();
    }

    /**
     * Método Responsável pela lógica de buscar todas as receita no banco de dados
     * pelo id do usuario
     * 
     * @param id recebe um UUID
     * @return retorna uma lista de objetos do tipo RecipeResponse
     */
    public List<RecipeResponse> findAllRecipesByUser(UUID id) {
        return this.recipeRepository.findAllRecipesByUser(this.userService.findUser(id).getId()).stream()
                .map(recipe -> new RecipeResponse(recipe.getId(), recipe.getTitle(), recipe.getDescription(),
                        this.findRatingAverage(recipe.getId())))
                .toList();
    }

    /**
     * Método privado reponsável pela lógica de buscar uma receita no banco de dados
     * caso o contrario lançar uma exeção
     * 
     * @param id recebe um UUID
     * @return retorna um objeto do tipo Recipe
     * @throws exception Lançara uma exeção caso não encontre a receita
     */
    public Recipe findRecipe(UUID id) throws RuntimeException {
        return this.recipeRepository.findRecipeByIdAndActivatedTrue(id)
                .orElseThrow(() -> new RecipeNotFoundException());
    }

    /**
     * Método responsável pela lógica da salvar uma avaliação de uma receita no
     * banco de dados
     * 
     * @param recipeRatingRequest recebe um objeto do tipo RecipeRatingRequest
     * @return retorna um objeto do tipo RecipeRatingResponse
     */
    @Transactional
    public RecipeRatingResponse saveRating(RecipeRatingRequest recipeRatingRequest) {
        RecipeRating recipeRating = RecipeRating.builder()
                .rating(recipeRatingRequest.rating())
                .recipe(this.findRecipe(recipeRatingRequest.recipeId()))
                .owner(this.userService.findUser(recipeRatingRequest.ownerId()))
                .build();

        RecipeRating savedRecipeRating = this.recipeRatingRepository.save(recipeRating);

        log.info("avaliação {} da receita {} foi salva no banco de dados pelo usuario {}.", savedRecipeRating.getId(),
        recipeRatingRequest.recipeId(), recipeRatingRequest.ownerId());

        return new RecipeRatingResponse(savedRecipeRating.getId(), savedRecipeRating.getRating());
    }

    /**
     * Método responsável pela lógica de atualizar uma avaliação no banco de dados
     * 
     * @param id              recebe um UUID
     * @param recipeRatingResponse recebe um objeto do tipo RecipeRatingResponse
     * @return retorna um objeto do tipo RecipeRatingResponse
     */
    @Transactional
    public RecipeRatingResponse updateRecipeRating(UUID id, RecipeRatingResponse recipeRatingResponse) {
        RecipeRating recipeRating = this.findRating(id);

        recipeRating.setRating(recipeRatingResponse.rating());

        return new RecipeRatingResponse(recipeRating.getId(), recipeRating.getRating());
    }

    /**
     * Método responsável pela lógica de retornar uma avaliação do banco de dados
     * 
     * @param id recebe um UUID
     * @return retorna um objeto do tipo RecipeRatingResponse
     */
    public RecipeRatingResponse findRatingById(UUID id) {
        RecipeRating recipeRating = this.findRating(id);
        return new RecipeRatingResponse(recipeRating.getId(), recipeRating.getRating());
    }

    /**
     * Método responsável pela lógica de buscar uma avaliação no banco de dados
     * 
     * @param id recebe um UUID
     * @return retorna um objeto do tipo RecipeRating
     */
    public RecipeRating findRating(UUID id) {
        return this.recipeRatingRepository.findById(id).orElseThrow(() -> new RuntimeException());
    }

    /**
     * Método responsável pela lógica de buscar uma avaliação com base no id da
     * receita ou retornar 0 caso não exista avaliação
     * 
     * @param id recebe um UUID
     * @return retorna um Double
     */
    private Double findRatingAverage(UUID id) {
        Double rating = this.recipeRatingRepository.rating(id);

        return rating != null ? rating : 0D;
    }
}