package com.j01.reactor.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient {
    @Id
    private Long id;
    // pseudo id for the reactive db.
    private String slug;
    private String name;
    private Type type;

    public Ingredient(String slug, String name, Type type) {
        this.slug = slug;
        this.name = name;
        this.type = type;
    }

    public enum Type {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }

}
