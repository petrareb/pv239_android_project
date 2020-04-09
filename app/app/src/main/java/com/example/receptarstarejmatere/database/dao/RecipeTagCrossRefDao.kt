package com.example.receptarstarejmatere.database.dao

import androidx.room.*
import com.example.receptarstarejmatere.database.model.RecipeTagCrossRef
import com.example.receptarstarejmatere.database.model.RecipesWithTags
import com.example.receptarstarejmatere.database.model.TagsWithRecipes

@Dao
interface RecipeTagCrossRefDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg join: List<RecipeTagCrossRef>)

    @Transaction
    @Query("select * from recipe")
    fun getRecipesWithTags() : List<RecipesWithTags>

    @Transaction
    @Query("select * from tag")
    fun getTagsWithRecipes() : List<TagsWithRecipes>
}