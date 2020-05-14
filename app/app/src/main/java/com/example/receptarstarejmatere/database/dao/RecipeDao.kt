package com.example.receptarstarejmatere.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.receptarstarejmatere.database.model.Recipe

@Dao
interface RecipeDao {
    @Query("select *, recipe_id from Recipe")
    fun getAll() : LiveData<List<Recipe>>

    @Query("select *, recipe_id from Recipe where recipe_id = :id")
    fun getRecipeById(id: Int): LiveData<Recipe>

    @Query("select *, recipe_id from Recipe where recipe_id in (:recipeIds)")
    fun getRecipesByIds(recipeIds : IntArray) : LiveData<List<Recipe>>

    @Query("select *, recipe_id from Recipe where is_favorite = 1 order by name asc")
    fun getFavoriteRecipes() : LiveData<List<Recipe>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(recipes: List<Recipe>) : List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipe: Recipe) : Long

    @Update
    suspend fun updateRecipe(recipe : Recipe)

    @Delete
    suspend fun delete(recipe : Recipe)

    @Query("delete from Recipe where recipe_id = :id")
    suspend fun deleteById(id: Int)

    @Query("update Recipe set is_favorite = :isFavorite where recipe_id = :id")
    suspend fun updateIsFavorite(id: Int, isFavorite: Boolean)
}
