package com.example.receptarstarejmatere.database.repository

import androidx.lifecycle.LiveData
import com.example.receptarstarejmatere.database.MyDb
import com.example.receptarstarejmatere.database.dao.RecipeIngredientCrossRefDao
import com.example.receptarstarejmatere.database.model.RecipeIngredientCrossRef
import com.example.receptarstarejmatere.database.model.RecipeWithIngredients

class RecipeIngredientCrossRefRepository(recipeDb: MyDb) {

    private val recipeIngredientDao: RecipeIngredientCrossRefDao = recipeDb.recipeIngredientDao()

    fun getIngredientsForRecipe(recipeId: Int): LiveData<RecipeWithIngredients> {
        return recipeIngredientDao.getIngredientsForRecipe(recipeId)
    }

    fun insertAll(joins: List<RecipeIngredientCrossRef>) {
        recipeIngredientDao.insertAll(joins)
    }

    fun insert(recipeWithIngredients: RecipeIngredientCrossRef) {
        recipeIngredientDao.insert(recipeWithIngredients)
    }

    fun getAll(): LiveData<List<RecipeWithIngredients>> {
        return recipeIngredientDao.getRecipesWithIngredients()
    }

    companion object {
        @Volatile private var instance: RecipeIngredientCrossRefRepository? = null

        fun getInstance(recipeDb: MyDb) =
            instance ?: synchronized(this) {
                instance ?: RecipeIngredientCrossRefRepository(recipeDb).also { instance = it }
            }
    }
}