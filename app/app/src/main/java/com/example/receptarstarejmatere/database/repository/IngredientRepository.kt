package com.example.receptarstarejmatere.database.repository

import com.example.receptarstarejmatere.database.MyDb
import com.example.receptarstarejmatere.database.dao.IngredientDao
import com.example.receptarstarejmatere.database.model.Ingredient

class IngredientRepository(recipeDb: MyDb) {

    private val ingredientDao: IngredientDao = recipeDb.ingredientDao()

    fun insertAll(ingredients: List<Ingredient>) {
        ingredientDao.insertAll(ingredients)
    }

    fun insert(ingredient: Ingredient) {
        ingredientDao.insert(ingredient)
    }

    companion object {
        @Volatile private var instance: IngredientRepository? = null

        fun getInstance(recipeDb: MyDb) =
            instance ?: synchronized(this) {
                instance ?: IngredientRepository(recipeDb).also { instance = it }
            }
    }

}