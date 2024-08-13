package com.receitas_da_vovo.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import com.receitas_da_vovo.domain.recipe.RecipeResponse;
import com.receitas_da_vovo.domain.recipe.SaveRecipeRequest;
import com.receitas_da_vovo.domain.recipe.SaveRecipeResponse;
import com.receitas_da_vovo.domain.recipe.UpdateRecipeRequest;
import com.receitas_da_vovo.domain.recipe.UpdateRecipeResponse;
import com.receitas_da_vovo.domain.recipe_rating.RecipeRating;
import com.receitas_da_vovo.domain.recipe_rating.RecipeRatingResponse;
import com.receitas_da_vovo.domain.recipe_rating.SaveRecipeRatingRequest;
import com.receitas_da_vovo.domain.recipe_rating.SaveRecipeRatingResponse;
import com.receitas_da_vovo.domain.recipe_rating.UpdateRecipeRatingRequest;
import com.receitas_da_vovo.domain.recipe_rating.UpdateRecipeRatingResponse;
import com.receitas_da_vovo.domain.recipe.Recipe;
import com.receitas_da_vovo.infra.exceptions.RecipeNotFoundException;
import com.receitas_da_vovo.infra.exceptions.UserForbiddenException;
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
     * @param SaveRecipeRequest      Recebe um objeto do tipo SaveRecipeRequest
     * @param JwtAuthenticationToken recebe um objeto do tipo JwtAuthenticationToken
     * @return Retorna um objeto do tipo SaveRecipeResponse
     */
    @Transactional
    public SaveRecipeResponse saveRecipe(SaveRecipeRequest saveRecipeRequest, JwtAuthenticationToken token) {

        Recipe recipe = Recipe.builder()
                .title(saveRecipeRequest.title())
                .description(saveRecipeRequest.description())
                .creator(this.userService.findUser(UUID.fromString(token.getName())))
                .activated(true)
                .build();

        Recipe recipeCreated = this.recipeRepository.save(recipe);

        log.info("receita {} foi salva no banco de dados.", recipeCreated.getId());

        return new SaveRecipeResponse(recipeCreated.getId(), recipeCreated.getTitle(), recipeCreated.getDescription(),
                this.findRatingAverage(recipeCreated.getId()));
    }

    /**
     * Método responsável pela lógica de atualizar uma receita no banco de dados
     * 
     * @param UpdateRecipeRequest    recebe um objeto do tipo UpdateRecipeRequest
     * @param id                     recebe um uuid
     * @param JwtAuthenticationToken recebe um objeto do tipo JwtAuthenticationToken
     * @return Retorna um objeto do tipo UpdateRecipeResponse
     */
    @Transactional
    public UpdateRecipeResponse updateRecipe(UUID id, UpdateRecipeRequest updateRecipeRequest,
            JwtAuthenticationToken token) {
        Recipe recipe = this.findRecipe(id);

        if (!UUID.fromString(token.getName()).equals(recipe.getCreator().getId())) {
            System.out.println(token.getName());
            throw new UserForbiddenException();
        }

        recipe.setTitle(updateRecipeRequest.title());
        recipe.setDescription(updateRecipeRequest.description());

        Recipe recipeUpdated = this.recipeRepository.save(recipe);

        log.info("receita {} foi atualizada no banco de dados.", recipeUpdated.getId());

        return new UpdateRecipeResponse(recipeUpdated.getId(), recipeUpdated.getTitle(), recipeUpdated.getDescription(),
                this.findRatingAverage(recipeUpdated.getId()));
    }

    /**
     * Método Responsálve pela lógica de deletar uma receita no banco de dados
     * 
     * @param id                     recebe um UUID
     * @param JwtAuthenticationToken recebe um objeto do tipo JwtAuthenticationToken
     * @return retorna um boolean
     */
    @Transactional
    public boolean deleteRecipe(UUID id, JwtAuthenticationToken token) {
        Recipe recipe = this.findRecipe(id);

        if (!UUID.fromString(token.getName()).equals(recipe.getCreator().getId())) {
            throw new UserForbiddenException();
        }

        recipe.setActivated(false);

        log.info("receita {} foi desativada no banco de dados.", recipe.getId());

        return true;
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
     * @param SaveRecipeRatingRequest recebe um objeto do tipo
     *                                SaveRecipeRatingRequest
     * @param JwtAuthenticationToken  recebe um objeto do tipo
     *                                JwtAuthenticationToken
     * @return retorna um objeto do tipo SaveRecipeRatingResponse
     */
    @Transactional
    public SaveRecipeRatingResponse saveRating(SaveRecipeRatingRequest saveRecipeRatingRequest,
            JwtAuthenticationToken token) {
        RecipeRating recipeRating = RecipeRating.builder()
                .rating(saveRecipeRatingRequest.rating())
                .recipe(this.findRecipe(saveRecipeRatingRequest.recipeId()))
                .owner(this.userService.findUser(UUID.fromString(token.getName())))
                .build();

        RecipeRating savedRecipeRating = this.recipeRatingRepository.save(recipeRating);

        log.info("avaliação {} da receita {} foi salva no banco de dados.", savedRecipeRating.getId(),
                saveRecipeRatingRequest.recipeId());

        return new SaveRecipeRatingResponse(savedRecipeRating.getId(), savedRecipeRating.getRating());
    }

    /**
     * Método responsável pela lógica de atualizar uma avaliação no banco de dados
     * 
     * @param id                        recebe um UUID
     * @param UpdateRecipeRatingRequest recebe um objeto do tipo
     *                                  UpdateRecipeRatingRequest
     * @param JwtAuthenticationToken    recebe um objeto do tipo
     *                                  JwtAuthenticationToken
     * @return retorna um objeto do tipo UpdateRecipeRatingResponse
     */
    @Transactional
    public UpdateRecipeRatingResponse updateRecipeRating(UUID id, UpdateRecipeRatingRequest updateRecipeRatingRequest,
            JwtAuthenticationToken token) {
        RecipeRating recipeRating = this.findRating(id);

        if (!UUID.fromString(token.getName()).equals(recipeRating.getOwner().getId())) {
            throw new UserForbiddenException();
        }
        
        recipeRating.setRating(updateRecipeRatingRequest.rating());

        log.info("avaliação {} da receita {} foi atualizada no banco de dados.", recipeRating.getId(),
                recipeRating.getRecipe().getId());

        return new UpdateRecipeRatingResponse(recipeRating.getId(), recipeRating.getRating());
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