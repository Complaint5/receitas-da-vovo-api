package com.receitas_da_vovo.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.receitas_da_vovo.domain.recipe.RecipeResponse;
import com.receitas_da_vovo.domain.recipe.SaveRecipeRequest;
import com.receitas_da_vovo.domain.recipe.SaveRecipeResponse;
import com.receitas_da_vovo.domain.recipe.UpdateRecipeRequest;
import com.receitas_da_vovo.domain.recipe.UpdateRecipeResponse;
import com.receitas_da_vovo.domain.recipe_rating.RecipeRatingResponse;
import com.receitas_da_vovo.domain.recipe_rating.SaveRecipeRatingRequest;
import com.receitas_da_vovo.domain.recipe_rating.SaveRecipeRatingResponse;
import com.receitas_da_vovo.domain.recipe_rating.UpdateRecipeRatingRequest;
import com.receitas_da_vovo.domain.recipe_rating.UpdateRecipeRatingResponse;
import com.receitas_da_vovo.services.RecipeService;

import jakarta.validation.Valid;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Classe reponsável pelos endpoints para receita
 */
@RestController
@RequestMapping("/v1/recipe")
public class RecipeController {
    @Autowired
    private RecipeService recipeService;

    /**
     * Método responsável pelo endpoint de salvar a avaliação da receita
     * 
     * @param SaveRecipeRatingRequest recebe um objeto do tipo
     *                                SaveRecipeRatingRequest
     * @param JwtAuthenticationToken  recebe um objeto do tipo
     *                                JwtAuthenticationToken
     * @return retorna um ResponseEntity de SaveRecipeRatingResponse com o estatos
     *         created
     */
    @PostMapping("/rating")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_STANDART')")
    public ResponseEntity<SaveRecipeRatingResponse> saveRecipeRating(
            @RequestBody @Valid SaveRecipeRatingRequest saveRecipeRatingRequest, JwtAuthenticationToken token) {
        SaveRecipeRatingResponse recipe = this.recipeService.saveRating(saveRecipeRatingRequest, token);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(recipe)
                .toUri();

        return ResponseEntity.created(uri).body(recipe);
    }

    /**
     * Método responsável pelo endpoint de salvar a receita
     * 
     * @param SaveRecipeRequest      recebe um objeto do tipo SaveRecipeRequest
     * @param JwtAuthenticationToken recebe um objeto do tipo JwtAuthenticationToken
     * @return retorna um ResponseEntity de SaveRecipeResponse com o estatos created
     */
    @PostMapping()
    @PreAuthorize("hasAuthority('SCOPE_ROLE_STANDART')")
    public ResponseEntity<SaveRecipeResponse> saveRecipe(@RequestBody @Valid SaveRecipeRequest saveRecipeRequest,
            JwtAuthenticationToken token) {
        SaveRecipeResponse recipe = recipeService.saveRecipe(saveRecipeRequest, token);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(recipe.id())
                .toUri();

        return ResponseEntity.created(uri).body(recipe);
    }

    /**
     * Método responsável pelo endpoint de atualizar a receita
     * 
     * @param id                     recebe um UUID
     * @param UpdateRecipeRequest    recebe um objeto do tipo UpdateRecipeRequest
     * @param JwtAuthenticationToken recebe um objeto do tipo JwtAuthenticationToken
     * @return retorna um ResponseEntity de UpdateRecipeResponse com o estatos ok
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_STANDART')")
    public ResponseEntity<UpdateRecipeResponse> updateRecipe(@PathVariable UUID id,
            @RequestBody @Valid UpdateRecipeRequest updateRecipeRequest, JwtAuthenticationToken token) {
        System.out.println(token.getName());
        return ResponseEntity.ok(this.recipeService.updateRecipe(id, updateRecipeRequest, token));
    }

    /**
     * Método responsável pelo endpoint de atualizar a avaliação da receita
     * 
     * @param id                        recebe um UUID
     * @param UpdateRecipeRatingRequest recebe um objeto do tipo
     *                                  UpdateRecipeRatingRequest
     * @param JwtAuthenticationToken    recebe um objeto do tipo
     *                                  JwtAuthenticationToken
     * @return retorna um ResponseEntity de UpdateRecipeRatingResponse com o estatos
     *         ok
     */
    @PutMapping("/rating/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_STANDART')")
    public ResponseEntity<UpdateRecipeRatingResponse> updateRecipeRating(@PathVariable UUID id,
            @RequestBody UpdateRecipeRatingRequest updateRecipeRatingRequest, JwtAuthenticationToken token) {
        return ResponseEntity.ok(this.recipeService.updateRecipeRating(id, updateRecipeRatingRequest, token));
    }

    /**
     * Método responsável pelo endpoint de deletar a receita
     * 
     * @param id                     recebe um UUID
     * @param JwtAuthenticationToken recebe um objeto do tipo JwtAuthenticationToken
     * @return retorna um ResponseEntity com o estatos no content
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_STANDART')")
    public ResponseEntity<Void> deleteRecipe(@PathVariable UUID id, JwtAuthenticationToken token) {
        this.recipeService.deleteRecipe(id, token);
        return ResponseEntity.noContent().build();
    }

    /**
     * Método responsável pelo endpoint de retornar uma receita com base no id
     * 
     * @param id recebe um UUID
     * @return retorna um ResponseEntity de RecipeResponse com o estatos ok
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_STANDART')")
    public ResponseEntity<RecipeResponse> findRecipeById(@PathVariable UUID id) {
        return ResponseEntity.ok(this.recipeService.findRecipeById(id));
    }

    /**
     * Método responsável pelo endpoint de retornar uma avaliação de uma receita com
     * base no id da receita
     * 
     * @param id recebe um UUID
     * @return retorna um ResponseEntity de RecipeRatingResponse com o estatos ok
     */
    @GetMapping("/rating/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_STANDART')")
    public ResponseEntity<RecipeRatingResponse> findRating(@PathVariable UUID id) {
        return ResponseEntity.ok(this.recipeService.findRatingById(id));
    }

    /**
     * Método responsável pelo endpoint de retornar uma lista de receitas pelo id do
     * usuario
     * 
     * @param id recebe um UUID
     * @return retorna um ResponseEntity com uma lista do tipo RecipeResponse com o
     *         estatos ok
     */
    @GetMapping("/user/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_STANDART')")
    public ResponseEntity<List<RecipeResponse>> findAllRecipesByUser(@PathVariable UUID id) {
        return ResponseEntity.ok(this.recipeService.findAllRecipesByUser(id));
    }

    /**
     * Método responsável pelo endpoint de retornar uma lista de todas as receitas
     * 
     * @return retorna um ResponseEntity com uma lista do tipo RecipeResponse com o
     *         estatos ok
     */
    @GetMapping()
    public ResponseEntity<List<RecipeResponse>> findAllRecipes() {
        return ResponseEntity.ok(this.recipeService.findAllRecipes());
    }
}
