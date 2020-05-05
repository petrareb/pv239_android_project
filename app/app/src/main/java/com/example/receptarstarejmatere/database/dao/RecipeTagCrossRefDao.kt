package com.example.receptarstarejmatere.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.receptarstarejmatere.database.model.RecipeTagCrossRef
import com.example.receptarstarejmatere.database.model.RecipeWithTags
import com.example.receptarstarejmatere.database.model.TagWithRecipes

@Dao
interface RecipeTagCrossRefDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(join: List<RecipeTagCrossRef>)

    @Transaction
    @Query("select *, `rowid` from recipe")
    fun getRecipesWithTags() : LiveData<List<RecipeWithTags>>

    @Transaction
    @Query("select *, `rowid` from tag")
    fun getTagsWithRecipes() : LiveData<List<TagWithRecipes>>

    @Transaction
    @Query("select *, `rowid` from tag where rowid = :givenTagId")
    fun getTagByIdWithRecipes(givenTagId: Int) : LiveData<List<TagWithRecipes>>
}
