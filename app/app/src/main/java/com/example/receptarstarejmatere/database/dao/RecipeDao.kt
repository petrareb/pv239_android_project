package com.example.receptarstarejmatere.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.receptarstarejmatere.database.model.Recipe

@Dao
interface RecipeDao {
    @Query("select *, `rowid` from Recipe")
    fun getAll() : LiveData<List<Recipe>>

    @Query("select *, `rowid` from Recipe where rowid in (:recipeIds)")
    fun getRecipesByIds(recipeIds : IntArray) : LiveData<List<Recipe>>

    @Query("select *, `rowid` from Recipe where is_favorite = 1 order by name asc")
    fun getFavoriteRecipes() : LiveData<List<Recipe>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(recipes: List<Recipe>)

    @Update
    fun updateRecipe(recipe : Recipe)

    @Delete
    fun delete(recipe : Recipe)
}
