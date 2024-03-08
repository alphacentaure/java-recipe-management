package com.rma.demov2.microservices.utilities;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.rma.demov2.microservices.ingredient.api.IngredientDTO;
import com.rma.demov2.microservices.ingredient.persistence.IngredientEntity;
import com.rma.demov2.microservices.recipe.api.RecipeDTO;
import com.rma.demov2.microservices.recipe.persistence.RecipeEntity;

@Component
public class GlobalMapper {

    public RecipeEntity mapToRecipeEntity(RecipeDTO recipeDTO) {
        return RecipeEntity.builder()
                .id(recipeDTO.getId())

                .name(recipeDTO.getName())
                .description(recipeDTO.getDescription())
                .instruction(recipeDTO.getInstruction())

                .ingredients(recipeDTO.getIngredients().stream()
                        .collect(Collectors.mapping(rDTO -> this.mapToIngredientEntity(rDTO), Collectors.toList())))

                .build();
    }

    public RecipeDTO mapToRecipeDTO(RecipeEntity recipeEntity) {
        return RecipeDTO.builder()
                .id(recipeEntity.getId())

                .name(recipeEntity.getName())
                .description(recipeEntity.getDescription())
                .instruction(recipeEntity.getInstruction())

                .ingredients(recipeEntity.getIngredients().stream()
                        .collect(Collectors.mapping(rEntity -> this.mapToIngredientDTO(rEntity), Collectors.toList())))

                .createdOn(recipeEntity.getCreatedOn())
                .updatedOn(recipeEntity.getUpdatedOn())
                .build();
    }

    public IngredientEntity mapToIngredientEntity(IngredientDTO ingredientDTO) {
        return IngredientEntity.builder()
                .id(ingredientDTO.getId())

                .name(ingredientDTO.getName())
                .quantity(ingredientDTO.getQuantity())

                .build();
    }

    public IngredientDTO mapToIngredientDTO(IngredientEntity ingredientEntity) {
        return IngredientDTO.builder()
                .id(ingredientEntity.getId())

                .name(ingredientEntity.getName())
                .quantity(ingredientEntity.getQuantity())
                .createdOn(ingredientEntity.getCreatedOn())
                .updatedOn(ingredientEntity.getUpdatedOn())
                .build();
    }
}