package com.example.receptarstarejmatere.database

import com.example.receptarstarejmatere.application.App
import com.example.receptarstarejmatere.database.model.*
import com.example.receptarstarejmatere.database.repository.TagRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.random.Random

class DataGenerator {

    companion object {
        //        fun generateRecipes(): MutableList<Recipe> {
//            val name = listOf("Halusky", "Palacinky", "Lokse", "Nic spesl", "Antena", "Recept", "Recept2")
//            val instructions = listOf("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.")
//            val time = listOf("10", "15", "20", "30","60","90")
//            val temp = listOf("180","200","175","220")
//            var recipes = mutableListOf<Recipe>() // list is by default immutable
//            for (x in 0..20){
//                var recipe = Recipe(
//                    id = x,
//                    name = name[x % name.size],
//                    source =  name[x % name.size],
//                    isFavorite = x % 2 == 0,
//                    preparationTime = time[x % time.size],
//                    cookingTime = time[(x+1) % time.size],
//                    cookingTemperature = temp[x % temp.size],
//                    instructions = instructions[0])
//                recipes.add(recipe)
//            }
//            return recipes
//        }
//
        suspend fun generateTags() {
            val tagNames = listOf(
                "Sladké",
                "Slané",
                "Bez mäsa",
                "Raňajky",
                "Koláče",
                "Vegan",
                "Drinky",
                "Šaláty",
                "Večera",
                "Raw"
            )
            tagNames.forEach { tagName ->
                val tag = Tag(tagName)
                App.tagRepository.insert(tag)
            }
        }

//
//        fun generateRecipesTagsCrossRef(): MutableList<RecipeTagCrossRef> {
//            var joins = mutableListOf<RecipeTagCrossRef>()
//            for(x in 0..20) {
//                var join = RecipeTagCrossRef(tagId = x, recipeId = x)
//                joins.add(join)
//                join = RecipeTagCrossRef(tagId = x, recipeId = Random.nextInt(0, 20))
//                joins.add(join)
//            }
//            return joins
//        }
//
//        fun generateIngredients() : MutableList<Ingredient> {
//            var ingredients = mutableListOf<Ingredient>()
//            val names = listOf("Ingredient1", "Ingredient2", "Ingredient3",
//                "Ingredient4", "Ingredient5", "Ingredient6", "Ingredient7", "Ingredient8", "Ingredient9")
//            for (x in 0..20) {
//                var ingredient = Ingredient( id = x,
//                    name = names[x % names.size])
//                ingredients.add(ingredient)
//            }
//            return ingredients
//        }
//
//        fun generateRecipesIngredientsCrossRef(): MutableList<RecipeIngredient> {
//            var joins = mutableListOf<RecipeIngredient>()
//            val names = listOf("PL", "hrncek", "CL",
//                "kg", "g", "l", "dcl", "ml", "dkg")
//            var id : Int = 0
//            for(x in 0..20) {
//                var join = RecipeIngredient(id = id,
//                    recipeId = x,
//                    ingredientId = x,
//                    measure = names[x % names.size],
//                    quantity = x)
//                joins.add(join)
//                id++
//                join = RecipeIngredient(id = id, recipeId = Random.nextInt(0, 20),
//                    ingredientId = x,
//                    measure = names[x % names.size],
//                    quantity = x-10)
//                joins.add(join)
//                id++
//            }
//            return joins
//        }
    }
}