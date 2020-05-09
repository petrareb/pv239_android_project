package com.example.receptarstarejmatere.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
//import com.example.receptarstarejmatere.database.model.RecipeIdWithIngredients
import com.example.receptarstarejmatere.database.model.RecipeIngredientCrossRef

@Dao
interface RecipeIngredientCrossRefDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(join: List<RecipeIngredientCrossRef>) // inserting

//    @Transaction
//    @Query("select *, `rowid` from recipe")
//    fun getRecipesWithIngredients() : LiveData<List<RecipeWithIngredients>>
//
//    @Transaction
//    @Query("select *, `rowid` from ingredient")
//    fun getIngredientsWithRecipes() : LiveData<List<IngredientWithRecipes>>

//    @Transaction
//    @Query("select * from recipeingredientcrossref where recipe_id = :givenRecipe")
//    fun getIngredientsForRecipe(givenRecipe: Int) : LiveData<List<RecipeIdWithIngredients>>
}