package com.example.receptarstarejmatere.database.model

import androidx.room.*

@Entity( indices = [Index(value = ["ingredient_id"])])
data class Ingredient (
    @PrimaryKey @ColumnInfo(name = "ingredient_id") val id : Int,  // @PrimaryKey(autoGenerate = true)
    val name : String
)
