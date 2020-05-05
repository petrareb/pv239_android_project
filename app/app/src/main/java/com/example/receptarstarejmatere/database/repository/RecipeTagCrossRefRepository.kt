package com.example.receptarstarejmatere.database.repository

import androidx.lifecycle.LiveData
import com.example.receptarstarejmatere.database.MyDb
import com.example.receptarstarejmatere.database.dao.RecipeTagCrossRefDao
import com.example.receptarstarejmatere.database.model.RecipeTagCrossRef
import com.example.receptarstarejmatere.database.model.TagWithRecipes

class RecipeTagCrossRefRepository(recipeDb: MyDb) {

    private val recipeTagDao: RecipeTagCrossRefDao = recipeDb.recipeTagDao()

    fun getTagWithRecipes(tagId: Int): LiveData<List<TagWithRecipes>> {
        return recipeTagDao.getTagByIdWithRecipes(tagId)
    }
    fun insertAll(joins: List<RecipeTagCrossRef>) {
        recipeTagDao.insertAll(joins)
    }

    companion object {
        @Volatile private var instance: RecipeTagCrossRefRepository? = null

        fun getInstance(recipeDb: MyDb) =
            instance ?: synchronized(this) {
                instance ?: RecipeTagCrossRefRepository(recipeDb).also { instance = it }
            }
    }
}