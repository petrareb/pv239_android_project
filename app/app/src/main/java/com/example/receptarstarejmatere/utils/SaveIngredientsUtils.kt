package com.example.receptarstarejmatere.utils

import com.example.receptarstarejmatere.application.App
import com.example.receptarstarejmatere.database.model.Ingredient
import com.example.receptarstarejmatere.database.model.RecipeIngredient
import com.example.receptarstarejmatere.database.viewModel.IngredientViewModel
import java.util.*

class SaveIngredientsUtils {
    companion object {
        suspend fun getOrCreateIngredientId(ingred: IngredientViewModel): Int {
            val ingredName = ingred.name.toLowerCase(Locale.ROOT).trim()
            val existingIngredients: List<Ingredient>

            existingIngredients = App.ingredientRepository.getByName(ingredName)

            return if (existingIngredients.isNotEmpty()) {
                existingIngredients.first().id
            } else {
                val newIngredient = Ingredient(
                    name = ingredName
                )
                App.ingredientRepository.insert(newIngredient).toInt()
            }
        }

        suspend fun joinIngredientsToRecipe(
            recipeId: Int,
            ingredientId: Int,
            ingred: IngredientViewModel
        ) {
            val newRecipeWithIngredients = RecipeIngredient(
                ingredientId = ingredientId,
                recipeId = recipeId,
                measure = ingred.measure,
                quantity = ingred.quantity.toInt()
            )

            App.recipeIngredientRepository.insert(newRecipeWithIngredients)
        }
    }
}