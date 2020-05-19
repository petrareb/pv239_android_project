package com.example.receptarstarejmatere.database.repository

import androidx.lifecycle.LiveData
import com.example.receptarstarejmatere.database.MyDb
import com.example.receptarstarejmatere.database.dao.TagDao
import com.example.receptarstarejmatere.database.model.AllTagsWithRecipes
import com.example.receptarstarejmatere.database.model.Tag

class TagRepository(recipeDb: MyDb) {

    private val tagDao: TagDao = recipeDb.tagDao()

    fun getAllTags(): LiveData<List<Tag>> {
        return tagDao.getAll()
    }

    suspend fun insert(tag: Tag) : Long {
        return tagDao.insert(tag)
    }

    suspend fun insertAll(tags: List<Tag>) : List<Long> {
        return tagDao.insertAll(tags)
    }

    suspend fun deleteTag(tag: Tag) {
        tagDao.delete(tag)
    }

    suspend fun getTagsCount(): Int {
        return tagDao.getTagsCount()
    }

    fun getAllTagsWithRecipes(recipeId : Int) : LiveData<List<AllTagsWithRecipes>> {
        return tagDao.getAllTagsIdsWithRecipeId(recipeId)
    }


    companion object {
        @Volatile private var instance: TagRepository? = null

        fun getInstance(recipeDb: MyDb) =
            instance ?: synchronized(this) {
                instance ?: TagRepository(recipeDb).also { instance = it }
            }
    }
}