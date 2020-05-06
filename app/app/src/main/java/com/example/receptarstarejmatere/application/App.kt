package com.example.receptarstarejmatere.application

import android.app.Application
import com.example.receptarstarejmatere.database.DataGenerator
import com.example.receptarstarejmatere.database.MyDb
import com.example.receptarstarejmatere.database.repository.RecipeRepository
import com.example.receptarstarejmatere.database.repository.RecipeTagCrossRefRepository
import com.example.receptarstarejmatere.database.repository.TagRepository
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
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        val db = MyDb.invoke(this)
        database = db
        recipeRepository = RecipeRepository.getInstance(database)
        tagRepository = TagRepository.getInstance(database)
        recipeTagRepository = RecipeTagCrossRefRepository.getInstance(database)

        // use only in case the DB is empty
//        insertTestData()
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
    }
}

