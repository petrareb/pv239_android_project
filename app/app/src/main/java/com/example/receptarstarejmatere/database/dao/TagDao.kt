package com.example.receptarstarejmatere.database.dao

import androidx.room.*
import com.example.receptarstarejmatere.database.model.Tag
import com.example.receptarstarejmatere.database.model.TagsWithRecipes

@Dao
interface TagDao {
    @Query("select * from tag")
    fun getAll() : List<Tag>

    @Query("select * from tag where tag_id = :tagId")
    fun getById(tagId : Int) : Tag

    @Insert
    fun insertAll(vararg tags: List<Tag>)

    @Update
    fun updateTag(vararg tag : Tag)

    @Delete
    fun delete(tag : Tag)
}