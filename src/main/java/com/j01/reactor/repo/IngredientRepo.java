package com.j01.reactor.repo;

import com.j01.reactor.model.Ingredient;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface IngredientRepo extends ReactiveCrudRepository<Ingredient, String> {
    Mono<Ingredient> findBySlug(String slug);
}