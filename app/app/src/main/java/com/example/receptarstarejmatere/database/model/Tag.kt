package com.example.receptarstarejmatere.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Tag
{
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "rowid")
    var tagId : Int = 0
    var name : String = ""

    constructor(name : String) {
        this.name = name
    }

//    constructor(name : String, id : Int) {
//        this.tagId = id
//        this.name = name
//    }
}
