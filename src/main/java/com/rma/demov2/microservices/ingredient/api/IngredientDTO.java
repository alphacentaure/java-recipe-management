package com.rma.demov2.microservices.ingredient.api;

import java.util.Calendar;

import com.rma.demov2.microservices.recipe.api.RecipeDTO;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IngredientDTO {
	private Long id;

	private String name;
	private String quantity;

	private RecipeDTO recipe = new RecipeDTO();

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	@Temporal(TemporalType.TIMESTAMP)
	private Calendar createdOn;

	@Temporal(TemporalType.TIMESTAMP)
	private Calendar updatedOn;
}