package com.rma.demov2.microservices.recipe.controller;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rma.demov2.microservices.ingredient.api.IngredientDTO;
import com.rma.demov2.microservices.ingredient.persistence.IngredientEntity;
import com.rma.demov2.microservices.recipe.api.RecipeDTO;
import com.rma.demov2.microservices.recipe.api.RecipeService;
import com.rma.demov2.microservices.recipe.persistence.RecipeEntity;
import com.rma.demov2.microservices.recipe.persistence.RecipeJpaRepository;
import com.rma.demov2.microservices.utilities.GlobalMapper;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/recipe")
public class RecipeController implements RecipeService {

	private RecipeJpaRepository recipeJpaRepository;

	private GlobalMapper mapper;

	@Autowired
	public void setCollectionJpaRepository(
			RecipeJpaRepository recipeJpaRepository,
			GlobalMapper mapper) {
		this.recipeJpaRepository = recipeJpaRepository;
		this.mapper = mapper;
	}

	@Override
	public ResponseEntity<RecipeDTO> createRecipe(@RequestBody RecipeDTO recipeDTO) {
		try {
			RecipeEntity recipeEntity = mapper.mapToRecipeEntity(recipeDTO);
			RecipeEntity newRecipenEntity = recipeJpaRepository.save(recipeEntity);
			recipeDTO = mapper.mapToRecipeDTO(newRecipenEntity);

			return new ResponseEntity<RecipeDTO>(recipeDTO, HttpStatus.CREATED);

		} catch (DataIntegrityViolationException dive) {
			return new ResponseEntity<RecipeDTO>(HttpStatus.NOT_ACCEPTABLE);
		}
	}

	@Override
	public ResponseEntity<List<RecipeDTO>> listAllRecipes() {

		List<RecipeEntity> recipes = recipeJpaRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));

		if (recipes.isEmpty()) {
			return new ResponseEntity<List<RecipeDTO>>(HttpStatus.NO_CONTENT);
		} else {

			List<RecipeDTO> result = recipes.stream()
					.collect(Collectors.mapping(c -> mapper.mapToRecipeDTO(c), Collectors.toList()));

			return new ResponseEntity<List<RecipeDTO>>(
					result, HttpStatus.OK);
		}

	}

	@Override
	public ResponseEntity<RecipeDTO> getRecipeById(@PathVariable("id") final Long id) {

		if (id != null) {
			RecipeEntity recipeEntity = recipeJpaRepository.findById(id).get();
			if (recipeEntity != null) {

				return new ResponseEntity<RecipeDTO>(mapper.mapToRecipeDTO(recipeEntity), HttpStatus.OK);
			}
			return new ResponseEntity<RecipeDTO>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<RecipeDTO>(HttpStatus.NOT_ACCEPTABLE);

	}

	@Override
	public ResponseEntity<RecipeDTO> updateRecipe(@PathVariable final Long id,
			@RequestBody RecipeDTO recipeDTO) {

		RecipeEntity currentRecipe = recipeJpaRepository.findById(id).get();

		if (currentRecipe == null) {
			return new ResponseEntity<RecipeDTO>(HttpStatus.NOT_FOUND);
		} else {

			currentRecipe.setName(recipeDTO.getName());
			currentRecipe.setDescription(recipeDTO.getDescription());
			currentRecipe.setInstruction(recipeDTO.getInstruction());
			currentRecipe.setUpdatedOn(Calendar.getInstance());

			currentRecipe = recipeJpaRepository.saveAndFlush(currentRecipe);

			return new ResponseEntity<RecipeDTO>(mapper.mapToRecipeDTO(currentRecipe), HttpStatus.OK);
		}

	}

	@Override
	public ResponseEntity<RecipeDTO> deleteRecipe(@PathVariable final Long id) {
		if (id != null) {
			Optional<RecipeEntity> currentRecipe = recipeJpaRepository.findById(id);
			if (currentRecipe.isEmpty()) {
				return new ResponseEntity<RecipeDTO>(HttpStatus.NOT_FOUND);
			} else {
				recipeJpaRepository.deleteById(id);
				return new ResponseEntity<RecipeDTO>(HttpStatus.OK);
			}
		}
		return new ResponseEntity<RecipeDTO>(HttpStatus.NOT_ACCEPTABLE);

	}

	@Override
	public ResponseEntity<RecipeDTO> addIngredientToRecipe(@RequestBody IngredientDTO ingredientDTO,
			@PathVariable Long id) {
		if (id != null) {

			Optional<RecipeEntity> recipe = recipeJpaRepository.findById(id);
			if (!recipe.isEmpty()) {
				recipe.get().getIngredients().add(mapper.mapToIngredientEntity(ingredientDTO));
				recipe.get().setUpdatedOn(Calendar.getInstance());

				return new ResponseEntity<RecipeDTO>(mapper.mapToRecipeDTO(recipeJpaRepository.save(recipe.get())),
						HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<RecipeDTO>(HttpStatus.NOT_ACCEPTABLE);
			}

		} else {
			return new ResponseEntity<RecipeDTO>(HttpStatus.NOT_ACCEPTABLE);
		}
	}

	@Override
	public ResponseEntity<RecipeDTO> removeIngredientToRecipe(@RequestBody IngredientDTO ingredientDTO,
			@PathVariable Long id) {

		Predicate<IngredientEntity> ingredientIsPresent = x -> {
			return x.getId().equals(ingredientDTO.getId());
		};

		if (id != null) {
			Optional<RecipeEntity> recipe = recipeJpaRepository.findById(id);

			if (!recipe.isEmpty()) {
				recipe.get().getIngredients().removeIf(ingredientIsPresent);
				recipe.get().setUpdatedOn(Calendar.getInstance());
				return new ResponseEntity<RecipeDTO>(mapper.mapToRecipeDTO(recipeJpaRepository.save(recipe.get())),
						HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<RecipeDTO>(HttpStatus.NOT_ACCEPTABLE);
			}
		} else {
			return new ResponseEntity<RecipeDTO>(HttpStatus.NOT_ACCEPTABLE);
		}

	}

}
