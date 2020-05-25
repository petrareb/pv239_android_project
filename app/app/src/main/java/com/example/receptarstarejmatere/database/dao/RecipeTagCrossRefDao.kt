package com.example.receptarstarejmatere.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.receptarstarejmatere.database.model.RecipeTagCrossRef
import com.example.receptarstarejmatere.database.model.RecipeWithTags
import com.example.receptarstarejmatere.database.model.TagWithRecipes

@Dao
interface RecipeTagCrossRefDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(join: List<RecipeTagCrossRef>) : List<Long>

    @Query("delete from RecipeTagCrossRef where recipe_id = :recipeId")
    suspend fun deleteCrossRefByRecipeId(recipeId: Int)

    @Transaction
    @Query("select *, `recipe_id` from recipe")
    fun getRecipesWithTags() : LiveData<List<RecipeWithTags>>

    @Transaction
    @Query("select *, `rowid` from tag")
    fun getTagsWithRecipes() : LiveData<List<TagWithRecipes>>

    @Transaction
    @Query("select *, `rowid` from tag where rowid = :givenTagId")
    fun getTagByIdWithRecipes(givenTagId: Int) : LiveData<TagWithRecipes>

    @Transaction
    @Query("select *, recipe_id from recipe where recipe_id = :givenRecipeId")
    fun getTagsForRecipe(givenRecipeId: Int) : LiveData<RecipeWithTags>
}
