package com.example.receptarstarejmatere.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.receptarstarejmatere.database.model.Tag

@Dao
interface TagDao {
    @Query("select *, `rowid` from tag")
    fun getAll() : LiveData<List<Tag>>

    @Query("select *, `rowid` from tag where rowid = :tagId")
    fun getById(tagId : Int) : LiveData<Tag>

    @Insert
    fun insertAll(tags: List<Tag>)

    @Update
    fun updateTag(tag : Tag)

    @Delete
    fun delete(tag : Tag)
}