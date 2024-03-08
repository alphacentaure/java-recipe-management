package com.rma.demov2.microservices.ingredient.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientJpaRepository extends JpaRepository<IngredientEntity, Long> {
}