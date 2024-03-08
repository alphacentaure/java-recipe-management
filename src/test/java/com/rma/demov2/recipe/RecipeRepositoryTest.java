package com.rma.demov2.recipe;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.rma.demov2.microservices.recipe.persistence.RecipeEntity;
import com.rma.demov2.microservices.recipe.persistence.RecipeJpaRepository;

import java.util.Optional;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
public class RecipeRepositoryTest {

    @Autowired
    private RecipeJpaRepository repository;

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:15-alpine");

    @Test
    public void testReicpeCRUD() {

        // Create a new Data
        RecipeEntity recipe = new RecipeEntity();
        recipe.setName("recipe name");
        recipe.setDescription("lorem impsum Description");
        recipe.setInstruction("lorem impsum Instruction");

        assertNull(recipe.getId());
        assertNull(recipe.getCreatedOn());

        // save Data
        repository.saveAndFlush(recipe);

        // find Data by Id
        Optional<RecipeEntity> result = repository.findById(recipe.getId());
        assertTrue(result.isPresent());

        RecipeEntity dataFromTheGet = result.get();

        Long recipeId = dataFromTheGet.getId();

        assertEquals("recipe name", dataFromTheGet.getName());
        assertEquals("lorem impsum Description", dataFromTheGet.getDescription());

        // update Data
        recipe.setName("recipe name - update");
        recipe.setDescription("lorem impsum Description - update");

        repository.saveAndFlush(recipe);

        // find Data by id
        Optional<RecipeEntity> result2 = repository.findById(recipeId);
        assertTrue(result2.isPresent());

        RecipeEntity dataFromTheGet2 = result2.get();

        assertEquals("recipe name - update", dataFromTheGet2.getName());
        assertEquals("lorem impsum Description - update", dataFromTheGet2.getDescription());

        // delete a book
        repository.delete(recipe);

        // should be empty
        assertTrue(repository.findById(recipeId).isEmpty());

    }

}