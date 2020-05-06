package com.example.receptarstarejmatere.application

import android.app.Application
import com.example.receptarstarejmatere.database.DataGenerator
import com.example.receptarstarejmatere.database.MyDb
import com.example.receptarstarejmatere.database.repository.*
import java.util.*
import kotlin.concurrent.thread

class App: Application() {
    companion object {
        lateinit var instance: App
            private set
        lateinit var database: MyDb
            private set
        lateinit var recipeRepository: RecipeRepository
            private set
        lateinit var tagRepository: TagRepository
            private set
        lateinit var recipeTagRepository: RecipeTagCrossRefRepository
            private set
        lateinit var ingredientRepository: IngredientRepository
            private set
        lateinit var recipeIngredientRepository: RecipeIngredientCrossRefRepository
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        val db = MyDb.invoke(this)
        database = db
        recipeRepository = RecipeRepository.getInstance(database)
        tagRepository = TagRepository.getInstance(database)
        recipeTagRepository = RecipeTagCrossRefRepository.getInstance(database)
        recipeIngredientRepository = RecipeIngredientCrossRefRepository.getInstance(database)
        ingredientRepository = IngredientRepository.getInstance(database)
        // use only in case the DB is empty
        insertTestData()
    }

    private fun insertTestData(){
        val recipes = DataGenerator.generateRecipes()
        thread {
            recipeRepository.insertAll(Collections.unmodifiableList(recipes))
        }
        val tags = DataGenerator.generateTags()
        thread {
            tagRepository.insertAll(Collections.unmodifiableList(tags))
        }
        val recipesWithTags = DataGenerator.generateRecipesTagsCrossRef()
        thread {
            recipeTagRepository.insertAll(Collections.unmodifiableList(recipesWithTags))
        }
        val ingred = DataGenerator.generateIngredients()
        thread {
            ingredientRepository.insertAll(Collections.unmodifiableList(ingred))
        }
        val recipeIngredCrossRef = DataGenerator.generateRecipesIngredientsCrossRef()
        thread {
            recipeIngredientRepository.insertAll(recipeIngredCrossRef)
        }

    }
}

