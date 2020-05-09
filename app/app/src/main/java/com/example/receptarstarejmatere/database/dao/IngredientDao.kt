package com.example.receptarstarejmatere.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.receptarstarejmatere.database.model.Ingredient

@Dao
interface IngredientDao {
    @Query("select *, `rowid` from Ingredient")
    fun getAll() : LiveData<List<Ingredient>>

    @Query("select *, `rowid` from Ingredient where rowid in (:ingredientsIds)")
    fun getRecipesByIds(ingredientsIds : IntArray) : LiveData<List<Ingredient>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(ingredients : List<Ingredient>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(ingredient: Ingredient)

    @Update
    fun update(ingredient: Ingredient)

    @Delete
    fun delete(ingredient: Ingredient)

    @Query("select *, `rowid` from Ingredient where name like :ingredName limit 1")
    fun getByName(ingredName: String): List<Ingredient>
}