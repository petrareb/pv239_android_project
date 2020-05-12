package com.example.receptarstarejmatere.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.receptarstarejmatere.database.dao.*
import com.example.receptarstarejmatere.database.model.*


@Database(entities = [Recipe::class, Tag::class, RecipeTagCrossRef::class, Ingredient::class, RecipeIngredient::class], version = 14, exportSchema = false)
abstract class MyDb : RoomDatabase() {

    abstract fun recipeDao() : RecipeDao
    abstract fun tagDao() : TagDao
    abstract fun recipeTagDao() : RecipeTagCrossRefDao
    abstract fun ingredientDao() : IngredientDao
    abstract fun recipeIngredientDao() : RecipeIngredientCrossRefDao


    companion object {
        @Volatile private var instance : MyDb? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            return instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context, MyDb::class.java, "receptar.db")
            .fallbackToDestructiveMigration()
            .build()
    }
}