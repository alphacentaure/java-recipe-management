package com.rma.demov2.microservices.recipe.api;

import java.util.List;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.rma.demov2.microservices.ingredient.api.IngredientDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "${api.recipe-controller-info.name}", description = "${api.recipe-controller-info.description}")
public interface RecipeService {

        @Operation(summary = "${api.recipe-controller-info.create-single-recipe.summary}", description = "${api.recipe-controller-info.create-single-recipe.description}")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "${api.common.responseCodes.created.description}"),
                        @ApiResponse(responseCode = "406", description = "${api.common.responseCodes.notAcceptable.description}")
        })
        @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<RecipeDTO> createRecipe(@RequestBody final RecipeDTO recipeDTO);

        @Operation(summary = "${api.recipe-controller-info.get-all-recipes.summary}", description = "${api.recipe-controller-info.get-all-recipes.description}")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "${api.common.responseCodes.ok.description}"),
                        @ApiResponse(responseCode = "204", description = "${api.common.responseCodes.noContent.description}")
        })
        @GetMapping
        public ResponseEntity<List<RecipeDTO>> listAllRecipes();

        @Operation(summary = "${api.recipe-controller-info.get-single-recipe.summary}", description = "${api.recipe-controller-info.get-single-recipe.description}")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "${api.common.responseCodes.ok.description}"),
                        @ApiResponse(responseCode = "404", description = "${api.common.responseCodes.notFound.description}"),
                        @ApiResponse(responseCode = "406", description = "${api.common.responseCodes.notAcceptable.description}")
        })
        @GetMapping("/{id}")
        public ResponseEntity<RecipeDTO> getRecipeById(@PathVariable("id") final Long id);

        @Operation(summary = "${api.recipe-controller-info.put-single-recipe.summary}", description = "${api.recipe-controller-info.put-single-recipe.description}")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "${api.common.responseCodes.ok.description}"),
                        @ApiResponse(responseCode = "404", description = "${api.common.responseCodes.notFound.description}"),
                        @ApiResponse(responseCode = "406", description = "${api.common.responseCodes.notAcceptable.description}")
        })
        @PutMapping(value = "/{id}")
        public ResponseEntity<RecipeDTO> updateRecipe(
                        @PathVariable("id") final Long id, @RequestBody RecipeDTO recipeDTO);

        @Operation(summary = "${api.recipe-controller-info.delete-single-recipe.summary}", description = "${api.recipe-controller-info.delete-single-recipe.description}")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "${api.common.responseCodes.ok.description}"),
                        @ApiResponse(responseCode = "404", description = "${api.common.responseCodes.notFound.description}"),
                        @ApiResponse(responseCode = "406", description = "${api.common.responseCodes.notAcceptable.description}")
        })
        @DeleteMapping("/{id}")
        public ResponseEntity<RecipeDTO> deleteRecipe(@PathVariable final Long id);

        @Operation(summary = "${api.recipe-controller-info.add-ingredient-to-recipe.summary}", description = "${api.recipe-controller-info.add-recipe-to-recipe.description}")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "202", description = "${api.common.responseCodes.acceptable.description}"),
                        @ApiResponse(responseCode = "406", description = "${api.common.responseCodes.notAcceptable.description}")
        })
        @PutMapping("/{id}/add-ingredient")
        public ResponseEntity<RecipeDTO> addIngredientToRecipe(@RequestBody IngredientDTO ingredientDTO,
                        @PathVariable Long id);

        @Operation(summary = "${api.recipe-controller-info.remove-ingredient-from-recipe.summary}", description = "${api.recipe-controller-info.remove-recipe-from-recipe.description}")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "202", description = "${api.common.responseCodes.acceptable.description}"),
                        @ApiResponse(responseCode = "406", description = "${api.common.responseCodes.notAcceptable.description}")
        })
        @PutMapping("/{id}/remove-ingredient")
        public ResponseEntity<RecipeDTO> removeIngredientToRecipe(@RequestBody IngredientDTO ingredientDTO,
                        @PathVariable Long id);

}
