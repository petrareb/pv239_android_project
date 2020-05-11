package com.example.receptarstarejmatere.database.model
import androidx.room.*

@Entity (indices = [
    Index(value = ["recipe_id"])
])
class Recipe{

    constructor(name: String, source: String, isFavorite : Boolean, preparationTime : String,
                cookingTime : String, cookingTemperature : String, instructions : String) {
        this.name = name
        this.source = source
        this.isFavorite = isFavorite
        this.preparationTime = preparationTime
        this.cookingTemperature = cookingTemperature
        this.cookingTime = cookingTime
        this.instructions = instructions
    }

//    constructor(id : Int, name: String, source: String, isFavorite : Boolean, preparationTime : String,
//                cookingTime : String, cookingTemperature : String, instructions : String) {
//        this.id = id
//        this.name = name
//        this.source = source
//        this.isFavorite = isFavorite
//        this.preparationTime = preparationTime
//        this.cookingTemperature = cookingTemperature
//        this.cookingTime = cookingTime
//        this.instructions = instructions
//    }

    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "recipe_id")
    var id : Int = 0
    var name: String = ""
    var source: String = ""
    @ColumnInfo(name = "is_favorite") var isFavorite: Boolean = false
    @ColumnInfo(name = "preparation_time") var preparationTime: String = ""// v minutach
    @ColumnInfo(name = "cooking_time") var cookingTime: String = "" // v minutach
    @ColumnInfo(name = "cooking_temp")var cookingTemperature: String = ""
    var instructions: String = ""
    var pathToImage : String? = "" // route to an image in local storage
}
