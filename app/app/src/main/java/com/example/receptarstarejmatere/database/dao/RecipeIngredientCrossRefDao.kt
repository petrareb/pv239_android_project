package com.example.receptarstarejmatere.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.receptarstarejmatere.database.model.IngredientWithRecipes
import com.example.receptarstarejmatere.database.model.RecipeIngredientCrossRef
import com.example.receptarstarejmatere.database.model.RecipeWithIngredients

@Dao
interface RecipeIngredientCrossRefDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(join: List<RecipeIngredientCrossRef>)

    @Transaction
    @Query("select *, `rowid` from recipe")
    fun getRecipesWithIngredients() : LiveData<List<RecipeWithIngredients>>

    @Transaction
    @Query("select *, `rowid` from ingredient")
    fun getIngredientsWithRecipes() : LiveData<List<IngredientWithRecipes>>

    @Transaction
    @Query("select *, `rowid` from recipe where rowid = :givenRecipe")
    fun getIngredientsForRecipe(givenRecipe: Int) : LiveData<RecipeWithIngredients> // ?????
}