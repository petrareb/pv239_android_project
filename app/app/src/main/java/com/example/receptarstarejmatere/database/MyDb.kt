package com.example.receptarstarejmatere.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.receptarstarejmatere.database.dao.RecipeDao
import com.example.receptarstarejmatere.database.dao.RecipeTagCrossRefDao
import com.example.receptarstarejmatere.database.dao.TagDao
import com.example.receptarstarejmatere.database.model.Recipe
import com.example.receptarstarejmatere.database.model.RecipeTagCrossRef
import com.example.receptarstarejmatere.database.model.Tag


@Database(entities = [Recipe::class, Tag::class, RecipeTagCrossRef::class], version = 1, exportSchema = false)
abstract class MyDb : RoomDatabase() {

    abstract fun recipeDao() : RecipeDao
    abstract fun tagDao() : TagDao
    abstract fun recipeTagDao() : RecipeTagCrossRefDao

    companion object {
        @Volatile private var instance : MyDb? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context, MyDb::class.java, "receptar.db")
            .build()
    }
}