package com.j01.reactor.repo;

import com.j01.reactor.model.Ingredient;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface IngredientRepo extends ReactiveCrudRepository<Ingredient, String> {
}