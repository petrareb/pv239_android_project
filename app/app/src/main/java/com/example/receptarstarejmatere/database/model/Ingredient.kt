package com.example.receptarstarejmatere.database.model

import androidx.room.*

@Entity( indices = [Index(value = ["ingredient_id"])])
class Ingredient
{
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "ingredient_id")
    var id : Int = 0
    var name : String = ""

    constructor(name : String){
        this.name  = name
    }
}
