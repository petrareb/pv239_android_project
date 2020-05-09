package com.example.receptarstarejmatere.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.receptarstarejmatere.database.model.Recipe

@Dao
interface RecipeDao {
    @Query("select *, recipe_id from Recipe")
    fun getAll() : LiveData<List<Recipe>>

    @Query("select *, recipe_id from Recipe where recipe_id in (:recipeIds)")
    fun getRecipesByIds(recipeIds : IntArray) : LiveData<List<Recipe>>

    @Query("select *, recipe_id from Recipe where is_favorite = 1 order by name asc")
    fun getFavoriteRecipes() : LiveData<List<Recipe>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(recipes: List<Recipe>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recipe: Recipe)

    @Update
    fun updateRecipe(recipe : Recipe)

    @Delete
    fun delete(recipe : Recipe)
}
