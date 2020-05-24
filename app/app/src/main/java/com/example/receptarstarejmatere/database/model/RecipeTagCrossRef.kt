package com.example.receptarstarejmatere.database.model

import androidx.room.*


@Entity(primaryKeys = ["recipe_id", "tag_id"])
data class RecipeTagCrossRef (
    @ColumnInfo(name= "recipe_id", index = true) val recipeId : Int,
    @ColumnInfo(name = "tag_id", index = true) val tagId : Int
)

data class RecipeWithTags (
    @Embedded val recipe: Recipe,
    @Relation(
        parentColumn = "recipe_id",
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
        entityColumn = "recipe_id",
        associateBy = Junction(
            value = RecipeTagCrossRef::class,
            parentColumn = "tag_id",
            entityColumn = "recipe_id"
        )
    )
    val recipes : List<Recipe>
)

class AllTagsWithRecipes(
    @ColumnInfo(name = "rowid") var tagId: Int, @ColumnInfo(name = "recipe_id") var recipeId: Int, @ColumnInfo(
        name = "name"
    ) var tagName: String
)