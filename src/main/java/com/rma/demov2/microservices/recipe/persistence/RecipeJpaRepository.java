package com.rma.demov2.microservices.recipe.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeJpaRepository extends JpaRepository<RecipeEntity, Long> {
}