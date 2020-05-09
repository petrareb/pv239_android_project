package com.example.receptarstarejmatere.database.model

import androidx.room.*


@Entity(primaryKeys = ["recipe_id", "tag_id"])
data class RecipeTagCrossRef (
    @ColumnInfo(name= "recipe_id", index = true) val recipeId : Int,
    @ColumnInfo(name = "tag_id", index = true) val tagId : Int
)

// https://medium.com/androiddevelopers/database-relations-with-room-544ab95e4542
// https://developer.android.com/reference/android/arch/persistence/room/Relation

data class RecipeWithTags (
    @Embedded val recipe: Recipe,
    @Relation(
        parentColumn = "rowid",
        entityColumn = "rowid",
        associateBy = Junction(
            value = RecipeTagCrossRef::class,
            parentColumn = "recipe_id",
            entityColumn = "tag_id"
        )
    )
    val tags : List<Tag>
)

data class TagWithRecipes (
    @Embedded val tag : Tag,
    @Relation (
        parentColumn = "rowid",
        entityColumn = "rowid",
        associateBy = Junction(
            value = RecipeTagCrossRef::class,
            parentColumn = "tag_id",
            entityColumn = "recipe_id"
        )
    )
    val recipes : List<Recipe>
)
