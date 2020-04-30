package com.example.receptarstarejmatere.database

import com.example.receptarstarejmatere.database.model.Recipe
import com.example.receptarstarejmatere.database.model.RecipeTagCrossRef
import com.example.receptarstarejmatere.database.model.Tag
import java.util.*
import kotlin.random.Random

class DataGenerator {

    companion object{
        fun generateRecipes(): MutableList<Recipe> {
            val name = listOf("Halusky", "Palacinky", "Lokse", "Nic spesl", "Antena", "Recept", "Recept2")
            val instructions = listOf("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.")
            val path =  "";
            var recipes = mutableListOf<Recipe>() // list is by default immutable
            for (x in 0..20){
                var recipe = Recipe(id = x,
                    name = name[x % name.size],
                    isFavorite = x % 2 == 0,
                    preparationTime = Random.nextInt(),
                    cookingTime = Random.nextInt(),
                    instructions = instructions[0],
                    pathToImage = path,
                    stars = Random.nextInt(0,4)
                    )
                recipes.add(recipe)
            }
            return recipes
        }

        fun generateTags() : MutableList<Tag> {
            var tags = mutableListOf<Tag>()
            val names = listOf("Lorem", "Ipsum", "is", "simply", "dummy", "text", "of", "the", "printing")
            for (x in 0..20) {
              var tag = Tag(tagId = x,
                  name = names[x % names.size])
                tags.add(tag)
            }
            return tags
        }

        fun generateRecipesTagsCrossRef(): MutableList<RecipeTagCrossRef> {
            var joins = mutableListOf<RecipeTagCrossRef>()
            for(x in 0..20) {
                var join = RecipeTagCrossRef(tagId = x, recipeId = x)
                joins.add(join)
                join = RecipeTagCrossRef(tagId = x, recipeId = Random.nextInt(0, 20))
                joins.add(join)
            }
            return joins
        }
    }
}