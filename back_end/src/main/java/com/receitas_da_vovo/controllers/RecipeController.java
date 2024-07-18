package com.receitas_da_vovo.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.receitas_da_vovo.dtos.RecipeDto;
import com.receitas_da_vovo.services.RecipeService;

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

@RestController
@RequestMapping("/v1/recipe")
public class RecipeController {
    @Autowired
    private RecipeService recipeService;

    @PostMapping()
    public ResponseEntity<RecipeDto> saveRecipe(@RequestBody RecipeDto recipeDto) {
        RecipeDto recipe = recipeService.saveRecipe(recipeDto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(recipe.id())
                .toUri();

        return ResponseEntity.created(uri).body(recipe);
    }

    @PutMapping("/rating/{id}")
    public ResponseEntity<Void> updateRating(@PathVariable UUID id, @RequestBody RecipeDto recipeDto) {
        this.recipeService.updateRating(id, recipeDto.rating());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipeDto> updateRecipe(@PathVariable UUID id, @RequestBody RecipeDto recipeDto) {
        return ResponseEntity.ok(this.recipeService.updateRecipe(id, recipeDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable UUID id) {
        this.recipeService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeDto> findRecipeById(@PathVariable UUID id) {
        return ResponseEntity.ok(this.recipeService.findRecipeById(id));
    }

    @GetMapping()
    public ResponseEntity<List<RecipeDto>> findAllRecipes() {
        return ResponseEntity.ok(this.recipeService.findAllRecipes());
    }
}
