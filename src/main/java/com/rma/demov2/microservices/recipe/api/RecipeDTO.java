package com.rma.demov2.microservices.recipe.api;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.rma.demov2.microservices.ingredient.api.IngredientDTO;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@Builder
public class RecipeDTO {
	private Long id;

	private String name;
	private String description;
	private String instruction;

	private List<IngredientDTO> ingredients = new ArrayList<IngredientDTO>();

	@Temporal(TemporalType.TIMESTAMP)
	private Calendar createdOn;

	@Temporal(TemporalType.TIMESTAMP)
	private Calendar updatedOn;

	public RecipeDTO() {
	}

}