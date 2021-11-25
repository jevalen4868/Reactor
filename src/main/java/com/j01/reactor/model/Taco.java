package com.j01.reactor.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
public class Taco {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 5, message = "Name must be at least 5 characters long")
    private String name;

    private Date createdAt;

    @ManyToMany(targetEntity = Ingredient.class)
    @Size(min = 1, message = "You must choose at least 1 ingredient")
    private List<Ingredient> ingredients;

    public Taco() {
    }

    @PrePersist
    void createdAt() {
        this.createdAt = new Date();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull @Size(min = 5, message = "Name must be at least 5 characters long") String getName() {
        return this.name;
    }

    public void setName(@NotNull @Size(min = 5, message = "Name must be at least 5 characters long") String name) {
        this.name = name;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public @Size(min = 1, message = "You must choose at least 1 ingredient") List<Ingredient> getIngredients() {
        return this.ingredients;
    }

    public void setIngredients(@Size(min = 1, message = "You must choose at least 1 ingredient") List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Taco)) return false;
        final Taco other = (Taco) o;
        if (!other.canEqual(this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$createdAt = this.getCreatedAt();
        final Object other$createdAt = other.getCreatedAt();
        if (this$createdAt == null ? other$createdAt != null : !this$createdAt.equals(other$createdAt)) return false;
        final Object this$ingredients = this.getIngredients();
        final Object other$ingredients = other.getIngredients();
        return this$ingredients == null ? other$ingredients == null : this$ingredients.equals(other$ingredients);
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Taco;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $createdAt = this.getCreatedAt();
        result = result * PRIME + ($createdAt == null ? 43 : $createdAt.hashCode());
        final Object $ingredients = this.getIngredients();
        result = result * PRIME + ($ingredients == null ? 43 : $ingredients.hashCode());
        return result;
    }

    public String toString() {
        return "Taco(id=" + this.getId() + ", name=" + this.getName() + ", createdAt=" + this.getCreatedAt() + ", ingredients=" + this.getIngredients() + ")";
    }
}