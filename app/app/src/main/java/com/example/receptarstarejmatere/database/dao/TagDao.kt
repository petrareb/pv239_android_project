package com.example.receptarstarejmatere.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.receptarstarejmatere.database.model.AllTagsWithRecipes
import com.example.receptarstarejmatere.database.model.Tag

@Dao
interface TagDao {
    @Query("select *, `rowid` from tag")
    fun getAll() : LiveData<List<Tag>>

    @Query("select *, `rowid` from tag where rowid = :tagId")
    fun getById(tagId : Int) : LiveData<Tag>

    @Query("select count(rowid) from tag")
    suspend fun getTagsCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(tags: List<Tag>) : List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tag: Tag) : Long

    @Update
    fun updateTag(tag : Tag)

    @Delete
    fun delete(tag : Tag)

    @Transaction
    @Query("select t.`rowid`, cr.recipe_id, t.name from tag as t left outer join RecipeTagCrossRef as cr on t.`rowid` = cr.tag_id and cr.recipe_id=:givenRecipeId")
    fun getAllTagsIdsWithRecipeId(givenRecipeId :Int) : LiveData<List<AllTagsWithRecipes>>
}