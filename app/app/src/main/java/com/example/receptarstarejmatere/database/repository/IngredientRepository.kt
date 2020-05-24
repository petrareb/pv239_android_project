package com.example.receptarstarejmatere.database.repository

import androidx.lifecycle.LiveData
import com.example.receptarstarejmatere.database.MyDb
import com.example.receptarstarejmatere.database.dao.IngredientDao
import com.example.receptarstarejmatere.database.model.Ingredient

class IngredientRepository(recipeDb: MyDb) {

    private val ingredientDao: IngredientDao = recipeDb.ingredientDao()

    suspend fun insertAll(ingredients: List<Ingredient>) : List<Long> {
        return ingredientDao.insertAll(ingredients)
    }

    suspend fun insert(ingredient: Ingredient) : Long {
        return ingredientDao.insert(ingredient)
    }

    suspend fun getByName(ingredName: String): List<Ingredient> {
        return ingredientDao.getByName(ingredName)
    }

    suspend fun getIngredientIdsByNameSubstring(substring: String): List<Int> {
        return ingredientDao.getIngredientIdsByNameSubstring(substring)
    }

    companion object {
        @Volatile private var instance: IngredientRepository? = null

        fun getInstance(recipeDb: MyDb) =
            instance ?: synchronized(this) {
                instance ?: IngredientRepository(recipeDb).also { instance = it }
            }
    }
}