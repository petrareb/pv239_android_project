package com.example.receptarstarejmatere.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey

@Fts4
@Entity
data class Ingredient (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "rowid") val id : Int,  // @PrimaryKey(autoGenerate = true)
    val name : String
)
