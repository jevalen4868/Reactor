package com.j01.reactor.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
public class Taco {

    @Id
    private Long id;

    @NotNull
    @Size(min = 5, message = "Name must be at least 5 characters long")
    private String name;

    private Set<Long> ingIds = new HashSet<>();

    public void addIng(Ingredient ing) {
        ingIds.add(ing.getId());
    }
}