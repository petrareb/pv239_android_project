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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(tags: List<Tag>) : List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(tag: Tag) : Long

    @Update
    fun updateTag(tag : Tag)

    @Delete
    fun delete(tag : Tag)
}