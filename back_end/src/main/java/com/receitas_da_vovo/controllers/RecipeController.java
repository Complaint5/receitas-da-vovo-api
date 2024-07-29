package com.receitas_da_vovo.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.receitas_da_vovo.dtos.RecipeDto;
import com.receitas_da_vovo.dtos.RecipeRatingDto;
import com.receitas_da_vovo.dtos.SaveRecipeDto;
import com.receitas_da_vovo.dtos.SaveRecipeRatingDto;
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

    // TODO: metodo atualizar rating

    /**
     * Método responsável pelo endpoint de salvar a avaliação da receita
     * 
     * @param saveRecipeRatingDto recebe um objeto do tipo SaveRecipeRatingDto
     * @return retorna um ResponseEntity de RecipeRatingDto com o estatos created
     */
    @PostMapping("/rating")
    public ResponseEntity<RecipeRatingDto> saveRecipeRating(
            @RequestBody @Valid SaveRecipeRatingDto saveRecipeRatingDto) {
        RecipeRatingDto recipe = this.recipeService.saveRating(saveRecipeRatingDto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(recipe)
                .toUri();

        return ResponseEntity.created(uri).body(recipe);
    }

    /**
     * Método responsável pelo endpoint de salvar a receita
     * 
     * @param saveRecipeDto recebe um objeto do tipo SaveRecipeDto
     * @return retorna um ResponseEntity de RecipeDto com o estatos created
     */
    @PostMapping()
    public ResponseEntity<RecipeDto> saveRecipe(@RequestBody @Valid SaveRecipeDto saveRecipeDto) {
        RecipeDto recipe = recipeService.saveRecipe(saveRecipeDto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(recipe.id())
                .toUri();

        return ResponseEntity.created(uri).body(recipe);
    }

    /**
     * Método responsável pelo endpoint de atualizar a receita
     * 
     * @param id        recebe um UUID
     * @param recipeDto recebe um objeto do tipo RecipeDto
     * @return retorna um ResponseEntity de RecipeDto com o estatos ok
     */
    @PutMapping("/{id}")
    public ResponseEntity<RecipeDto> updateRecipe(@PathVariable UUID id, @RequestBody @Valid RecipeDto recipeDto) {
        return ResponseEntity.ok(this.recipeService.updateRecipe(id, recipeDto));
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
     * @return retorna um ResponseEntity de RecipeDto com o estatos ok
     */
    @GetMapping("/{id}")
    public ResponseEntity<RecipeDto> findRecipeById(@PathVariable UUID id) {
        return ResponseEntity.ok(this.recipeService.findRecipeById(id));
    }

    /**
     * Método responsável pelo endpoint de retornar uma avaliação de uma receita com
     * base no id da receita
     * 
     * @param id recebe um UUID
     * @return retorna um ResponseEntity de RecipeRatingDto com o estatos ok
     */
    @GetMapping("/rating/{id}")
    public ResponseEntity<RecipeRatingDto> findRating(@PathVariable UUID id) {
        return ResponseEntity.ok(this.recipeService.rating(id));
    }

    /**
     * Método responsável pelo endpoint de retornar uma lista de receitas pelo id do
     * usuario
     * 
     * @param id recebe um UUID
     * @return retorna um ResponseEntity com uma lista do tipo RecipeDto com o
     *         estatos ok
     */
    @GetMapping("/user/{id}")
    public ResponseEntity<List<RecipeDto>> findAllRecipesByUser(@PathVariable UUID id) {
        return ResponseEntity.ok(this.recipeService.findAllRecipesByUser(id));
    }

    /**
     * Método responsável pelo endpoint de retornar uma lista de todas as receitas
     * 
     * @return retorna um ResponseEntity com uma lista do tipo RecipeDto com o
     *         estatos ok
     */
    @GetMapping()
    public ResponseEntity<List<RecipeDto>> findAllRecipes() {
        return ResponseEntity.ok(this.recipeService.findAllRecipes());
    }
}
