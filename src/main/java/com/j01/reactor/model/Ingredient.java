package com.j01.reactor.model;

import lombok.Data;
import org.springframework.data.annotation.Id;


@Data
public class Ingredient {
    @Id
    private Long id;
    // pseudo id for the reactive db.
    private String slug;
    private String name;
    private Type type;

    public enum Type {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }

}
