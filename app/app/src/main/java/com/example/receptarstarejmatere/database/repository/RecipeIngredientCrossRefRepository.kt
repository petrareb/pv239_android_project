package com.example.receptarstarejmatere.database.repository

import androidx.lifecycle.LiveData
import com.example.receptarstarejmatere.database.MyDb
import com.example.receptarstarejmatere.database.dao.RecipeIngredientCrossRefDao
import com.example.receptarstarejmatere.database.model.IngredientWithMeasure
import com.example.receptarstarejmatere.database.model.RecipeIngredient

class RecipeIngredientCrossRefRepository(recipeDb: MyDb) {

    private val recipeIngredientDao: RecipeIngredientCrossRefDao = recipeDb.recipeIngredientDao()

    suspend fun insertAll(joins: List<RecipeIngredient>) : List<Long> {
        return recipeIngredientDao.insertAll(joins)
    }

    suspend fun insert(recipeWithIngredients: RecipeIngredient) : Long {
        return recipeIngredientDao.insert(recipeWithIngredients)
    }

    suspend fun deleteIngredientsByRecipeId(recipeId: Int) {
        recipeIngredientDao.deleteIngredientsByRecipeId(recipeId)
    }

    fun getIngredientsForRecipe(recipeId : Int): LiveData<List<IngredientWithMeasure>> {
        return recipeIngredientDao.getIngredientsForRecipe(recipeId)
    }

    suspend fun getRecipesWithIngredientIds(ingredientIds: List<Int>): List<Int> {
        return recipeIngredientDao.getRecipesWithIngredientIds(ingredientIds)
    }

    companion object {
        @Volatile private var instance: RecipeIngredientCrossRefRepository? = null

        fun getInstance(recipeDb: MyDb) =
            instance ?: synchronized(this) {
                instance ?: RecipeIngredientCrossRefRepository(recipeDb).also { instance = it }
            }
    }
}