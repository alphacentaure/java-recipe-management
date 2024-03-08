package com.rma.demov2.microservices.recipe.persistence;

import java.util.Calendar;
import java.util.List;

import com.rma.demov2.microservices.ingredient.persistence.IngredientEntity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "recipes")

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecipeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Version
	private int version;

	@Column(name = "recipename")
	private String name;

	@Column(name = "description")
	private String description;
	
	@Column(name = "cookinginstruction")
	private String instruction;

	@OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	private List<IngredientEntity> ingredients;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getInstruction() {
		return instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
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

	public List<IngredientEntity> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<IngredientEntity> ingredients) {
		this.ingredients = ingredients;
	}

	// ======================================
	// = Lifecycle Methods =
	// ======================================

	@PrePersist
	private void setCreatedOn() {
		createdOn = Calendar.getInstance();
	}

}
