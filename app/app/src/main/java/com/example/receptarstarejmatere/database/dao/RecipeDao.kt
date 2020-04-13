package com.example.receptarstarejmatere.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.receptarstarejmatere.database.model.Recipe

@Dao
interface RecipeDao {
    @Query("select *, `rowid` from Recipe")
    fun getAll() : List<Recipe> // LiveData<List<Recipe>>

    @Query("select *, `rowid` from Recipe where rowid in (:recipeIds)")
    fun loadRecipesByIds(recipeIds : IntArray) : LiveData<List<Recipe>>

    @Query("select *, `rowid` from Recipe where is_favorite = 1 order by name asc")
    fun loadFavoriteRecipes() : LiveData<List<Recipe>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(recipes: List<Recipe>)

    @Update
    fun updateRecipe(recipe : Recipe)

    @Delete
    fun delete(recipe : Recipe)
}
