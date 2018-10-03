package com.example.recipeproject.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"recipes"})
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;

    // mappedBy is the name of the object in the related entity that controls the relationship. In this case, the "categories" object in the Recipe entity.
    @ManyToMany(mappedBy = "categories")
    private Set<Recipe> recipes;

}
