package com.rma.demov2.microservices.ingredient.controller;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.rma.demov2.microservices.ingredient.api.*;
import com.rma.demov2.microservices.ingredient.persistence.IngredientEntity;
import com.rma.demov2.microservices.ingredient.persistence.IngredientJpaRepository;
import com.rma.demov2.microservices.utilities.GlobalMapper;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/ingredient")
public class IngredientController implements IngredientService {

	private IngredientJpaRepository ingredientJpaRepository;
	private GlobalMapper mapper;

	@Autowired
	public void setIngredientJpaRepository(
			IngredientJpaRepository ingredientJpaRepository,
			GlobalMapper mapper) {

		this.ingredientJpaRepository = ingredientJpaRepository;
		this.mapper = mapper;
	}

	@Override
	public ResponseEntity<IngredientDTO> createIngredient(@RequestBody IngredientDTO ingredientDTO) {
		if (ingredientDTO != null) {
			IngredientEntity ingredientEntity = mapper.mapToIngredientEntity(ingredientDTO);
			IngredientEntity newIngredientEntity = ingredientJpaRepository.save(ingredientEntity);
			ingredientDTO = mapper.mapToIngredientDTO(newIngredientEntity);
			return new ResponseEntity<IngredientDTO>(ingredientDTO, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<IngredientDTO>(HttpStatus.NOT_ACCEPTABLE);
		}
	}

	@Override
	public ResponseEntity<List<IngredientDTO>> listAllIngredients() {

		List<IngredientEntity> ingredients = ingredientJpaRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));

		if (ingredients.isEmpty()) {
			return new ResponseEntity<List<IngredientDTO>>(HttpStatus.NO_CONTENT);
		} else {

			List<IngredientDTO> result = ingredients.stream()
					.collect(Collectors.mapping(e -> mapper.mapToIngredientDTO(e), Collectors.toList()));

			return new ResponseEntity<List<IngredientDTO>>(
					result, HttpStatus.OK);
		}

	}

	@Override
	public ResponseEntity<IngredientDTO> getIngredientById(@PathVariable("id") final Long id) {
		if (id != null) {
			IngredientEntity ingredientEntity = ingredientJpaRepository.findById(id).get();
			if (ingredientEntity != null) {
				return new ResponseEntity<IngredientDTO>(mapper.mapToIngredientDTO(ingredientEntity), HttpStatus.OK);
			}
			return new ResponseEntity<IngredientDTO>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<IngredientDTO>(HttpStatus.NOT_ACCEPTABLE);
	}

	@Override
	public ResponseEntity<IngredientDTO> updateIngredient(
			@PathVariable("id") final Long id, @RequestBody IngredientDTO ingredientDTO) {

				IngredientEntity ingredientEntity = ingredientJpaRepository.findById(id).get();
		if (ingredientEntity == null) {
			return new ResponseEntity<IngredientDTO>(HttpStatus.NOT_FOUND);
		} else {
			ingredientEntity.setName(ingredientDTO.getName());
			ingredientEntity.setQuantity(ingredientDTO.getQuantity());

			ingredientEntity.setUpdatedOn(Calendar.getInstance());
			ingredientEntity = ingredientJpaRepository.saveAndFlush(ingredientEntity);

			return new ResponseEntity<IngredientDTO>(mapper.mapToIngredientDTO(ingredientEntity), HttpStatus.OK);
		}

	}

	@Override
	public ResponseEntity<IngredientDTO> deleteIngredient(@PathVariable final Long id) {
		if (id != null) {
			Optional<IngredientEntity> currentIngredient = ingredientJpaRepository.findById(id);
			if (currentIngredient.isEmpty()) {
				return new ResponseEntity<IngredientDTO>(HttpStatus.NOT_FOUND);
			} else {
				ingredientJpaRepository.deleteById(id);
				return new ResponseEntity<IngredientDTO>(HttpStatus.OK);
			}
		}
		return new ResponseEntity<IngredientDTO>(HttpStatus.NOT_ACCEPTABLE);
	}

}