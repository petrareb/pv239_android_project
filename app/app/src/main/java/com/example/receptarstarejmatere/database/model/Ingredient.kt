package com.example.receptarstarejmatere.database.model

import androidx.room.*

@Entity( indices = [Index(value = ["ingredient_id"])])
class Ingredient(var name: String) {
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "ingredient_id")
    var id : Int = 0
}
