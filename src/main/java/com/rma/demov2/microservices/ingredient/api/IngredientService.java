package com.rma.demov2.microservices.ingredient.api;

import java.util.List;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "${api.ingredient-controller-info.name}", description = "${api.ingredient-controller-info.description}")
public interface IngredientService {

        @Operation(summary = "${api.ingredient-controller-info.create-single-ingredient.summary}", description = "${api.ingredient-controller-info.create-single-ingredient.description}")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "${api.common.responseCodes.created.description}"),
                        @ApiResponse(responseCode = "406", description = "${api.common.responseCodes.notAcceptable.description}")
        })
        @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<IngredientDTO> createIngredient(@RequestBody final IngredientDTO ingredientDTO);

        @Operation(summary = "${api.ingredient-controller-info.get-all-ingredients.summary}", description = "${api.ingredient-controller-info.get-all-ingredients.description}")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "${api.common.responseCodes.ok.description}"),
                        @ApiResponse(responseCode = "204", description = "${api.common.responseCodes.noContent.description}")
        })
        @GetMapping
        public ResponseEntity<List<IngredientDTO>> listAllIngredients();

        @Operation(summary = "${api.ingredient-controller-info.get-single-ingredient.summary}", description = "${api.ingredient-controller-info.get-single-ingredient.description}")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "${api.common.responseCodes.ok.description}"),
                        @ApiResponse(responseCode = "404", description = "${api.common.responseCodes.notFound.description}"),
                        @ApiResponse(responseCode = "406", description = "${api.common.responseCodes.notAcceptable.description}")
        })
        @GetMapping("/{id}")
        public ResponseEntity<IngredientDTO> getIngredientById(@PathVariable("id") final Long id);

        @Operation(summary = "${api.ingredient-controller-info.put-single-ingredient.summary}", description = "${api.ingredient-controller-info.put-single-ingredient.description}")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "${api.common.responseCodes.ok.description}"),
                        @ApiResponse(responseCode = "404", description = "${api.common.responseCodes.notFound.description}"),
                        @ApiResponse(responseCode = "406", description = "${api.common.responseCodes.notAcceptable.description}")
        })
        @PutMapping(value = "/{id}")
        public ResponseEntity<IngredientDTO> updateIngredient(
                        @PathVariable("id") final Long id, @RequestBody IngredientDTO ingredientDTO);

        @Operation(summary = "${api.ingredient-controller-info.delete-single-ingredient.summary}", description = "${api.ingredient-controller-info.delete-single-ingredient.description}")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "${api.common.responseCodes.ok.description}"),
                        @ApiResponse(responseCode = "404", description = "${api.common.responseCodes.notFound.description}"),
                        @ApiResponse(responseCode = "406", description = "${api.common.responseCodes.notAcceptable.description}")
        })
        @DeleteMapping("/{id}")
        public ResponseEntity<IngredientDTO> deleteIngredient(@PathVariable final Long id);

}
