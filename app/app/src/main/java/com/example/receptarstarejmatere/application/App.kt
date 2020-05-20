package com.example.receptarstarejmatere.application

import android.app.Application
import com.example.receptarstarejmatere.database.DataGenerator
import com.example.receptarstarejmatere.database.MyDb
import com.example.receptarstarejmatere.database.repository.*
import com.example.receptarstarejmatere.database.service.SearchService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class App : Application() {
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
        lateinit var searchService: SearchService
            private set
        private lateinit var dataGenerator: DataGenerator
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

        searchService = SearchService.getInstance(
            recipeRepository,
            ingredientRepository,
            recipeIngredientRepository
        )

        dataGenerator = DataGenerator(this)

        // use only in case the DB is empty
        GlobalScope.launch {
            if (isDbEmpty()) {
                insertInitialData()
            }
        }
    }

    private suspend fun isDbEmpty(): Boolean {
        val tagCount = tagRepository.getTagsCount()
        return tagCount == 0
    }

    private suspend fun insertInitialData() {
        dataGenerator.generateTags()
    }
}
