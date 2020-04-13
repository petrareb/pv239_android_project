package com.example.receptarstarejmatere.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Tag (
    @PrimaryKey @ColumnInfo(name = "rowid") val tagId : Int,
    val name : String
    )
