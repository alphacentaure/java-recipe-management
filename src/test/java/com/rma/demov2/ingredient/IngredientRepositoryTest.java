package com.rma.demov2.ingredient;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.rma.demov2.microservices.ingredient.persistence.IngredientEntity;
import com.rma.demov2.microservices.ingredient.persistence.IngredientJpaRepository;

import java.util.Optional;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
public class IngredientRepositoryTest {

    @Autowired
    private IngredientJpaRepository repository;

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:15-alpine");

    @Test
    public void testIngredientCRUD() {

        // Create a new Data
        IngredientEntity ingredient = new IngredientEntity();
        ingredient.setName("ingredient name");
        ingredient.setQuantity("1 cup");

        assertNull(ingredient.getId());
        assertNull(ingredient.getCreatedOn());

        // save Data
        repository.saveAndFlush(ingredient);

        // find Data by Id
        Optional<IngredientEntity> result = repository.findById(ingredient.getId());
        assertTrue(result.isPresent());

        IngredientEntity ingredientFromGet = result.get();

        Long ingredientId = ingredientFromGet.getId();

        assertEquals("ingredient name", ingredientFromGet.getName());
        assertEquals("1 cup", ingredientFromGet.getQuantity());

        // update Data
        ingredient.setName("ingredient name - update");
        ingredient.setQuantity("1 cup - update");

        repository.saveAndFlush(ingredient);

        // find Data by id
        Optional<IngredientEntity> result2 = repository.findById(ingredientId);
        assertTrue(result2.isPresent());

        IngredientEntity ingredientFromGet2 = result2.get();

        assertEquals("ingredient name - update", ingredientFromGet2.getName());
        assertEquals("1 cup - update", ingredientFromGet2.getQuantity());

        // delete a book
        repository.delete(ingredient);

        // should be empty
        assertTrue(repository.findById(ingredientId).isEmpty());

    }

}