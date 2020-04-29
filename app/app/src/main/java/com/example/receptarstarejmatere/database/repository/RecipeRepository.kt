package com.example.receptarstarejmatere.database.repository

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.receptarstarejmatere.database.MyDb
import com.example.receptarstarejmatere.database.dao.RecipeDao
import com.example.receptarstarejmatere.database.model.Recipe

class RecipeRepository(recipeDb: MyDb) {

    private var recipeDao: RecipeDao = recipeDb.recipeDao()

    fun getRecipes(owner: LifecycleOwner): List<Recipe>? {
        val recipesData = MutableLiveData<List<Recipe>>()
        recipeDao.getAll().observe(
            owner,
            Observer {
                recipes -> recipesData.value = recipes
            }
        )
        return recipesData.value
    }

    fun getFavoriteRecipes(owner: LifecycleOwner): LiveData<List<Recipe>> {
        val favoriteRecipes = MutableLiveData<List<Recipe>>()
        recipeDao.getFavoriteRecipes().observe(
            owner,
            Observer {
                recipes -> favoriteRecipes.value = recipes
            }
        )
        return favoriteRecipes
    }

    fun insertAll(recipes: List<Recipe>) {
        recipeDao.insertAll(recipes)
    }

    companion object {
        @Volatile private var instance: RecipeRepository? = null

        fun getInstance(recipeDb: MyDb) =
            instance ?: synchronized(this) {
                instance ?: RecipeRepository(recipeDb).also { instance = it }
            }
    }
}