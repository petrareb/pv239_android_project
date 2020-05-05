package com.example.receptarstarejmatere.database.repository

import androidx.lifecycle.LiveData
import com.example.receptarstarejmatere.database.MyDb
import com.example.receptarstarejmatere.database.dao.TagDao
import com.example.receptarstarejmatere.database.model.Tag

class TagRepository(recipeDb: MyDb) {

    private val tagDao: TagDao = recipeDb.tagDao()

    fun getAllTags(): LiveData<List<Tag>> {
        return tagDao.getAll()
    }

    fun insert(tag: Tag) {
        tagDao.insert(tag)
    }

    fun insertAll(tags: List<Tag>) {
        tagDao.insertAll(tags)
    }

    fun deleteTag(tag: Tag) {
        tagDao.delete(tag)
    }

    companion object {
        @Volatile private var instance: TagRepository? = null

        fun getInstance(recipeDb: MyDb) =
            instance ?: synchronized(this) {
                instance ?: TagRepository(recipeDb).also { instance = it }
            }
    }
}