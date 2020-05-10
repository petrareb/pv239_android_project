package com.example.receptarstarejmatere.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.receptarstarejmatere.database.model.IngredientWithMeasure
import com.example.receptarstarejmatere.database.model.RecipeIngredient

@Dao
interface RecipeIngredientCrossRefDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(join: List<RecipeIngredient>) // inserting

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recipeWithIngredients: RecipeIngredient)

    @Query("select ri.ingredient_id, ri.quantity, ri.measure, i.name from recipes_ingredients as ri inner join ingredient as i on ri.ingredient_id = i.ingredient_id where ri.recipe_id = :recipeId")
    fun getIngredientsForRecipe(recipeId : Int) : LiveData<List<IngredientWithMeasure>>

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