package com.example.receptarstarejmatere.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.receptarstarejmatere.database.model.Ingredient

@Dao
interface IngredientDao {
    @Query("select *, `rowid` from Ingredient")
    fun getAll() : LiveData<List<Ingredient>>

    @Query("select *, `rowid` from Ingredient where rowid in (:ingredientsIds)")
    fun getIngredientsByIds(ingredientsIds : IntArray) : LiveData<List<Ingredient>>

    @Query("select *, `rowid` from Ingredient where rowid = :ingredientId")
    fun getIngredientById(ingredientId : Int) : LiveData<Ingredient>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(ingredients : List<Ingredient>) : List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(ingredient: Ingredient) : Long

    @Update
    fun update(ingredient: Ingredient)

    @Delete
    fun delete(ingredient: Ingredient)

    @Query("select *, `rowid` from Ingredient where name like :ingredName limit 1")
    suspend fun getByName(ingredName: String): List<Ingredient>
}