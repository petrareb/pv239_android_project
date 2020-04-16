package com.example.receptarstarejmatere.database.repository

import android.app.Application
import com.example.receptarstarejmatere.database.MyDb
import com.example.receptarstarejmatere.database.dao.RecipeDao
import com.example.receptarstarejmatere.database.model.Recipe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class RecipeRepository(application: Application): CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private var recipeDao: RecipeDao?

    init {
        val db = MyDb.invoke(application)
        recipeDao = db?.recipeDao()
    }

    fun getRecipes(): List<Recipe> = recipeDao?.getAll()?.value.orEmpty()

    fun getFavoriteRecipes() = recipeDao?.getFavoriteRecipes()

    fun insertAll(recipes: List<Recipe>) {
        launch { insertAllRecipesBG(recipes) }
    }

    private suspend fun insertAllRecipesBG(recipes: List<Recipe>) {
        withContext(Dispatchers.IO){
            recipeDao?.insertAll(recipes)
        }
}

//    https://medium.com/@aungkyawmyint_26195/room-database-with-mvvm-and-kotlin-coroutines-android-a1b1b0ef7b84

}