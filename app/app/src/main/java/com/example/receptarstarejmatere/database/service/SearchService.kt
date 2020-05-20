package com.example.receptarstarejmatere.database.service

import com.example.receptarstarejmatere.database.model.Recipe
import com.example.receptarstarejmatere.database.repository.IngredientRepository
import com.example.receptarstarejmatere.database.repository.RecipeIngredientCrossRefRepository
import com.example.receptarstarejmatere.database.repository.RecipeRepository

class SearchService(
    private val recipeRepository: RecipeRepository,
    private val ingredientRepository: IngredientRepository,
    private val recipeIngredientRepository: RecipeIngredientCrossRefRepository
) {

    suspend fun getRecipesContainingString(string: String): List<Recipe> {
        val results: MutableSet<Recipe> = mutableSetOf()
        val recipesWithNames = recipeRepository.getRecipesWithNameSubstring2(string)
        results.addAll(recipesWithNames)

        val recipesWithInstructions =
            recipeRepository.getRecipesWithInstructionSubstring2(string)
        results.addAll(recipesWithInstructions)

        val ingredients = ingredientRepository.getIngredientIdsByNameSubstring(string)
        val recipeIdsWithIngredients =
            recipeIngredientRepository.getRecipesWithIngredientIds(ingredients)
        val recipesWithIngredient = recipeRepository.getRecipesByIds(recipeIdsWithIngredients)
        results.addAll(recipesWithIngredient)
        return results.distinctBy { item -> item.id }.toList()
    }

    companion object {
        @Volatile
        private var instance: SearchService? = null

        fun getInstance(
            recipeRepository: RecipeRepository,
            ingredientRepository: IngredientRepository,
            recipeIngredientRepository: RecipeIngredientCrossRefRepository
        ) =
            instance ?: synchronized(this) {
                instance ?: SearchService(
                    recipeRepository,
                    ingredientRepository,
                    recipeIngredientRepository
                ).also { instance = it }
            }
    }
}