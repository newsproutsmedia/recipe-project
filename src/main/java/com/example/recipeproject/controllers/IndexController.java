package com.example.recipeproject.controllers;

import com.example.recipeproject.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class IndexController {

    private final RecipeService recipeService;

    // constructor injection to make recipe service data available for model attributes
    @Autowired
    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"", "/", "/index"})
    public String getIndexPage(Model model) {

        log.debug("##### Accessing Index page and adding recipes model attribute. #####");
        model.addAttribute("recipes", recipeService.getRecipes());

        return "index";
    }
}
