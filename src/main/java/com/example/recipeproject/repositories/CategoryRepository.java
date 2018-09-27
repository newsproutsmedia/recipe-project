package com.example.recipeproject.repositories;

import com.example.recipeproject.domain.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    // create custom query w/o SQL by utilizing the database column name w/in the method name.
    // In this case, including "Description" in the method name will search w/in that column and check against the provided String.
    Optional<Category> findByDescription(String description);

}
