package com.example.receptarstarejmatere.database.model
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey

@Fts4
@Entity
data class Recipe(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "rowid") val id : Int,  // @PrimaryKey(autoGenerate = true)
    val name: String,
    @ColumnInfo(name = "is_favorite") val isFavorite: Boolean,
    @ColumnInfo(name = "preparation_time") val preparationTime: Int, // v minutach ?
    @ColumnInfo(name = "cooking_time") val cookingTime: Int, // v minutach
    val instructions: String,
    val pathToImage : String?, // route to an image in local storage
    val stars : Int // * number of stars
    // ingredients .. new table ?
    // tags  .. new table?
)
