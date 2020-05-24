package com.example.receptarstarejmatere.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Tag(var name: String) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "rowid")
    var tagId: Int = 0
}
