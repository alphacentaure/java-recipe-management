package com.rma.demov2.microservices.ingredient.persistence;

import java.util.Calendar;

import com.rma.demov2.microservices.recipe.persistence.RecipeEntity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ingredients")

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IngredientEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Version
	private int version;

	private String name;
	private String quantity;

	@ManyToOne
	@Transient
	private RecipeEntity recipe = new RecipeEntity();

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createdon")
	private Calendar createdOn;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updatedon")
	private Calendar updatedOn;

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

	public Calendar getCreatedOn() {
		return createdOn;
	}

	public Calendar getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Calendar updatedOn) {
		this.updatedOn = updatedOn;
	}

	public void setRecipe(RecipeEntity recipe) {
		this.recipe = recipe;
	}

	public RecipeEntity getRecipe() {
		return recipe;
	}

	// ======================================
	// = Lifecycle Methods =
	// ======================================

	@PrePersist
	private void setCreatedOn() {
		createdOn = Calendar.getInstance();
	}

}