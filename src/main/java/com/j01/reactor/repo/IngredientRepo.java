package com.j01.reactor.repo;

import com.j01.reactor.model.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepo
        extends CrudRepository<Ingredient, String> {

}