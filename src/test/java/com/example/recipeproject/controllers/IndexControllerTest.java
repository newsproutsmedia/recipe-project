package com.example.recipeproject.controllers;

import com.example.recipeproject.domain.Recipe;
import com.example.recipeproject.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class IndexControllerTest {

    @Mock
    RecipeService recipeService;

    @Mock
    Model model;

    IndexController controller;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        // Creates instance of controller that can be used for tests.
        // Since the actual indexcontroller constructor requires that recipeservice be passed into it,
        // this controller must have recipeservice as well.
        controller = new IndexController(recipeService);
    }

    // tests whether going to the root actually bring up the index page
    @Test
    public void testMockMVC() throws Exception {
        // use standaloneSetup vs webappcontextsetup to keep as a Unit test
        // the latter will load the spring context and make everything really slow
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    public void getIndexPage() {

        // given -- add two new recipes to the hashset
        Set<Recipe> recipes = new HashSet<>();
        recipes.add(new Recipe());

        // add a second dummy recipe to the recipes set -- when adding multiple objects you have to explicitly declare the id of the second object in order to create it
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipes.add(recipe);

        // set up that when getRecipes is called, return the recipes hashset
        when(recipeService.getRecipes()).thenReturn(recipes);

        // set up an argument captor to expect a Set of type Recipe when using the Set class
        ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        //when -- set viewName to that of the Mock model
        String viewName = controller.getIndexPage(model);


        //then -- check that the viewName returned by the Mock is equal to "index"
        assertEquals("index", viewName);

        // check that the getRecipes method of recipe service is only called once
        verify(recipeService, times(1)).getRecipes();

        // check that the adding of the mock recipes set to the attribute called "recipes" (eq) happens only once (times)
        verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());

        // get the recipes in the argumentCaptor object and create a new set of Recipe objects
        Set<Recipe> setInController = argumentCaptor.getValue();

        //check that we return a set with two values in it
        assertEquals(2, setInController.size());

    }
}