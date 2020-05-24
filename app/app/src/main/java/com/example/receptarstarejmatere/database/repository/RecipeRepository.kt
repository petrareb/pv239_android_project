package com.example.receptarstarejmatere.database.repository

import androidx.lifecycle.LiveData
import com.example.receptarstarejmatere.database.MyDb
import com.example.receptarstarejmatere.database.dao.RecipeDao
import com.example.receptarstarejmatere.database.model.Recipe

class RecipeRepository(recipeDb: MyDb) {

    private val recipeDao: RecipeDao = recipeDb.recipeDao()

    fun getFavoriteRecipes(): LiveData<List<Recipe>> {
        return recipeDao.getFavoriteRecipes()
    }

    suspend fun getRecipesByIds(recipeIds: List<Int>): List<Recipe> {
        return recipeDao.getRecipesByIds(recipeIds)
    }

    fun getRecipeById(id: Int): LiveData<Recipe> {
        return recipeDao.getRecipeById(id)
    }

    suspend fun getRecipesWithNameSubstring2(substring: String): List<Recipe> {
        return recipeDao.getRecipesWithNameSubstring(substring)
    }

    suspend fun getRecipesWithInstructionSubstring2(substring: String): List<Recipe> {
        return recipeDao.getRecipesWithInstructionSubstring(substring)
    }

    suspend fun insertAll(recipes: List<Recipe>) : List<Long> {
        return recipeDao.insertAll(recipes)
    }

    suspend fun insert(recipe: Recipe) : Long {
        return recipeDao.insert(recipe)
    }

    suspend fun updateIsFavorite(id: Int, isFavorite: Boolean) {
        recipeDao.updateIsFavorite(id, isFavorite)
    }

    suspend fun deleteRecipe(recipe: Recipe) {
        recipeDao.delete(recipe)
    }

    suspend fun updateRecipe(recipe: Recipe) {
        recipeDao.updateRecipe(recipe)
    }

    companion object {
        @Volatile private var instance: RecipeRepository? = null

        fun getInstance(recipeDb: MyDb) =
            instance ?: synchronized(this) {
                instance ?: RecipeRepository(recipeDb).also { instance = it }
            }
    }
}