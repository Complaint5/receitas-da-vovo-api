package com.receitas_da_vovo.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.receitas_da_vovo.domain.recipe.RecipeResponse;
import com.receitas_da_vovo.domain.recipe_rating.RecipeRatingRequest;
import com.receitas_da_vovo.domain.recipe_rating.RecipeRatingResponse;
import com.receitas_da_vovo.domain.recipe.RecipeRequest;
import com.receitas_da_vovo.services.RecipeService;

import jakarta.validation.Valid;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
     * @param recipeRatingRequest recebe um objeto do tipo RecipeRatingRequest
     * @return retorna um ResponseEntity de RecipeRatingResponse com o estatos
     *         created
     */
    @PostMapping("/rating")
    public ResponseEntity<RecipeRatingResponse> saveRecipeRating(
            @RequestBody @Valid RecipeRatingRequest recipeRatingRequest) {
        RecipeRatingResponse recipe = this.recipeService.saveRating(recipeRatingRequest);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(recipe)
                .toUri();

        return ResponseEntity.created(uri).body(recipe);
    }

    /**
     * Método responsável pelo endpoint de salvar a receita
     * 
     * @param recipeRequest recebe um objeto do tipo RecipeRequest
     * @return retorna um ResponseEntity de RecipeResponse com o estatos created
     */
    @PostMapping()
    public ResponseEntity<RecipeResponse> saveRecipe(@RequestBody @Valid RecipeRequest recipeRequest) {
        RecipeResponse recipe = recipeService.saveRecipe(recipeRequest);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(recipe.id())
                .toUri();

        return ResponseEntity.created(uri).body(recipe);
    }

    /**
     * Método responsável pelo endpoint de atualizar a receita
     * 
     * @param id             recebe um UUID
     * @param recipeResponse recebe um objeto do tipo RecipeResponse
     * @return retorna um ResponseEntity de RecipeResponse com o estatos ok
     */
    @PutMapping("/{id}")
    public ResponseEntity<RecipeResponse> updateRecipe(@PathVariable UUID id,
            @RequestBody @Valid RecipeResponse recipeResponse) {
        return ResponseEntity.ok(this.recipeService.updateRecipe(id, recipeResponse));
    }

    /**
     * Método responsável pelo endpoint de atualizar a avaliação da receita
     * 
     * @param id                   recebe um UUID
     * @param recipeRatingResponse recebe um objeto do tipo RecipeRatingResponse
     * @return retorna um ResponseEntity de RecipeRatingResponse com o estatos ok
     */
    @PutMapping("/rating/{id}")
    public ResponseEntity<RecipeRatingResponse> updateRecipeRating(@PathVariable UUID id,
            @RequestBody RecipeRatingResponse recipeRatingResponse) {
        return ResponseEntity.ok(this.recipeService.updateRecipeRating(id, recipeRatingResponse));
    }

    /**
     * Método responsável pelo endpoint de deletar a receita
     * 
     * @param id recebe um UUID
     * @return retorna um ResponseEntity com o estatos no content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable UUID id) {
        this.recipeService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Método responsável pelo endpoint de retornar uma receita com base no id
     * 
     * @param id recebe um UUID
     * @return retorna um ResponseEntity de RecipeResponse com o estatos ok
     */
    @GetMapping("/{id}")
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
