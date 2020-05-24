package com.example.receptarstarejmatere.database.model

import androidx.room.*

@Entity(
    indices = [
        Index(value = ["recipe_id"])
    ]
)
class Recipe(
    var name: String,
    var source: String, @ColumnInfo(name = "is_favorite") var isFavorite: Boolean,// v minutach
    @ColumnInfo(name = "preparation_time") var preparationTime: String,// v minutach
    @ColumnInfo(name = "cooking_time") var cookingTime: String, @ColumnInfo(name = "cooking_temp") var cookingTemperature: String,
    var instructions: String
) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "recipe_id")
    var id: Int = 0
    var pathToImage: String? = "" // route to an image in local storage
}
