package com.example.receptarstarejmatere.database.model
import androidx.room.*

@Entity (indices = [
    Index(value = ["recipe_id"])
])
data class Recipe(
    @PrimaryKey @ColumnInfo(name = "recipe_id") val id : Int,  // @PrimaryKey(autoGenerate = true)
    val name: String,
    val source: String,
    @ColumnInfo(name = "is_favorite") val isFavorite: Boolean,
    @ColumnInfo(name = "preparation_time") val preparationTime: Int, // v minutach
    @ColumnInfo(name = "cooking_time") val cookingTime: Int, // v minutach
    @ColumnInfo(name = "cooking_temp")val cookingTemperature: Int,
    val instructions: String,
    val pathToImage : String? // route to an image in local storage
)
