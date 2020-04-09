package com.example.receptarstarejmatere.database.dao

import androidx.room.*
import com.example.receptarstarejmatere.database.model.Recipe
import com.example.receptarstarejmatere.database.model.RecipesWithTags

@Dao
interface RecipeDao {
    @Query("select * from Recipe")
    fun getAll() : List<Recipe>

    @Query("select * from Recipe where rowid in (:recipeIds)")
    fun loadRecipesByIds(recipeIds : IntArray) : List<Recipe>

    @Insert
    fun insertAll(vararg recipes: List<Recipe>)

    @Update
    fun updateRecipe(vararg recipe : Recipe)

    @Delete
    fun delete(recipe : Recipe)
}
